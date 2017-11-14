

package com.Manoj.framework.utilities.messages;

import com.Manoj.framework.AppModel;


public class BooleanMessageWithDetails extends BooleanMessage{

    AppModel data;

    public BooleanMessageWithDetails(AppModel data) {
        super();
        this.data = data;
    }

    public BooleanMessageWithDetails(AppModel data, boolean success) {
        super(success);
        this.data = data;
    }

    public AppModel getData() {
        return data;
    }

    public void setData(AppModel data) {
        this.data = data;
    }
    
}
