

package com.Manoj.framework.search.annotations;

import com.Manoj.framework.search.types.EqualsSearch;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.web.bind.annotation.Mapping;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Mapping
public @interface AutoSearchType {

    public Class type() default EqualsSearch.class;
}
