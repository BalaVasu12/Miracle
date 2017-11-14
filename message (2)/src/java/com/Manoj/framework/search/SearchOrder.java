

package com.Manoj.framework.search;


public class SearchOrder {

    private String column;
    private String order;
    public static String ORDER_ASC = "ASC";
    public static String ORDER_DESC = "DESC";
    public static String DEFAULT_ORDER = ORDER_ASC;

    public SearchOrder() {
    }

    public SearchOrder(String column, String order) {
        this.column = column;
        this.order = order;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
    
    
}
