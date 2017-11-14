

package com.Manoj.framework.exporter.field;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DateFieldModifier implements ReportFieldModifier {

    @Override
    public String getFieldValue(Object value, String path, Object row) {
        if(value instanceof Date) {
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa", Locale.ENGLISH);
            return df.format((Date) value);
        } else {
            throw new UnsupportedOperationException("Cannot convert non date objects using this method"); 
        }
    }

}
