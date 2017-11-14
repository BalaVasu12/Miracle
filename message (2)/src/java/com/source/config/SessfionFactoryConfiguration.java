
package com.source.config;

import com.Manoj.framework.SessionFactoryConfigurationInterface;
import com.source.entities.RolePrivileges;
import com.source.entities.User;
import com.source.entities.UserRoles;
import org.hibernate.cfg.Configuration;


public class SessfionFactoryConfiguration implements SessionFactoryConfigurationInterface{
    
    @Override
    public Configuration getFactoryConfiguration() {
        Configuration config = new Configuration();
        
        config.configure()
               
                .addAnnotatedClass(User.class);
                
        
        return config;
    }
}
