

package com.Manoj.framework.exporter.service;

import com.Manoj.framework.search.SearchMap;


public class ExportSearchMap extends SearchMap {
    
    String filename;
    ReportType reportType;

    public ExportSearchMap() {
    }

    public ExportSearchMap(SearchMap searchMap) {
        super(searchMap.getRequestMap(), null, searchMap.getOrder());
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
    }
    
}
