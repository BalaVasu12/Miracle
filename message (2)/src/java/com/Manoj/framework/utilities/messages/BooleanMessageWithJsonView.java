

package com.Manoj.framework.utilities.messages;

import com.Manoj.framework.AppModel;
import com.monitorjbl.json.JsonView;


public class BooleanMessageWithJsonView extends BooleanMessage{

    JsonView data;

    public BooleanMessageWithJsonView(JsonView data) {
        super();
        this.data = data;
    }

    public BooleanMessageWithJsonView(JsonView data, boolean success) {
        super(success);
        this.data = data;
    }

    public JsonView getData() {
        return data;
    }

    public void setData(JsonView data) {
        this.data = data;
    }
    
}
