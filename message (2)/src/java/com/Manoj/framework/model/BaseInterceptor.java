

package com.Manoj.framework.model;

import java.io.Serializable;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;


public class BaseInterceptor extends EmptyInterceptor {
    @Override
   public boolean onSave(Object entity,
                    Serializable id,
                    Object[] state,
                    String[] propertyNames,
                    Type[] types) {
       
       return false;
   }
    
    @Override
   public boolean onFlushDirty(Object entity,
                   Serializable id,
                   Object[] currentState,
                   Object[] previousState,
                   String[] propertyNames,
                   Type[] types) {
       
       return false;
   }
}
