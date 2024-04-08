package com.yuanshuaicn.utils;

import java.io.*;

/**
 * @program: rise
 * @description:
 * @author: yuanshuai
 * @create: 2024-04-08 17:53
 **/
public class ByteArray {
    private byte[] data;
    private int length;

    public ByteArray() {
        length = 0;
        data = new byte[length];
    }

    public ByteArray(byte[] ba) {
        data = ba;
        length = ba.length;
    }

    /**
     * 合并数组
     */
    public void cat(byte[] second, int offset, int length) {

        if (this.length + length > data.length) {
            int allocatedLength = Math.max(data.length, length);
            byte[] allocated = new byte[allocatedLength << 1];
            System.arraycopy(data, 0, allocated, 0, this.length);
            System.arraycopy(second, offset, allocated, this.length, length);
            data = allocated;
        } else {
            System.arraycopy(second, offset, data, this.length, length);
        }

        this.length += length;
    }

    public void cat(byte[] second) {
        cat(second, 0, second.length);
    }

    public byte[] getArray() {
        if (length == data.length) {
            return data;
        }

        byte[] ba = new byte[length];
        System.arraycopy(data, 0, ba, 0, this.length);
        data = ba;
        return ba;
    }

    public int getLength() {
        return length;
    }

    /**
     * 此文件是将byte[] 转换成文件存储到指定路径的
     *
     * @param arr
     * @param value
     */
    public static void convertByteArrayToFile(byte[] arr, String value, String path) {
        try (
                BufferedInputStream bis = new BufferedInputStream(new ByteArrayInputStream(arr));
                //这里是转换以后的文件存储的路径
                FileOutputStream fileOutputStream = new FileOutputStream(path + value);
                BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream)
        ) {
            int data;
            while ((data = bis.read()) != -1) {
                bos.write(data);
            }
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
