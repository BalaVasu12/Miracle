

package com.Manoj.framework;

import com.Manoj.framework.model.BaseInterceptor;
import com.Manoj.framework.utilities.BFM;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class ApplicationInterceptor extends EmptyInterceptor {
    
    ArrayList<BaseInterceptor> interceptorsList;
    
    public ApplicationInterceptor() {
        super();
        
        interceptorsList = (ArrayList)BFM.getBean("interceptorsList");
    }
    
    @Override
    public boolean onSave(Object entity,
                    Serializable id,
                    Object[] state,
                    String[] propertyNames,
                    Type[] types) {
       
        Iterator itr = interceptorsList.iterator();
        Boolean modified = false;
        while(itr.hasNext()) {
            BaseInterceptor interceptor = (BaseInterceptor) itr.next();
            modified = interceptor.onSave(entity, id, state, propertyNames, types) || modified;
        }
        
       return modified;
   }
    
    @Override
   public boolean onFlushDirty(Object entity,
                   Serializable id,
                   Object[] currentState,
                   Object[] previousState,
                   String[] propertyNames,
                   Type[] types) {
       
       Iterator itr = interceptorsList.iterator();
       Boolean modified = false;
       while(itr.hasNext()) {
            BaseInterceptor interceptor = (BaseInterceptor) itr.next();
            modified = interceptor.onFlushDirty(entity, id, currentState, previousState, propertyNames, types) || modified;
       }
        
       return modified;
   }
    
}
