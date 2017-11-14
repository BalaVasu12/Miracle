

package com.Manoj.framework.search;

import com.Manoj.framework.AppModel;
import org.hibernate.Criteria;


public interface SearchType {

    public void addCriteriaRules(Criteria crit, Class entity, String fieldName, String[] values);
}
