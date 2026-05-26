package com.volcengine.hibot.v1.types;

/**
 * IM/SaaS 多租户嵌入场景下指定渠道与对端身份。
 * webchat 主流程创建 Session 时无须填写本结构。
 */
public final class V1SessionPeerParams {
    /** IM 渠道标识（"feishu"/"wecom"/...）。留空时维持 webchat 默认。 */
    public String channel;
    public String peerKind;
    public String peerId;

    public V1SessionPeerParams() {}

    public V1SessionPeerParams(String peerKind, String peerId) {
        this.peerKind = peerKind;
        this.peerId = peerId;
    }

    public V1SessionPeerParams(String channel, String peerKind, String peerId) {
        this.channel = channel;
        this.peerKind = peerKind;
        this.peerId = peerId;
    }
}
