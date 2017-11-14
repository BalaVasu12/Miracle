

package com.Manoj.framework.search.types;

import com.Manoj.framework.search.SearchType;
import org.hibernate.Criteria;


public class ExcludeFromSearch implements SearchType{

    @Override
    public void addCriteriaRules(Criteria crit, Class entity, String fieldName, String[] values) {
        //Nothing here
    }

}
