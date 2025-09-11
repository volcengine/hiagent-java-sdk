package com.volcengine.hiagent.api.model.base;

import org.jetbrains.annotations.Nullable;

public class EvaTaskResultTargetContentPair {
    @Nullable
    private String Content;
    @Nullable
    private String ContentThought;
    private long Round;
    @Nullable
    private String MessageID;
    @Nullable
    private String ConversationID;
    private String Status;
    @Nullable
    private String StatusMessage;
    private long CostTokens;
    private long InferenceDuration;
    private long RuleDuration;
    private long TTFT;

    public EvaTaskResultTargetContentPair() {
    }

    public EvaTaskResultTargetContentPair(@Nullable String content, @Nullable String contentThought, long round, @Nullable String messageID, @Nullable String conversationID, String status, @Nullable String statusMessage, long costTokens, long inferenceDuration, long ruleDuration, long TTFT) {
        Content = content;
        ContentThought = contentThought;
        Round = round;
        MessageID = messageID;
        ConversationID = conversationID;
        Status = status;
        StatusMessage = statusMessage;
        CostTokens = costTokens;
        InferenceDuration = inferenceDuration;
        RuleDuration = ruleDuration;
        this.TTFT = TTFT;
    }

    @Nullable
    public String getContent() {
        return Content;
    }

    public void setContent(@Nullable String content) {
        Content = content;
    }

    @Nullable
    public String getContentThought() {
        return ContentThought;
    }

    public void setContentThought(@Nullable String contentThought) {
        ContentThought = contentThought;
    }

    public long getRound() {
        return Round;
    }

    public void setRound(long round) {
        Round = round;
    }

    @Nullable
    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(@Nullable String messageID) {
        MessageID = messageID;
    }

    @Nullable
    public String getConversationID() {
        return ConversationID;
    }

    public void setConversationID(@Nullable String conversationID) {
        ConversationID = conversationID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    @Nullable
    public String getStatusMessage() {
        return StatusMessage;
    }

    public void setStatusMessage(@Nullable String statusMessage) {
        StatusMessage = statusMessage;
    }

    public long getCostTokens() {
        return CostTokens;
    }

    public void setCostTokens(long costTokens) {
        CostTokens = costTokens;
    }

    public long getInferenceDuration() {
        return InferenceDuration;
    }

    public void setInferenceDuration(long inferenceDuration) {
        InferenceDuration = inferenceDuration;
    }

    public long getRuleDuration() {
        return RuleDuration;
    }

    public void setRuleDuration(long ruleDuration) {
        RuleDuration = ruleDuration;
    }

    public long getTTFT() {
        return TTFT;
    }

    public void setTTFT(long TTFT) {
        this.TTFT = TTFT;
    }
}
