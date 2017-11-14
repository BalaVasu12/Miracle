

package com.Manoj.framework.utilities.messages;

import java.util.List;


public class BooleanMessageWithList extends BooleanMessage{

    List data;

    public BooleanMessageWithList(List data) {
        super();
        this.data = data;
    }

    public BooleanMessageWithList(List data, boolean success) {
        super(success);
        this.data = data;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
    
}
