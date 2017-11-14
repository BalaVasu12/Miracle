/*
 * ***NOTE::::: Dao is not properly implemented. Requires further breaking down into interface and implementation
 */
package com.Manoj.framework;

import com.Manoj.exceptions.DatabaseException;
import com.Manoj.framework.search.SearchMap;
import com.Manoj.framework.search.SearchOnCriteriaInterface;
import com.Manoj.framework.utilities.BFM;
import com.Manoj.framework.utilities.ResultList;
import com.Manoj.framework.utilities.SFM;
import com.source.services.AuthService;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.jboss.logging.Logger;
import java.util.regex.Matcher;

public abstract class AppDao {
    
    abstract public AppModel getModelInstance();
    abstract protected Class<?> getModelClass();
    
    private Session daoSession;
    private AppModel owner; //The user who owns this operation

    public AppDao() {
    }

    public AppDao(Session daoSession) {
        this();
        this.daoSession = daoSession;
    }
    
    public AppDao(Session daoSession, AppModel owner) {
        this();
        this.daoSession = daoSession;
        this.owner = owner;
    }

    public AppModel getOwner() {
        return getOwner(false);
    }
    
    public AppModel getOwner(boolean returnOnlySetOwner) {
        if(owner!=null)
            return owner;
        else if(returnOnlySetOwner)
            return null;
        
        return AuthService.getLoggedInEmployee(); 
    }

    public AppDao setOwner(AppModel owner) {
        this.owner = owner;
        return this;
    }
       
    public long add(AppModel model) throws DatabaseException {
        if(daoSession != null) {
            return add(model, daoSession);
        } else {
            return add(model, SFM.openSession());
        }
    }
    
    public long add(AppModel model, Session session) throws DatabaseException {
        long savedId = 0;

        try {
            savedId = (long) session.save(model);
        } catch (Exception ex) {
            throw new DatabaseException("SQL Error wile adding", ex);
        } finally {
            
        }
        
        return savedId;
    }
    
    public void update(AppModel updatedModel) throws DatabaseException {
        if(daoSession != null)
            update(updatedModel, daoSession);
        else
            update(updatedModel, SFM.openSession());
    }
    
    public void update(AppModel updatedModel, Session session) throws DatabaseException {
        try {
            session.merge(updatedModel);
        } catch (Exception ex) {
            throw new DatabaseException("SQL Error wile updating", ex);
        } finally {
            
        }
    }
    
    public AppModel read(long id) throws DatabaseException {
        if(daoSession != null)
            return read(id, daoSession);
        else
            return read(id, SFM.openSession());
    }
    
    public AppModel read(long id, Session session) throws DatabaseException {
        AppModel model;
        
        try {
            model = (AppModel)session.get(getModelClass(), id);
        } catch (Exception ex) {
            throw new DatabaseException("SQL Error wile reading", ex);
        } finally {
        
        }
        
        return model;
    }
    
    public ResultList list() throws DatabaseException {
        if(daoSession != null)
            return list(daoSession, null);
        else
            return list(SFM.openSession(), null);
    }
    
    public ResultList list(SearchMap search) throws DatabaseException {
        if(daoSession != null)
            return list(daoSession, search);
        else
            return list(SFM.openSession(), search);
    }
    
    public ResultList list(Session session, SearchMap search) throws DatabaseException {
        Criteria indexCrit;
        SearchOnCriteriaInterface searchService = searchService();
        ResultList result = new ResultList();
        try {
            indexCrit = session.createCriteria(getModelClass());
            
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
    
    public void delete(long id) throws DatabaseException {
        if(daoSession != null)
            delete(id, daoSession);
        else
            delete(id, SFM.openSession());
    }
    
    public void delete(long id, Session session) throws DatabaseException {
        
        try{
           AppModel model = 
                     (AppModel)session.get(getModelClass(), id); 
           session.delete(model); 
        } catch (Exception ex) {
           throw new DatabaseException("SQL Error wile deleting", ex);
        } finally {
           
        }
    }

    public Session getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(Session daoSession) {
        this.daoSession = daoSession;
    }
    
    public SearchOnCriteriaInterface searchService() {
        return (SearchOnCriteriaInterface)BFM.getBean("searchService");
    }
    
    public void saveAuditLogs(AppModel model, String details, String operation,long resourceId) throws DatabaseException {
//        saveAuditLogs(model, details, null, operation, resourceId);
    }
    
      public String jSonParsing(HttpServletRequest request) throws IOException{
    StringBuilder sb = new StringBuilder();
    BufferedReader reader = request.getReader();
    try {
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }
    } finally {
        reader.close();
    }
    
    String jsonData = sb.toString();
       
       return jsonData;
   }  
    
}
