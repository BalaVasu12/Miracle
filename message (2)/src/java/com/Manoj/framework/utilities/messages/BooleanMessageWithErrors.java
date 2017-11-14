

package com.Manoj.framework.utilities.messages;


public abstract class BooleanMessageWithErrors extends BooleanMessage {
    
    public BooleanMessageWithErrors() {
        super(false);
    }

    public abstract String getMessage();
}
