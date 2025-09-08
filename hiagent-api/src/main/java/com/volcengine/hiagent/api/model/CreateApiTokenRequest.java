package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;

public class CreateApiTokenRequest {
  @SerializedName("WorkspaceID")
  private String workspaceID = null;

  @SerializedName("CustomAppID")
  private String customAppID = null;

  public String getWorkspaceID() {
    return workspaceID;
  }

  public String getCustomAppID() {
    return customAppID;
  }

  public void setWorkspaceID(String workspaceID) {
    this.workspaceID = workspaceID;
  }

  public void setCustomAppID(String customAppID) {
    this.customAppID = customAppID;
  }
}
