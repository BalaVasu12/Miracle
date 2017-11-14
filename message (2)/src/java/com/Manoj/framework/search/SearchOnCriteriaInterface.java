

package com.Manoj.framework.search;

import com.Manoj.framework.utilities.ResultList;
import org.hibernate.Criteria;


public interface SearchOnCriteriaInterface {

    public Criteria search(Class entity, Criteria crit, SearchMap search);
    
    public Criteria paginate(Class entity, Criteria crit, SearchMap search);
    
    public Criteria order(Class entity, Criteria crit, SearchMap search);
    
    public ResultList searchOrderAndPaginate(Class entity, Criteria crit, SearchMap search);
    
}
