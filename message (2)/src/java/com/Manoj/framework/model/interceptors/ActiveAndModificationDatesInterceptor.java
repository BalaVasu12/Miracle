

package com.Manoj.framework.model.interceptors;

import com.Manoj.framework.model.BaseInterceptor;
import com.Manoj.framework.model.ModelWithCreationAndModificationDates;
import java.io.Serializable;
import java.util.Calendar;
import org.hibernate.type.Type;

public class ActiveAndModificationDatesInterceptor extends BaseInterceptor{

    @Override
    public boolean onSave(Object entity,
                    Serializable id,
                    Object[] state,
                    String[] propertyNames,
                    Type[] types) {
       
       if(entity instanceof ModelWithCreationAndModificationDates) {
           ModelWithCreationAndModificationDates model = 
                   (ModelWithCreationAndModificationDates)entity;
           
           Boolean modified = false;
           for(int i=0; i<propertyNames.length; i++) {
               if("createdOn".equals(propertyNames[i])) {
                   state[i] = Calendar.getInstance().getTime();
                   modified = true;
               }
               if("modifiedOn".equals(propertyNames[i])) {
                   state[i] = Calendar.getInstance().getTime();
                   modified = true;
               }
           }
           
           return modified;
       }
       
       return false;
   }
    
    @Override
   public boolean onFlushDirty(Object entity,
                   Serializable id,
                   Object[] currentState,
                   Object[] previousState,
                   String[] propertyNames,
                   Type[] types) {
       
       if(entity instanceof ModelWithCreationAndModificationDates) {
           ModelWithCreationAndModificationDates model = 
                   (ModelWithCreationAndModificationDates)entity;
           
           Boolean modified = false;
           for(int i=0; i<propertyNames.length; i++) {
               if("createdOn".equals(propertyNames[i]) && previousState!=null) {
                   currentState[i] = previousState[i];
                   modified = true;
               }
               if("modifiedOn".equals(propertyNames[i])) {
                   currentState[i] = Calendar.getInstance().getTime();
                   modified = true;
               }
           }
           
           return modified;
       }
       
       return false;
   }
}
