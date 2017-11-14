

package com.Manoj.framework.search;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import org.jboss.logging.Logger;


public class ConversionService implements ConversionServiceInterface{

    @Override
    public Object convert(Class type, String value) {
        if(type == String.class)
            return value;
        else if(type == Integer.class || (type.isPrimitive() && type == Integer.TYPE))
            return Integer.parseInt(value);
        else if(type == Float.class || (type.isPrimitive() && type == Float.TYPE))
            return Float.parseFloat(value);
        else if(type == Long.class || (type.isPrimitive() && type == Long.TYPE))
            return Long.parseLong(value);
        else if(type == Boolean.class || (type.isPrimitive() && type == Boolean.TYPE))
            return Boolean.parseBoolean(value);
        else if(type == Date.class) {
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            try {
                return dateTimeFormat.parse(value);
            } catch (ParseException ex) {
                try  {
                    return dateFormat.parse(value);
                } catch (ParseException ex1) {
                    Logger.getLogger(ConversionService.class.getName()).debug(ex1.getMessage());
                    return null;
                }
            }
        }
        else 
            return type.cast(value);
    }

}
