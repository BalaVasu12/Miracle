

package com.Manoj.framework.exporter.storage;

import com.Manoj.framework.exporter.service.ExportSearchMap;
import com.Manoj.framework.exporter.type.ExportType;
import java.util.List;


public abstract class ExportStorageType {

    public void beforeExport(List data, ExportSearchMap exportSearchMap, ExportType type, ExportStorageType storage) {
        //for alterations before exporting 
    }
    
    public void afterExport(List data, ExportedSource source, ExportSearchMap exportSearchMap, ExportType type, ExportStorageType storage) {
        //This was done to be called if there are errors in between export so that even if store routine is 
        //not called, this one is called. Right now this is not of much use. This is to be called after export
        //before store
    }
    
    abstract public void store(ExportedSource source) throws Exception;
}
