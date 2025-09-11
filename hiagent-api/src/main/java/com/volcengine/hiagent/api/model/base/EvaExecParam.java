package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaExecParam{
    @Nullable
    private String Name; 
    private boolean Required; 
    private boolean Builtin; 
    @Nullable
    private String Source; 
    @Nullable
    private String Field; 
    @Nullable
    private String JsonPath;

    public EvaExecParam() {
    }

    public EvaExecParam(@Nullable String name, boolean required, boolean builtin, @Nullable String source, @Nullable String field, @Nullable String jsonPath) {
        Name = name;
        Required = required;
        Builtin = builtin;
        Source = source;
        Field = field;
        JsonPath = jsonPath;
    }

    @Nullable
    public String getName() {
        return Name;
    }

    public void setName(@Nullable String name) {
        Name = name;
    }

    public boolean isRequired() {
        return Required;
    }

    public void setRequired(boolean required) {
        Required = required;
    }

    public boolean isBuiltin() {
        return Builtin;
    }

    public void setBuiltin(boolean builtin) {
        Builtin = builtin;
    }

    @Nullable
    public String getSource() {
        return Source;
    }

    public void setSource(@Nullable String source) {
        Source = source;
    }

    @Nullable
    public String getField() {
        return Field;
    }

    public void setField(@Nullable String field) {
        Field = field;
    }

    @Nullable
    public String getJsonPath() {
        return JsonPath;
    }

    public void setJsonPath(@Nullable String jsonPath) {
        JsonPath = jsonPath;
    }
}
