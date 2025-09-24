package com.volcengine.hiagent.api.model;

import com.google.gson.annotations.SerializedName;
import com.volcengine.hiagent.api.model.base.ChatEvent;

public class MessageChatEvent extends ChatEvent {

    @SerializedName("answer")
    private String answer;

}
