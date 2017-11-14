

package com.source.utilities;

import com.Manoj.framework.utilities.BFM;
import com.source.exceptions.StorageSystemException;
import java.io.File;
import java.io.FileNotFoundException;
import org.jboss.logging.Logger;


public class CommonUtils {

    public static String getStorageFolder(String moduleName, String id, boolean relativePath) throws StorageSystemException {
        String basePath = (String) BFM.getBean("fileStoragePath");
        File directory = new File(basePath +File.separator+ moduleName);
        if (!directory.exists()) {
            if(!directory.mkdir())
                throw new StorageSystemException("Cannot create base path for "+moduleName);
        }

        if(id!=null && !id.trim().equals("")) {
            directory = new File(basePath + File.separator+moduleName + File.separator + id);
            if (!directory.exists()) {
                if(!directory.mkdir())
                    throw new StorageSystemException("Cannot create path for "+moduleName+" / "+id);
            }
        }
        
        return relativePath? directory.getPath()+File.separator:directory.getAbsolutePath()+File.separator;
    }
    
    public static String getStorageFolder(String moduleName, String id) throws StorageSystemException {
        return getStorageFolder(moduleName, id, false);
    }
    public static String getStorageFolder(String moduleName) throws StorageSystemException {
        return getStorageFolder(moduleName, null);
    }
    
    public static String getFileUrl(String type, String moduleName, String id, String fileName) {
        return "/files/"+type+"/"+moduleName+"/"+id+"/"+fileName;
    }
    
    public static File getFileFromUrl(String url) throws FileNotFoundException {
        String[] parts = url.split("\\/");
        if(parts.length == 0)
            throw new FileNotFoundException("Could not find "+url);
        int startingIndex = url.startsWith("/")?1:0;
        if(parts[startingIndex].equals("files")) 
            startingIndex ++;
        
        if(parts.length<startingIndex+4) {
            throw new FileNotFoundException("Could not search "+url);
        }
        
        String moduleName = parts[startingIndex+1],
                id = parts[startingIndex+2],
                filename = parts[startingIndex+3];
        
        String folder;
        try {
            folder = getStorageFolder(moduleName, id);
            Logger.getLogger(CommonUtils.class).debug(folder + File.separator + filename);
            File file = new File(folder + File.separator + filename);
            return file;
        } catch (StorageSystemException ex) {
            ex.printStackTrace(System.err);
            throw new FileNotFoundException(ex.getMessage());
        }
    }
    
    public static void deleteAllFilesInFolder(String path) {
        File dir = new File(path);
        if(!dir.isDirectory())
            return ;
        
        File[] files = dir.listFiles();
        for(int i=0; i<files.length; i++) {
            if(files[i].isFile())
                files[i].delete();
        }
    }
}
