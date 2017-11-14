

package com.source.controller;

import com.Manoj.exceptions.BaseException;
import com.Manoj.exceptions.ForbiddenException;
import com.Manoj.exceptions.NotFoundException;
import com.Manoj.framework.AppController;
import com.source.exceptions.StorageSystemException;
import com.source.services.ServeFile;
import com.source.utilities.CommonUtils;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileServiceController extends AppController{
    
    @Autowired
    ServeFile fileService;
    
    @RequestMapping("/files/{type}/{module}/{id}/{filename}.{ext}")
    public void serve(@PathVariable("type") String type, @PathVariable("module") String module,
            @PathVariable("id") String id, @PathVariable("filename") String filename, @PathVariable("ext") String ext, 
            HttpServletResponse response) throws StorageSystemException, BaseException {
        
        String filepath = CommonUtils.getStorageFolder(module, id)+filename+"."+ext;
        File targetFile = new File(filepath);
        
        if(!targetFile.exists() || !targetFile.isFile()) 
            throw new NotFoundException("File not found");
        
        if(!targetFile.canRead()) 
            throw new ForbiddenException("This file cannot be accessed");
        
        String exportFilename = null;
        
        try {
            fileService.serve(targetFile, exportFilename, response, type);
        } catch (IOException ex) {
            Logger.getLogger(FileServiceController.class.getName()).warn(ex);
            throw new BaseException("File not accessible: "+ex.getMessage());
        }
    }
}
