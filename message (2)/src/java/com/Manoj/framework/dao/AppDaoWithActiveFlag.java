
package com.Manoj.framework.dao;

import com.Manoj.framework.AppDao;
import com.Manoj.exceptions.DatabaseException;
import com.Manoj.framework.AppModel;
import com.Manoj.framework.model.ModelWithActiveFlag;
import com.Manoj.framework.search.SearchMap;
import com.Manoj.framework.search.SearchOnCriteriaInterface;
import com.Manoj.framework.utilities.BFM;
import com.Manoj.framework.utilities.ResultList;
import com.Manoj.framework.utilities.SFM;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;


public abstract class AppDaoWithActiveFlag extends AppDao {

    public AppDaoWithActiveFlag() {
        super();
    }
    
    public AppDaoWithActiveFlag(Session session) {
        super(session);
    }
    
    public ResultList activeList() throws DatabaseException {
        if(getDaoSession() != null)
            return activeList(getDaoSession(), null);
        else
            return activeList(SFM.openSession(), null);
    }
    
    public ResultList activeList(SearchMap search) throws DatabaseException {
        if(getDaoSession() != null)
            return activeList(getDaoSession(), search);
        else
            return activeList(SFM.openSession(), search);
    }
    
    public ResultList activeList(Session session, SearchMap search) throws DatabaseException {
        Criteria indexCrit;
        SearchOnCriteriaInterface searchService = searchService();
        ResultList result = new ResultList();
        try {
            indexCrit = session.createCriteria(getModelClass());
            indexCrit.add(Restrictions.eq("active", true));
            
            //Fetching unfiltered row count
            indexCrit.setProjection(Projections.rowCount());
            result.setFullCount((long)indexCrit.uniqueResult());
            
            
            if(search != null && search.isSearchable()) {
                searchService.search(getModelClass(), indexCrit, search);
                
                //Fetching filtered row count
                result.setFilteredCount((long)indexCrit.uniqueResult());
            } else {
                result.setFilteredCount(result.getFullCount());
            }
            
            ///Setting mode to fetching rows
            indexCrit.setProjection(null);
            
            searchService.paginate(getModelClass(), indexCrit, search);
            searchService.order(getModelClass(), indexCrit, search);

            result.setList(indexCrit.list());
            return result;
        } catch(Exception ex){
            throw new DatabaseException("SQL Error wile listing", ex);
        } finally {

        }
    }
    
    public AppModel activeRead(long id) throws DatabaseException {
        if(getDaoSession() != null)
            return activeRead(id, getDaoSession());
        else
            return activeRead(id, SFM.openSession());
    }
    
    public AppModel activeRead(long id, Session session) throws DatabaseException {
        AppModel model = read(id, session);
        if(model!=null && ((ModelWithActiveFlag)model).getActive())
            return model;
        else
            return null;
    }
}
