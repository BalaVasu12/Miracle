

package com.Manoj.framework.exporter.service;

import com.Manoj.framework.exporter.storage.ExportStorageType;
import com.Manoj.framework.exporter.storage.ExportedSource;
import com.Manoj.framework.exporter.type.ExportType;
import java.util.List;


public interface Exporter {

    ExportedSource export(List data, ExportSearchMap exportSearchMap, ExportType type, ExportStorageType storage) throws Exception;
    
}
