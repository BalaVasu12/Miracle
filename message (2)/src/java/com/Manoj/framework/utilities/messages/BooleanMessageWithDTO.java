

package com.Manoj.framework.utilities.messages;

import com.Manoj.framework.AppDto;


public class BooleanMessageWithDTO extends BooleanMessage{

    AppDto data;

    public BooleanMessageWithDTO(AppDto data) {
        super();
        this.data = data;
    }

    public BooleanMessageWithDTO(AppDto data, boolean success) {
        super(success);
        this.data = data;
    }

    public AppDto getData() {
        return data;
    }

    public void setData(AppDto data) {
        this.data = data;
    }
    
}
