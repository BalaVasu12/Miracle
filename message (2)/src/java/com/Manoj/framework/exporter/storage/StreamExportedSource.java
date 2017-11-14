

package com.Manoj.framework.exporter.storage;

import java.io.InputStream;
import java.io.OutputStream;


public interface StreamExportedSource extends ExportedSource {

    InputStream getReaderStream();
    OutputStream getWriterStream();
    
}
