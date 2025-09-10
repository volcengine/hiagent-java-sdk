package com.volcengine.hiagent.api.model.eva.base;

public class EvaTaskItemTaskTemplate {
    private String TaskTemplateID;
    private String TaskTemplateName;
    private boolean IsDeleted;

    public EvaTaskItemTaskTemplate() {
    }

    public EvaTaskItemTaskTemplate(String taskTemplateID, String taskTemplateName, boolean isDeleted) {
        TaskTemplateID = taskTemplateID;
        TaskTemplateName = taskTemplateName;
        IsDeleted = isDeleted;
    }

    public String getTaskTemplateID() {
        return TaskTemplateID;
    }

    public void setTaskTemplateID(String taskTemplateID) {
        TaskTemplateID = taskTemplateID;
    }

    public String getTaskTemplateName() {
        return TaskTemplateName;
    }

    public void setTaskTemplateName(String taskTemplateName) {
        TaskTemplateName = taskTemplateName;
    }

    public boolean isDeleted() {
        return IsDeleted;
    }

    public void setDeleted(boolean deleted) {
        IsDeleted = deleted;
    }
}
