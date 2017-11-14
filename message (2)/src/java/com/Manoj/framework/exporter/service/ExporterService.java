

package com.Manoj.framework.exporter.service;

import com.Manoj.framework.exporter.storage.ExportStorageType;
import com.Manoj.framework.exporter.storage.ExportedSource;
import com.Manoj.framework.exporter.type.ExportType;
import java.util.List;


public class ExporterService implements Exporter{

    @Override
    public ExportedSource export(List data, ExportSearchMap exportSearchMap, ExportType type, ExportStorageType storage) throws Exception {
        
        if(type == null)
            throw new Exception("Report type cannot be null");
        
        if(storage!=null) {
            storage.beforeExport(data, exportSearchMap, type, storage);
        }
        
        ExportedSource source = type.export(data, exportSearchMap);
        
        if(storage!=null) {
            storage.afterExport(data, source, exportSearchMap, type, storage);
        }
        
        if(storage!=null) {
            storage.store(source);
        }
        
        return source;
    }

    
}
