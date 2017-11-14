

package com.Manoj.framework.exporter.field;


public class ToStringModifier implements ReportFieldModifier{

    @Override
    public String getFieldValue(Object value, String path, Object row) {
        return value.toString();
    }

}
