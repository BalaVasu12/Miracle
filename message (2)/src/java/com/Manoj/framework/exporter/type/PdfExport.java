

package com.Manoj.framework.exporter.type;

import com.Manoj.framework.exporter.field.HtmlOutputCapableModifier;
import com.Manoj.framework.exporter.field.ReportField;
import com.Manoj.framework.exporter.service.ExportSearchMap;
import com.Manoj.framework.exporter.service.ReportType;
import com.Manoj.framework.exporter.storage.ExportedSource;
import com.Manoj.framework.exporter.storage.FileStreamExportedSource;
import com.Manoj.framework.utilities.BFM;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.jboss.logging.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.resource.XMLResource;


public class PdfExport implements ExportType {
    ReportType reportType;
    Document doc;
    XPath xpath;

    public PdfExport() {
    }

    public PdfExport(ReportType reportType) {
        this.reportType = reportType;
    }

    @Override
    public ExportedSource export(List resultList, ExportSearchMap exportSearchMap) throws Exception {
        File file = File.createTempFile("Report", ".pdf");
        
        ExportedSource source = null;
        
        file.createNewFile();
//        OutputStreamWriter writer = new FileWriter(file);

        ReportType report = exportSearchMap.getReportType();
        if(report == null)
            throw new Exception("Excel export needs a report type. This cannot be null");
        
        File templateFile = BFM.getContext().getResource("\\reportFormats\\pdf_template.html").getFile();
        InputStream templateInputStream = new FileInputStream(templateFile);
        doc = XMLResource.load(templateInputStream).getDocument();
        xpath = XPathFactory.newInstance().newXPath();
        
        writeHeader(report);
        
        try {
            for(Object row: resultList) {
                if(report.getApplicableClass()!=null && !report.getApplicableClass().equals(row.getClass()))
                    throw new Exception("Row's class "+row.getClass()+" and report class "+report.getApplicableClass()+" do not match");

                List<String> values = new ArrayList<>();
                for(ReportField field: report.getFields()) {
                    String fieldValue = extractValue(row, field);
                    values.add(fieldValue);
                }
                writeRow(values);
            }
        } catch (Exception ex) {
            throw ex;
        }
        
        writeTail(report);

        ITextRenderer renderer = new ITextRenderer();
        OutputStream os = new FileOutputStream(file);
        renderer.setDocument(doc, null);
        renderer.layout();
        renderer.createPDF(os);
        os.close();

        source = new FileStreamExportedSource(file);
        
        return source;
    }
    
    

    /* @Override
    public void dispose() {
        
    } */

    @Override
    public String getExportName() {
        String filename = "Report.pdf";
        if(reportType != null && reportType.getName()!=null) {
            filename = reportType.getName();
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            filename+= df.format(Calendar.getInstance().getTime()) +".pdf";
        }
        
        return filename;
    }

    @Override
    public String getExportType() {
        return "pdf";
    }
    
    private String convertValue(Object value, Object row, String fieldName, ReportField field) {
        if(value == null) 
            return "";
        
        if(field.getModifier()==null) 
            return value.toString();
        
        String returnValue;
        
        //This should be implemented. Left for future 
        /*if(field.getModifier() instanceof HtmlOutputCapableModifier) {
            returnValue = ((HtmlOutputCapableModifier) field.getModifier()).getFieldHtmlValue(value, fieldName, row);
        } else {*/
            returnValue = field.getModifier().getFieldValue(value, fieldName, row);
        /*}*/
        
        return returnValue;
    }
    
    private String findValue(Object row, String fieldName, ReportField field) {
        if(fieldName.contains(".")) {
            String[] fieldNameParts = fieldName.split("\\.");
            String name = fieldNameParts[0],
                    path = String.join(".", Arrays.copyOfRange(fieldNameParts, 1, fieldNameParts.length));
            
            try {
                Field rowField = row.getClass().getDeclaredField(name);
                rowField.setAccessible(true);
                Object rowFieldValue = rowField.get(row);
                
                if(path==null || path.equals("")) {
                    return convertValue(rowFieldValue, row, name, field);
                } else {
                    return findValue(rowFieldValue, path, field);
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) {
                return null;
            }
        } else {
            try {
                Field rowField = row.getClass().getDeclaredField(fieldName);
                rowField.setAccessible(true);
                Object rowFieldValue = rowField.get(row);
                return convertValue(rowFieldValue, row, fieldName, field);
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
                return null;
            }
        }
    }

    private String extractValue(Object row, ReportField field) {
        return findValue(row, field.getPath(), field);
    }
    
    private void writeHeader(ReportType report) throws IOException {
        try {
            Node node = (Node) xpath.evaluate("//*[@id='report_title']", doc, XPathConstants.NODE);
            node.setTextContent(report.getName());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String generationDate = format.format(Calendar.getInstance().getTime());
            node = (Node) xpath.evaluate("//*[@id='generation_date']", doc, XPathConstants.NODE);
            node.setTextContent(generationDate);

            Node headerRow = (Node) xpath.evaluate("//*[@id='report_header']", doc, XPathConstants.NODE);
            Node headerFieldTempalte = (Node) xpath.evaluate("//*[@id='header_template']", doc, XPathConstants.NODE);

            for(ReportField field: report.getFields()) {
                Node fieldNode = headerFieldTempalte.cloneNode(true);
                fieldNode.getAttributes().removeNamedItem("id");
                fieldNode.setTextContent(field.getName());
                headerRow.appendChild(fieldNode);
            }
        } catch (XPathExpressionException ex) {
            Logger.getLogger(this.getClass()).error(ex);
            Logger.getLogger(this.getClass()).error("Header not set properly for this pdf report");
            ex.printStackTrace(System.err);
        }
        
    }
    
    private void writeTail(ReportType report) {
        
    }
    
    private void writeRow(List<String> values) throws IOException {
        try {
            Node dataTable = (Node) xpath.evaluate("//*[@id='report_data']", doc, XPathConstants.NODE);

            Node dataRow = ((Node) xpath.evaluate("//*[@id='report_data_row_template']", doc, XPathConstants.NODE)).cloneNode(true);
            dataRow.getAttributes().removeNamedItem("id");

            Node dataFieldTemplate = (Node) xpath.evaluate("//*[@id='report_data_field_template']", doc, XPathConstants.NODE);

            for(String value: values) {
                Node dataNode = dataFieldTemplate.cloneNode(true);
                dataNode.getAttributes().removeNamedItem("id");

                dataNode.setTextContent((value==null?"":value));
                dataRow.appendChild(dataNode);
            }

            dataTable.appendChild(dataRow);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(this.getClass()).error(ex);
            Logger.getLogger(this.getClass()).error("Data not set properly for this pdf report");
            ex.printStackTrace(System.err);
        }
    }
}
