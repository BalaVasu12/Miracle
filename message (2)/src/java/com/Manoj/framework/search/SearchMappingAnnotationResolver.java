

package com.Manoj.framework.search;

import com.Manoj.framework.search.annotations.SearchMapping;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class SearchMappingAnnotationResolver implements HandlerMethodArgumentResolver{
    
    private static String PAGINATE_START_KEY = "start";
    private static String PAGINATE_LENGTH_KEY = "count";
    private static String ORDER_ITEM_KEY = "col";
    private static String ORDER_DIR_KEY = "dir";

    @Override
    public boolean supportsParameter(MethodParameter mp) {
        SearchMapping mapping = (SearchMapping) mp.getParameterAnnotation(SearchMapping.class);
        if(mapping == null)
            return false;
        
        if(!mp.getParameterType().equals(SearchMap.class))
            return false;
        
        return true;
    }

    @Override
    public Object resolveArgument(MethodParameter mp, ModelAndViewContainer mavc, NativeWebRequest nwr, WebDataBinderFactory wdbf) throws Exception {
        String searchParam = ((SearchMapping) mp.getParameterAnnotation(SearchMapping.class)).search(),
                paginateParam = ((SearchMapping) mp.getParameterAnnotation(SearchMapping.class)).paginate(),
                orderParam = ((SearchMapping) mp.getParameterAnnotation(SearchMapping.class)).order();
        Set<Map.Entry<String, String[]>> paramList = nwr.getParameterMap().entrySet();
        Map <String, String[]> searchObject = new LinkedHashMap();
        PaginationData pagination = new PaginationData();
        SearchOrder order = new SearchOrder();
        
        for(Map.Entry<String, String[]> entry: paramList) {
            String key = entry.getKey();
            if(key.startsWith(searchParam+".")) {
                key = key.replaceFirst(searchParam+".", "");
                if(key.endsWith("[]")) {
                    key = key.replace("[]", "");
                } 
                
                searchObject.put(key, entry.getValue());
            } else if(key.startsWith(paginateParam+".")) {
                key = key.replaceFirst(paginateParam+".", "");
                if(key.equals(PAGINATE_START_KEY)) {
                    pagination.setStartNumber(Integer.parseInt(entry.getValue()[0]));
                } else if(key.equals(PAGINATE_LENGTH_KEY)) {
                    pagination.setLimit(Integer.parseInt(entry.getValue()[0]));
                } 
            } else if(key.startsWith(orderParam+".")) {
                key = key.replaceFirst(orderParam+".", "");
                if(key.equals(ORDER_ITEM_KEY)) {
                    order.setColumn(entry.getValue()[0]);
                } else if(key.equals(ORDER_DIR_KEY)) {
                    order.setOrder(entry.getValue()[0]);
                }
            }
        }
        
        return new SearchMap(searchObject, pagination, order);
    }

}
