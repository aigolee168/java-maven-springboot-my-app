package com.bf.app.constant;

import lombok.Getter;

@Getter
public enum AuthType {
    /**
     * 容器节点,包含容器节点和系统节点
     */
    CONTAINER((byte)0),
    /**
     * 系统节点,包含常规节点
     */
    SYSTEM((byte)1),
    /**
     * 常规节点,可以包含常规节点
     */
    NORMAL((byte)2);
    
    private final byte value;
    
    private AuthType(byte value) {
        this.value = value;
    }
    
}
