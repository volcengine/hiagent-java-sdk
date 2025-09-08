package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.model.AbstractResponse;

public class CreateApiTokenResponse extends AbstractResponse {
  @SerializedName("Token")
  private String token;

  @SerializedName("ExpiresIn")
  private int expiresIn;

  public String getToken() {
    return token;
  }

  public int getExpiresIn() {
    return expiresIn;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setExpiresIn(int expiresIn) {
    this.expiresIn = expiresIn;
  }
}
