
package com.Manoj.framework.model;

import java.util.Date;


public interface ModelWithCreationAndModificationDates {
    
    public Date getCreatedOn();

    public void setCreatedOn(Date createdOn);

    public Date getModifiedOn();

    public void setModifiedOn(Date modifiedOn);
}
