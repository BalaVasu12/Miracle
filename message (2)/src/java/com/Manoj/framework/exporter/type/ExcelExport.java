

package com.Manoj.framework.exporter.type;

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
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExcelExport implements ExportType {
    ReportType reportType;
    Workbook workbook;
    Sheet worksheet;
    
    private int dataRowIndex = 14;

    public ExcelExport() {
    }

    public ExcelExport(ReportType reportType) {
        this.reportType = reportType;
    }

    @Override
    public ExportedSource export(List resultList, ExportSearchMap exportSearchMap) throws Exception {
        File file = File.createTempFile("Report", ".xlsx");
        
        ExportedSource source = null;
        
        file.createNewFile();
//        OutputStreamWriter writer = new FileWriter(file);

        ReportType report = exportSearchMap.getReportType();
        if(report == null)
            throw new Exception("Excel export needs a report type. This cannot be null");
        
        File templateFile = BFM.getContext().getResource("\\reportFormats\\excel_template.xlsx").getFile();
        this.workbook = new XSSFWorkbook(new FileInputStream(templateFile));
        this.worksheet = this.workbook.getSheetAt(0);
        
        writeHeader(report);
        writeSearchFields(report, exportSearchMap);
        
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
            this.workbook.close();
            throw ex;
        }
        
        writeTail(report);

        OutputStream os = new FileOutputStream(file);
        this.workbook.write(os);
        this.workbook.close();
        os.close();

        source = new FileStreamExportedSource(file);
        
        return source;
    }
    
    

    /* @Override
    public void dispose() {
        
    } */

    @Override
    public String getExportName() {
        String filename = "Report.txt";
        if(reportType != null && reportType.getName()!=null) {
            filename = reportType.getName();
            
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            filename+= df.format(Calendar.getInstance().getTime()) +".xlsx";
        }
        
        return filename;
    }

    @Override
    public String getExportType() {
        return "xlsx";
    }
    
    private String convertValue(Object value, Object row, String fieldName, ReportField field) {
        if(value == null) 
            return "";
        
        if(field.getModifier()==null) 
            return value.toString();
        
        return field.getModifier().getFieldValue(value, fieldName, row);
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
        writeCell(10, 3, report.getName());
        writeCell(7, 10, Calendar.getInstance().getTime());
        
        CellStyle headerStyle = this.workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderBottom(BorderStyle.THICK);
        
        Font font = this.workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        
        int i = 1;
        for(ReportField field: report.getFields()) {
            getCell(12, i).setCellStyle(headerStyle);
            writeCell(12, i++, field.getName());
        }
        
    }
    
    private void writeSearchFields(ReportType report, ExportSearchMap exportSearchMap) {
        Map<String, String[]> requestMap = exportSearchMap.getRequestMap();
        List<String> lines = new ArrayList<>();
        for(Map.Entry<String, String[]> entry: requestMap.entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();
            
            lines.add(sanitizeValue(key, values.length>0?values[0]:"<NA>"));
        }
        
        String searchString = StringUtils.join(lines, ", ");
        if(searchString!=null && !searchString.trim().equals(""))
            searchString = "Exported on: "+searchString;
        else
            searchString = "";
        writeCell(5, 3, searchString);
    }
    
    private String sanitizeName(String fieldName) {
        String[] splitFieldName = fieldName.split("\\.");
        return StringUtils.capitalize(splitFieldName[splitFieldName.length-1]);
    }
    private String sanitizeValue(String field, String value) {
        return sanitizeName(field)+": "+value;
    }
    
    private void writeTail(ReportType report) {
        for(int i=1; i<=report.getFields().size(); i++) {
            this.worksheet.autoSizeColumn(i);
        }
    }
    
    private void writeRow(List<String> values) throws IOException {
        int dataColumnIndex = 1;
        
        for(String value: values) {
            writeCell(dataRowIndex, dataColumnIndex++, (value==null?"":value));
        }
        
        dataRowIndex++;
        
    }
    
    private void writeCell(int row, int column, String value) {
        Cell thisCell = getCell(row, column);
        thisCell.setCellValue(value);
    }
    
    private void writeCell(int row, int column, Date value) {
        Cell thisCell = getCell(row, column);
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        thisCell.setCellValue(format.format(value));
    }
    
    private Cell getCell(int row, int column) {
        Row thisRow = this.worksheet.getRow(row);
        if(thisRow == null) {
            thisRow = this.worksheet.createRow(row);
        }
        
        Cell thisCell = thisRow.getCell(column);
        if(thisCell == null) {
            thisCell = thisRow.createCell(column);
        }
        
        return thisCell;
    }
}
