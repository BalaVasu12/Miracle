

package com.Manoj.framework.search.types;

import com.Manoj.framework.search.ConversionService;
import com.Manoj.framework.search.ConversionServiceInterface;
import com.Manoj.framework.search.SearchType;
import com.Manoj.framework.utilities.BFM;
import java.lang.reflect.Field;
import java.util.logging.Level;
import org.hibernate.Criteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import java.util.logging.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.SimpleExpression;
import org.springframework.beans.factory.annotation.Autowired;


public class EqualsOrRangeSearch implements SearchType{
    
    ConversionServiceInterface conversionService;
    protected String rangeSeparator = "~";

    public EqualsOrRangeSearch() {
        conversionService = (ConversionServiceInterface)BFM.getBean("conversionService");
    }

    @Override
    public void addCriteriaRules(Criteria crit, Class entity, String fieldName, String[] values) {
        Disjunction or = Restrictions.or();
        for(int i=0; i<values.length; i++) {
            String value = values[i];
            
            if(value.contains(rangeSeparator)) {
                int separatorPosition = value.indexOf(rangeSeparator);
                try {
                    Field field = entity.getDeclaredField(fieldName);
                    Object lowerLimit = null, upperLimit = null;
                    String lowerLimitString = value.substring(0, separatorPosition),
                            upperLimitString = value.substring(separatorPosition+1);
                    
                    if(!lowerLimitString.trim().isEmpty()) {
                        lowerLimit = conversionService.convert(
                            field.getType(), 
                            lowerLimitString);
                    }
                
                    if(!upperLimitString.trim().isEmpty()) {
                        upperLimit = conversionService.convert(
                            field.getType(), 
                            upperLimitString);
                    }
                    
                    boolean isLowerLimitSet = emptyCheck(lowerLimit, field.getType());
                    boolean isUppperLimitSet = emptyCheck(upperLimit, field.getType());
                    
                    Criterion expr = null;
                    if(isLowerLimitSet && isUppperLimitSet) {
                        //Using between especially for dates
                        expr = Restrictions.between(fieldName, lowerLimit, upperLimit);
                    } else if(isLowerLimitSet) {
                        expr = Restrictions.ge(fieldName, lowerLimit);
                    } else if(isUppperLimitSet) {
                        expr = Restrictions.le(fieldName, upperLimit);
                    }
                    
                    if(expr != null)
                        or.add(expr);
                    
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(EqualsOrRangeSearch.class.getName()).log(Level.WARNING, "Field not found: "+fieldName, ex);
                }
            } else {
                try {
                    or.add(Restrictions.eq(fieldName, conversionService.convert(
                        entity.getDeclaredField(fieldName).getType(), 
                        values[i]
                    )));
                } catch (NoSuchFieldException ex) {
                    Logger.getLogger(EqualsOrRangeSearch.class.getName()).log(Level.WARNING, "Field not found: "+fieldName, ex);
                }
            }
        }

        crit.add(or);
    }
    
    private boolean emptyCheck(Object operand, Class type) {
        return operand!=null && (
                            (type.isPrimitive() && type == Integer.TYPE && (int)operand != 0) ||
                            !type.isPrimitive() || type != Integer.TYPE);
    }

}
