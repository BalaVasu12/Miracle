

package com.source.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Calendar;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriUtils;

@Service
public class ServeFile {
    
    public void serve(File file, HttpServletResponse response, String type) throws IOException {
        serve(file, null, response, type);
    }

    public void serve(InputStream is, String filename, HttpServletResponse response, String type) throws FileNotFoundException, IOException  {
        if(type!=null) {
            if(type.equals("pdf")) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment");
            } else if(type.equals("txt")) {
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition", "attachment");
            } else if(type.equals("xls")) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment");
            } else if(type.equals("png"))
                response.setContentType("image/png");
        }
        
        if(filename != null) {
            String existingHeader = response.getHeader("Content-Disposition");
            response.setHeader("Content-Disposition", existingHeader+"; filename=\""+filename+"\"");
        }
        /*Calendar expiryTime= Calendar.getInstance();
        expiryTime.add(Calendar.MINUTE, 5);
        response.setHeader("Expires", expiryTime.getTime().toString());
        response.setHeader("Cache-Control", "max-age=600");*/
       
        IOUtils.copy(is, response.getOutputStream());
        try {
            response.getOutputStream().flush();
        } catch (IOException ex) {
            Logger.getLogger(this.getClass()).error(ex);
            ex.printStackTrace();
        }
    }
    
    public void serve(File file, String filename, HttpServletResponse response, String type) throws FileNotFoundException, IOException  {
        serve(new FileInputStream(file), filename, response, type);
    }
}
