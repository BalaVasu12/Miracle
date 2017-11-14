

package com.Manoj.framework;

import com.monitorjbl.json.JsonView;
import com.monitorjbl.json.JsonViewSerializer;


public class FixedJsonViewSerializer extends JsonViewSerializer{
    
    @Override
    public Class<JsonView> handledType() {
        return JsonView.class;
    }
    
}
