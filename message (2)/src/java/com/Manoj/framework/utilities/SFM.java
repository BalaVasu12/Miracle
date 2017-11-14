package com.Manoj.framework.utilities;

import com.Manoj.framework.ApplicationInterceptor;
import com.Manoj.framework.SessionFactoryConfigurationInterface;
import com.Manoj.framework.exporter.storage.FileStreamExportedSource;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/*
    Session Factory manager
 */
public class SFM {

    @Autowired
    private static  SessionFactory factory = buildSessionFactory() ;

    private static SessionFactory buildSessionFactory() {
        try{
             Configuration config = ((SessionFactoryConfigurationInterface) BFM.getBean("sessionFactoryConfiguration"))
                    .getFactoryConfiguration();
            config.setInterceptor(new ApplicationInterceptor());

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();
            factory = config.buildSessionFactory(serviceRegistry);
           
            System.out.println("aws testing"+factory);
            
        }catch(HibernateException ex){
//            factory = buildSessionFactory();
            System.out.println("aws testing from catch data "+factory);
            Logger.getLogger(SFM.class).error(ex);
            
        }    
        return factory;

    }

    public static SessionFactory getFactory() {
        if(factory==null){
            factory = buildSessionFactory();
        }
        return factory;
    }

    public static Session openSession() {
        if(factory==null){
            factory = buildSessionFactory();
        }
        return factory.openSession();
    }    
}
