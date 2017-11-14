

package com.source.exceptions;

import com.Manoj.exceptions.BaseException;
import com.Manoj.exceptions.InternalApplicationError;


public class StorageSystemException extends InternalApplicationError{

    public StorageSystemException() {
        super();
    }
    
    public StorageSystemException(String message) {
        super(message);
    }
    
    public StorageSystemException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
