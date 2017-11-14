/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.source.dao;

import com.Manoj.framework.AppDao;
import com.Manoj.framework.AppModel;
import com.source.entities.MissingDocuments;
import org.hibernate.Session;

/**
 *
 * @author Hp
 */
public class MissingDocumentsDao extends AppDao{
    
    public MissingDocumentsDao(){
        super();
    }
    
    public MissingDocumentsDao(Session session){
        super(session);
    }

    @Override
    public AppModel getModelInstance() {
        return new MissingDocuments();
    }

    @Override
    protected Class<?> getModelClass() {
        return MissingDocumentsDao.class;
    }
    
}
