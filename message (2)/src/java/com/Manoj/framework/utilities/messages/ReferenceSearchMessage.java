

package com.Manoj.framework.utilities.messages;

import com.Manoj.framework.AppModel;
import com.Manoj.framework.utilities.messages.AppMessage;


public class ReferenceSearchMessage implements AppMessage {

    AppModel model;
    Boolean result;
    
    public ReferenceSearchMessage() {}
    
    public ReferenceSearchMessage(AppModel resultReference) {
        result = (resultReference != null);
        model = resultReference;
    }

    public AppModel getModel() {
        return model;
    }

    public Boolean getResult() {
        return result;
    }
}
