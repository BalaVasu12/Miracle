package com.source.dao;

import com.Manoj.framework.AppDao;
import com.Manoj.framework.AppModel;
import com.source.entities.Roles;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class RolesDao extends AppDao{
    
    public RolesDao(){
        super();
    }
    
    public RolesDao(Session session){
        super(session);
    }
    
    @Override
    public AppModel getModelInstance(){
        return new Roles();
    }
    @Override
    protected Class<?> getModelClass() {
        return Roles.class;
    }
    
    public Roles findByName(String name) {
        Session session = getDaoSession();
        Criteria crit = session.createCriteria(getModelClass());
        crit.add(Restrictions.eq("roleName", name));
        crit.setMaxResults(1);
        
        return (Roles) crit.uniqueResult();
    }
}
