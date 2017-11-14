

package com.Manoj.framework.exporter.service;

import com.Manoj.framework.exporter.field.ReportField;
import java.util.List;
import java.util.Map;


public interface ReportType {

    String getName();
    Class getApplicableClass();
    
    //This should return path => Name of field
    List<ReportField> getFields();
}
