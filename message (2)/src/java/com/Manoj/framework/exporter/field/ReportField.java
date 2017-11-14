

package com.Manoj.framework.exporter.field;

import java.util.Objects;


public class ReportField {

    String name;
    String path;
    ReportFieldModifier modifier;

    public ReportField(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public ReportField(String name, String path, ReportFieldModifier modifier) {
        this.name = name;
        this.path = path;
        this.modifier = modifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ReportFieldModifier getModifier() {
        return modifier;
    }

    public void setModifier(ReportFieldModifier modifier) {
        this.modifier = modifier;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.path);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportField other = (ReportField) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.path, other.path)) {
            return false;
        }
        if (!Objects.equals(this.modifier, other.modifier)) {
            return false;
        }
        return true;
    }
    
    
}
