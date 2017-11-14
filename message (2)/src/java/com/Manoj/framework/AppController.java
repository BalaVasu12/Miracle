/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Manoj.framework;

import com.Manoj.framework.exporter.service.ExportSearchMap;
import com.Manoj.framework.exporter.service.Exporter;
import com.Manoj.framework.exporter.storage.HttpResponseReportStorageType;
import com.Manoj.framework.exporter.type.ExcelExport;
import com.Manoj.framework.exporter.type.PdfExport;
import com.Manoj.framework.utilities.BFM;
import com.Manoj.framework.utilities.SFM;
import com.source.services.AuthService;
import com.source.services.ServeFile;
import com.monitorjbl.json.JsonView;
import com.source.dto.UserWithPrivilege;
import com.source.entities.User;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AppController {
    
    @Autowired
    AuthService auth;
    
    @Autowired
    ServeFile fileService;
    
    protected Session getSession() {
        return SFM.openSession();
    }
    
    protected User getLoggedInUser() {
        return AuthService.getLoggedInEmployee();
    }
    
    protected UserWithPrivilege getLoggedInDetails() {
        return AuthService.getLoggedInDetails();
    }
    
    protected void exportAsExcel(List data, ExportSearchMap exportSearchMap, HttpServletResponse response) throws Exception {
        Exporter exporter = (Exporter) BFM.getBean("exporterService");
        ExcelExport excelExport = new ExcelExport(exportSearchMap.getReportType());
        exportSearchMap.setFilename(excelExport.getExportName());
        exporter.export(data, exportSearchMap, excelExport, new HttpResponseReportStorageType(response, exportSearchMap));
    }
    
    protected void exportAsExcel(JsonView data, ExportSearchMap exportSearchMap, HttpServletResponse response) throws Exception {
        exportAsExcel((List) data.getValue(), exportSearchMap, response);
    }
    
    protected void exportAsPdf(List data, ExportSearchMap exportSearchMap, HttpServletResponse response) throws Exception {
        Exporter exporter = (Exporter) BFM.getBean("exporterService");
        PdfExport pdfExport = new PdfExport(exportSearchMap.getReportType());
        exportSearchMap.setFilename(pdfExport.getExportName());
        exporter.export(data, exportSearchMap, pdfExport, new HttpResponseReportStorageType(response, exportSearchMap));
    }
    
    protected void exportAsPdf(JsonView data, ExportSearchMap exportSearchMap, HttpServletResponse response) throws Exception {
        exportAsPdf((List) data.getValue(), exportSearchMap, response);
    }
    
    protected void serveFile(File file, String type, String filename, HttpServletResponse response) throws IOException {
        fileService.serve(file, filename, response, type);
    }
}
