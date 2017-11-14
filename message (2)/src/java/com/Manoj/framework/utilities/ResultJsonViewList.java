

package com.Manoj.framework.utilities;

import com.monitorjbl.json.JsonView;
import java.util.List;


public class ResultJsonViewList implements Result {

    private JsonView list; //bad implementation. Should have a generic property that allows both list and json view
    private long filteredCount;
    private long fullCount;

    public ResultJsonViewList() {
    }

    public ResultJsonViewList(JsonView list) {
        this.list = list;
    }

    public ResultJsonViewList(JsonView list, long filteredCount) {
        this.list = list;
        this.filteredCount = filteredCount;
    }
    
    public ResultJsonViewList(JsonView list, long filteredCount, long fullCount) {
        this.list = list;
        this.filteredCount = filteredCount;
        this.fullCount = fullCount;
    }
    
    public ResultJsonViewList(ResultList resultList) {
        this.filteredCount = resultList.getFilteredCount();
        this.fullCount = resultList.getFullCount();
        
        this.list = JsonView.with(resultList.getList());
    }

    public JsonView getList() {
        return list;
    }

    public void setList(JsonView list) {
        this.list = list;
    }

    public long getFilteredCount() {
        return filteredCount;
    }

    public void setFilteredCount(long filteredCount) {
        this.filteredCount = filteredCount;
    }

    public long getFullCount() {
        return fullCount;
    }

    public void setFullCount(long fullCount) {
        this.fullCount = fullCount;
    }
}
