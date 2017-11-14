

package com.Manoj.framework.search;

import com.Manoj.framework.search.annotations.AutoSearch;
import com.Manoj.framework.search.annotations.AutoSearchType;
import com.Manoj.framework.search.types.ExcludeFromSearch;
import com.Manoj.framework.utilities.ResultList;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;


public class Search implements SearchOnCriteriaInterface {

    @Override
    public Criteria search(Class entity, Criteria crit, SearchMap search) {
        if(search == null) 
            return crit;
        
        AutoSearch classAutoSearchApplicable = (AutoSearch)entity.getAnnotation(AutoSearch.class);
        if(classAutoSearchApplicable == null)
            return crit;
        
        Set<Map.Entry<String, String[]>> searchParameters = search.getRequestMap().entrySet();
        
        for(Map.Entry<String, String[]> entry: searchParameters) {
            searchEntry(entity, crit, entry.getKey(), entry.getValue());
        }
        
        return crit;
    }

    @Override
    public Criteria paginate(Class entity, Criteria crit, SearchMap search) {
        if(search == null)
            return crit;
        
        PaginationData data = search.getPagination();
        
        if(data == null)
            return crit;
        
        int limit = data.getLimit();
        int start = data.getStartNumber();
        
        if(limit > 0) {
            crit.setMaxResults(limit);
            if(start>0)
                crit.setFirstResult((start-1)*limit);
        }
        
        return crit;
    }

    @Override
    public Criteria order(Class entity, Criteria crit, SearchMap search) {
        if(search == null)
            return crit;
        
        SearchOrder order = search.getOrder();
        
        if(order == null)
            return crit;
        
        String column = order.getColumn();
        String columnOrder = order.getOrder();
        
        if(column == null)
            return crit;
        if(columnOrder == null)
            columnOrder = SearchOrder.DEFAULT_ORDER;
       
        if(isNestedObject(column)) {
            sortForeignKeyField(entity, crit, column, columnOrder);
        } else {
            sortField(entity, crit, column, columnOrder);
        }
        return crit;
    }
    
    @Override
    public ResultList searchOrderAndPaginate(Class entity, Criteria crit, SearchMap search) {
        ResultList result = new ResultList();
        
        crit.setProjection(Projections.rowCount());
        result.setFullCount((long)crit.uniqueResult());

        if(search != null && search.isSearchable()) {
            search(entity, crit, search);

            //Fetching filtered row count
            result.setFilteredCount((long)crit.uniqueResult());
        } else {
            result.setFilteredCount(result.getFullCount());
        }

        ///Setting mode to fetching rows
        crit.setProjection(null);

        paginate(entity, crit, search);
        order(entity, crit, search);
        
        result.setList(crit.list());
        
        return result;
    }

    private boolean isFieldAnAssociation(Field field) {
        OneToOne oneToOne = field.getAnnotation(OneToOne.class);
        OneToMany oneToMany = field.getAnnotation(OneToMany.class);
        ManyToOne manyToOne = field.getAnnotation(ManyToOne.class);
        ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);
        
        if(oneToOne!=null || oneToMany!=null || manyToOne!=null || manyToMany!=null)
            return true;
        else
            return false;
    }
    
    private void searchEntry(Class entity, Criteria crit, String key, String[] value) {
        if(isNestedObject(key)) {
            searchForeignKeyField(entity, crit, key, value);
        } else {
            searchField(entity, crit, key, value);
        }
    }
    
    private boolean isNestedObject(String refKey) {
        return refKey.contains(".");
    }
    
    private List getBaseAndReferencePaths(String key) {
        int dotPosition = key.indexOf(".");
        List returnList = new ArrayList();
        returnList.add(key.substring(0, dotPosition));
        returnList.add(key.substring(dotPosition+1));
        
        return returnList;
    }
    
    private void searchField(Class entity, Criteria crit, String key, String[] values) {
        try {
            Field field = entity.getDeclaredField(key);
            if(field.getAnnotation(Column.class)==null) {
                Logger.getLogger(Search.class.getName()).log(Level.WARNING, "Non column field ''{0}'' attempted to be searched", field.getName());
                return;
            }
            
            AutoSearchType searchType = field.getAnnotation(AutoSearchType.class);
            SearchType type;
            if(searchType == null) {
                if(!isFieldAnAssociation(field))
                    type = (SearchType)(SearchTypeList.DEFAULT.newInstance());
                else {
                    type = (SearchType)(SearchTypeList.DEFAULT.newInstance());
                }
            } else {
                type = (SearchType)searchType.type().newInstance();
            }

            type.addCriteriaRules(crit, entity, field.getName(), values);
        } catch (SecurityException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.WARNING, "Unable to instantiate a class while trying to do an autosearch", ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void searchForeignKeyField(Class entity, Criteria crit, String key, String[] value) {
        List path = getBaseAndReferencePaths(key);
        String fieldName = (String) path.get(0);
        String subPath = (String) path.get(1);
        
        Field field;
        try {
            field = entity.getDeclaredField(fieldName);
            
            AutoSearch classAutoSearchApplicable = (AutoSearch)field.getType().getAnnotation(AutoSearch.class);
            if(classAutoSearchApplicable == null)
                return;

            AutoSearchType searchType = field.getAnnotation(AutoSearchType.class);
            if(searchType != null && searchType.type() == ExcludeFromSearch.class) {
                return;
            }

            if(!isFieldAnAssociation(field)) {
                Logger.getLogger(Search.class.getName()).log(Level.ALL, "{0} is not an associated class", key);
            }

            Criteria subC = getCriteria(crit, fieldName);
            
            searchEntry(field.getType(), subC, subPath, value);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sortField(Class entity, Criteria crit, String sortColumn, String sortOrder) {
        Order critOrder;
        if(sortOrder.equalsIgnoreCase(SearchOrder.ORDER_ASC))
            critOrder = Order.asc(sortColumn);
        else
            critOrder = Order.desc(sortColumn);
        
        crit.addOrder(critOrder);
    }
    
    private void sortForeignKeyField(Class entity, Criteria crit, String sortColumn, String sortOrder) {
        List path = getBaseAndReferencePaths(sortColumn);
        String fieldName = (String) path.get(0);
        String subPath = (String) path.get(1);
        
        Field field;
        try {
            field = entity.getDeclaredField(fieldName);
            
            if(!isFieldAnAssociation(field)) {
                Logger.getLogger(Search.class.getName()).log(Level.ALL, "{0} is not an associated class", sortColumn);
                return ;
            }
            
            Criteria subC = getCriteria(crit, fieldName);
            
            if(isNestedObject(subPath)) {
                sortForeignKeyField(entity, subC, subPath, sortOrder);
            } else {
                sortField(entity, subC, subPath, sortOrder);
            }
            
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Criteria getCriteria(Criteria crit, String associationPath) {
        try {
            Field listField = crit.getClass().getDeclaredField("subcriteriaList");
            listField.setAccessible(true);
            List<CriteriaImpl.Subcriteria> subcriteriaList = (List<CriteriaImpl.Subcriteria>) listField.get(crit);
            for(CriteriaImpl.Subcriteria subCrit: subcriteriaList) {
                if(subCrit.getPath().equals(associationPath))
                    return subCrit;
            }
            return crit.createCriteria(associationPath);
        } catch (Exception ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace(System.out);
            return crit.createCriteria(associationPath);
        }
    }
}
