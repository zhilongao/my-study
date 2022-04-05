package com.charles.im.common.packet;

import lombok.Data;

import java.io.Serializable;

@Data
public abstract class Packet implements Serializable {
    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
