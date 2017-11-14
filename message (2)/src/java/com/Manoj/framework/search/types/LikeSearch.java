

package com.Manoj.framework.search.types;

import com.Manoj.framework.search.ConversionServiceInterface;
import com.Manoj.framework.search.SearchType;
import com.Manoj.framework.utilities.BFM;
import java.util.logging.Level;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import java.util.logging.Logger;


public class LikeSearch implements SearchType{
    
    ConversionServiceInterface conversionService;

    public LikeSearch() {
        conversionService = (ConversionServiceInterface)BFM.getBean("conversionService");
    }

    @Override
    public void addCriteriaRules(Criteria crit, Class entity, String fieldName, String[] values) {
            Disjunction or = Restrictions.or();
            for(int i=0; i<values.length; i++) {
                or.add(Restrictions.like(fieldName, "%"+values[i]+"%"));
            }
            
            crit.add(or);
    }

}
