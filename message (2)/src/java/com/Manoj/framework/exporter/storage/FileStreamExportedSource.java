

package com.Manoj.framework.exporter.storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.jboss.logging.Logger;


public class FileStreamExportedSource implements StreamExportedSource{
    
    private File outputFile;
    private OutputStream outputStream = null;
    private InputStream inputStream = null;

    public FileStreamExportedSource(File outputFile) {
        this.outputFile = outputFile;
    }

    @Override
    public InputStream getReaderStream() {
        if(inputStream == null) {
            try {
                inputStream = new FileInputStream(outputFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileStreamExportedSource.class).error(ex);
            }
        }
        
        return inputStream;
    }

    @Override
    public OutputStream getWriterStream() {
        if(outputStream == null) {
            try {
                outputStream = new FileOutputStream(outputFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FileStreamExportedSource.class).error(ex);
            }
        }
        
        return outputStream;
    }
    
    

}
