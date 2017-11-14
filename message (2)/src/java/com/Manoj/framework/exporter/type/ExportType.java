

package com.Manoj.framework.exporter.type;

import com.Manoj.framework.exporter.service.ExportSearchMap;
import com.Manoj.framework.exporter.storage.ExportedSource;
import java.util.List;


public interface ExportType {

    ExportedSource export(List resultList, ExportSearchMap exportSearchMap) throws Exception;
    String getExportName();
    String getExportType();
    
    //Right now the interface relies only on garbage collection. If needed below method
    // can be opened up later
//    void dispose();
}
