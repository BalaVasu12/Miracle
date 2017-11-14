

package com.Manoj.framework.utilities.messages;


public class SimpleErrorMessage extends BooleanMessageWithErrors{
    String message;

    public SimpleErrorMessage(String message) {
        super();
        this.message = message;
    }

    public SimpleErrorMessage() {
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
