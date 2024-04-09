package com.yuanshuaicn.utils;

import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.net.URL;

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

    /**
     * 文件转为二进制数组
     * @param file
     * @return
     */
    public static byte[] fileToBinArray(File file){
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            return bytes;
        }catch (Exception ex){
            throw new RuntimeException("transform file into bin Array 出错",ex);
        }
    }

    /**
     * 文件转为二进制字符串
     * @param file
     * @return
     */
    public static String fileToBinStr(File file){
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            return new String(bytes,"ISO-8859-1");
        }catch (Exception ex){
            throw new RuntimeException("transform file into bin String 出错",ex);
        }
    }


    /**
     * 二进制字符串转文件
     * @param bin
     * @param fileName
     * @param parentPath
     * @return
     */
    public static File binToFile(String bin,String fileName,String parentPath){
        try {
            File fout = new File(parentPath,fileName);
            fout.createNewFile();
            byte[] bytes1 = bin.getBytes("ISO-8859-1");
            FileCopyUtils.copy(bytes1,fout);

            //FileOutputStream outs = new FileOutputStream(fout);
            //outs.write(bytes1);
            //outs.flush();
            //outs.close();

            return fout;
        }catch (Exception ex){
            throw new RuntimeException("transform bin into File 出错",ex);
        }
    }

    /**
     * 文件转为二进制数组
     * 等价于fileToBin
     * @param file
     * @return
     */
    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin Array 出错",ex);
        }
        return by;
    }

    /**从URL中获取图片输入流
     * 并创建本地文件
     * @param imageUrl
     * @param savePath
     * @return
     * @throws Exception
     */
    public static File getImageFileFromUrl(String imageUrl, String savePath) throws Exception {
        // 从URL中获取图片输入流
        URL url = new URL(imageUrl);
        InputStream in = url.openStream();


        // 构建保存路径
        String[] split = imageUrl.split("/");
        String fileName = split[split.length - 1];
        File file = new File(savePath + fileName);
        if(!file.exists()) {
            file.createNewFile();
        }

        // 写入文件
        FileOutputStream out = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        out.close();
        in.close();

        return file;
    }

    /**
     * 根据URL地址获取文件
     * 得到file对象
     * @param path URL网络地址
     * @return File
     */
    public static File getFileByHttpURL(String path){
        String newUrl = path.split("[?]")[0];
        String[] suffix = newUrl.split("/");
        //得到最后一个分隔符后的名字
        String fileName = suffix[suffix.length - 1];
        File file = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            file = File.createTempFile("report",fileName);//创建临时文件
            URL urlFile = new URL(newUrl);
            inputStream = urlFile.openStream();
            outputStream = new FileOutputStream(file);

            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead=inputStream.read(buffer,0,8192))!=-1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (null != outputStream) {
                    outputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}
