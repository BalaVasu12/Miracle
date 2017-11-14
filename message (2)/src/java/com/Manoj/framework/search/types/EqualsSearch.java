

package com.Manoj.framework.search.types;

import com.Manoj.framework.search.ConversionService;
import com.Manoj.framework.search.ConversionServiceInterface;
import com.Manoj.framework.search.SearchType;
import com.Manoj.framework.utilities.BFM;
import java.util.logging.Level;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;


public class EqualsSearch implements SearchType{
    
    ConversionServiceInterface conversionService;

    public EqualsSearch() {
        conversionService = (ConversionServiceInterface)BFM.getBean("conversionService");
    }

    @Override
    public void addCriteriaRules(Criteria crit, Class entity, String fieldName, String[] values) {
        if(values.length>1) {
            Disjunction or = Restrictions.or();
            for(int i=0; i<values.length; i++) {
                
                try {
                    or.add(Restrictions.eq(fieldName, conversionService.convert(
                        entity.getDeclaredField(fieldName).getType(), 
                        values[i]
                    )));
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(EqualsSearch.class.getName()).log(Level.WARNING, "Field not found: "+fieldName, ex);
                }
            }
            
            crit.add(or);
        } else {
            try {
                crit.add(Restrictions.eq(fieldName, conversionService.convert(
                        entity.getDeclaredField(fieldName).getType(),
                        values[0]
                )));
            } catch (NoSuchFieldException ex) {
                Logger.getLogger(EqualsSearch.class.getName()).log(Level.SEVERE, "Field not found: "+fieldName, ex);
            }
        } 
    }

}
