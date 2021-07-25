package com.study.stu.api;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBufferTest {

    public static void main(String[] args) {
        test1();
    }

    private static void test1() {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(9, 100);
        print("allocate ByteBuf(9, 100)", buffer);

        // write()方法改变写指针,写完之后,写指针如果未到capacity的时候,buffer仍然可写
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        print("writeBytes(1, 2, 3, 4)", buffer);

        // write()方法改变写指针,写完之后,写指针如果未到capacity的时候,buffer仍然可写 写完int,写指针加4
        buffer.writeInt(12);
        print("writeInt(12)", buffer);

        // write()方法改变写指针 写完当写指针等于capacity的时候,buffer不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(5)", buffer);

        // write()方法改变写指针，写时发现buffer不可写开始扩容,扩容之后capacity随即改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(6)", buffer);

        // set()方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte", buffer);

        // read()方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);
    }

    private static void print(String action, ByteBuf buffer) {
        System.err.println("after =====================" + action + "===============");
        System.err.println("capacity():" + buffer.capacity());
        System.err.println("maxCapacity():" + buffer.maxCapacity());
        System.err.println("readerIndex():" + buffer.readerIndex());
        System.err.println("readableBytes():" + buffer.readableBytes());
        System.err.println("isReadable():" + buffer.isReadable());
        System.err.println("writerIndex():" + buffer.writerIndex());
        System.err.println("writableBytes():" + buffer.writableBytes());
        System.err.println("isWritable():" + buffer.isWritable());
        System.err.println("maxWritableBytes():" + buffer.maxWritableBytes());
        System.err.println();
    }

}
