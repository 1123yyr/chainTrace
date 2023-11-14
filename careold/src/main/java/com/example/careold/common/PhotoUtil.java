package com.example.careold.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.util.ExceptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

//import java.util.Base64;

/**
 * 功能：图片操作工具类
 *
 */
public class PhotoUtil {

    private PhotoUtil(){}

    private static final Logger LOG = LoggerFactory.getLogger(PhotoUtil.class);
    /**
     * jpg/jpeg
     */
    private static final String TYPE_JPG = "jpg";
    private static final String TYPE_PNG = "png";
    private static final String TYPE_BMP = "bmp";
    private static final String TYPE_UNKNOWN = "unknown";

        /**
         *
         * 功能描述 ： 保存图片
         * @create
         * @param filedata 文件数据
         *@return　String 返回图片位置
         */
        public static Map<String,String> saveFile(MultipartFile filedata, String path) {
            Map<String,String> result = new HashMap<>(16);
            String picType = getPicType(filedata);
            if(TYPE_UNKNOWN.equals(picType)){
                LOG.info("照片存储失败，不支持此格式的图片。");
                result.put(DefineData.ERROR_CODE,DefineData.PHOTO_SAVE_FAILED);
                result.put(DefineData.ERROR_MESSAGE,"照片存储失败，不支持此格式的图片。");
                return result;
            }
            //获取图片保存路径
            String pathval = path;
            /* 构建文件目录 */
            File fileDir = new File(pathval);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //上传的文件名
            String rfilename=filedata.getOriginalFilename();
            //文件的扩展名
            String extensionName = rfilename.substring(rfilename.lastIndexOf('.') + 1);
//            long timsMillis=System.currentTimeMillis();
            if(!extensionName.equals(picType)){
                LOG.info("照片格式恶意修改!");
                result.put(DefineData.ERROR_CODE,DefineData.PHOTO_SAVE_FAILED);
                result.put(DefineData.ERROR_MESSAGE,"照片格式恶意修改");
                return result;
            }
            try (FileOutputStream  out= new FileOutputStream(pathval + rfilename)){
                // 写入文件

                out.write(filedata.getBytes());
                out.flush();
                LOG.info("照片存储成功");
            } catch (IOException e) {
                LOG.info(e.getStackTrace()[0].toString());
            }
            result.put(DefineData.ERROR_CODE,DefineData.PHOTO_SAVE_SUCCESS);

            result.put(DefineData.ERROR_MESSAGE, pathval + rfilename);
            return result;
        }

    /**
     * 功能：byte数组转换成16进制字符串
     *
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes){
        StringBuilder stringBuilder = new StringBuilder();
        if (bytes == null || bytes.length <= 0) { return TYPE_UNKNOWN; }
        for(byte b:bytes){
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 功能：根据文件流判断图片类型
     * @param filedata
     * @return jpg/png/gif/bmp
     */
    private static String getPicType(MultipartFile filedata) {
        //读取文件的前几个字节来判断图片格式
        String typeJpg = "FFD8FF";
        String typePng = "89504E47";
        String typeBmp = "424D";

        try {
            InputStream inputStream = filedata.getInputStream();
            byte[] b = new byte[10];
            int sum = inputStream.read(b);
            if(sum<10){
                return TYPE_UNKNOWN;
            }
            String type = bytesToHexString(b).toUpperCase();
            if (type.contains(typeJpg)) {
                return TYPE_JPG;
            } else if (type.contains(typePng)) {
                return TYPE_PNG;
            } else if (type.contains(typeBmp)) {
                return TYPE_BMP;
            }else{
                return TYPE_UNKNOWN;
            }
        } catch (IOException e) {
//            LOG.info(ExceptionUtil.exception2string(e));
            return TYPE_UNKNOWN;
        }catch (NullPointerException ne){
            return TYPE_UNKNOWN;
        }
    }

    /**
     * 将图片转换成Base64编码
     * @param imgFile 待处理图片
     * @return
     */
    public static String getImgStr(String imgFile){
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try
        {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return new String(Base64.encodeBase64(data));
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @param imgStr 图片数据
     * @param imgFilePath 保存图片全路径地址
     * @return
     */
    public static boolean generateImage(String imgStr,String imgFilePath){
        //
        if (imgStr == null) //图像数据为空
            return false;

        try
        {
            //Base64解码
            byte[] b = Base64.decodeBase64(imgStr);
            for(int i=0;i<b.length;++i)
            {
                if(b[i]<0)
                {//调整异常数据
                    b[i]+=256;
                }
            }
            //生成jpeg图片

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
