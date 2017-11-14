

package com.Manoj.framework.exporter.storage;

import com.Manoj.framework.exporter.service.ExportSearchMap;
import com.Manoj.framework.utilities.BFM;
import com.source.services.ServeFile;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;


public class HttpResponseReportStorageType extends ExportStorageType {
    
    HttpServletResponse response;
    ExportSearchMap exportSearchMap = null;

    public HttpResponseReportStorageType(HttpServletResponse response) {
        this.response = response;
    }
    
    public HttpResponseReportStorageType(HttpServletResponse response, ExportSearchMap exportSearchMap) {
        this(response);
        this.exportSearchMap = exportSearchMap;
    }

    @Override
    public void store(ExportedSource source) throws Exception {
        if(source instanceof StreamExportedSource) {
            ServeFile serveService = new ServeFile();
            String filename = exportSearchMap.getFilename();
            String ext;
            
            if(filename!=null) {
                String[] parts = filename.split(".");
                ext = parts.length>0? parts[parts.length-1]: "txt";
            } else {
                filename = "sample.txt";
                ext = "txt";
            }
            
            try {
                serveService.serve(((StreamExportedSource) source).getReaderStream(), filename, response, ext);
            } catch (IOException ex) {
                Logger.getLogger(HttpResponseReportStorageType.class.getName()).error(ex);
                ex.printStackTrace(System.err);
            }
        } else {
            //This will forever be the same
            throw new UnsupportedOperationException("Not possible to convert non stream type of exported source to http response"); //To change body of generated methods, choose Tools | Templates.
        }
    }

}
