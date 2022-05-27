package utils;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * 现在使用hutool的请求工具替换该工具类
 */
@Deprecated
public class HttpUtils {

    /**
     * get请求
     *
     * @throws Exception
     */
    public static final String sendGet(String url, String param) throws Exception {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            // 获得结果：{"code":0,"msg":"/1_getpositionqueue.xml"}
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                throw e2;
            }
        }
        return result;
    }

    /**
     * 通过HttpUrlConnection的方式发送Post请求
     * @param urlPath
     * @param param
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String doPost(String urlPath, String param)
            throws IllegalStateException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);

        connection.setRequestProperty("Content-Type", "application/json");// 以json的方式传递参数
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Charsert", "UTF-8");

        // POST请求
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        //System.out.println(bean.toString());
        if (param != null) {
            out.write(param.getBytes("UTF-8"));// 参数需要json格式(其实就是一个字符串)
        }
        out.flush();
        out.close();
        
        String result = "";
        if (connection.getResponseCode() == 200) {
            // 读取响应
            result = ConvertStream2Json(connection.getResponseCode(), connection.getInputStream());
        } else {
            result = ConvertStream2Json(connection.getResponseCode(), connection.getErrorStream());
        }
        // 断开连接
        connection.disconnect();
        return result;

    }

    /**
     * 通过HttpUrlConnection的方式发送Post请求
     * @param urlPath
     * @param param
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String doPost(String urlPath, String param, Map<String, String> headMap)
            throws IllegalStateException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);

        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json");// 以json的方式传递参数
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Charsert", "UTF-8");

        if (null != headMap && !headMap.isEmpty()) {
            headMap.forEach(connection::setRequestProperty);
        }

        // POST请求
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        //System.out.println(bean.toString());
        if (param != null) {
            out.write(param.getBytes("UTF-8"));// 参数需要json格式(其实就是一个字符串)
        }
        out.flush();
        out.close();

        String result = "";
        if (connection.getResponseCode() == 200) {
            // 读取响应
            result = ConvertStream2Json(connection.getResponseCode(), connection.getInputStream());
        } else {
            result = ConvertStream2Json(connection.getResponseCode(), connection.getErrorStream());
        }
        // 断开连接
        connection.disconnect();
        return result;

    }


    public static String doPost(String urlPath, String param,String sign)
            throws IllegalStateException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);

        connection.setRequestProperty("Content-Type", "application/json");// 以json的方式传递参数
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Charsert", "UTF-8");

        if(StringUtils.isNotEmpty(sign)){
            connection.setRequestProperty("sign", sign);
        }

        // POST请求
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        //System.out.println(bean.toString());
        if (param != null) {
            out.write(param.getBytes("UTF-8"));// 参数需要json格式(其实就是一个字符串)
        }
        out.flush();
        out.close();

        String result = "";
        if (connection.getResponseCode() == 200) {
            // 读取响应
            result = ConvertStream2Json(connection.getResponseCode(), connection.getInputStream());
        } else {
            result = ConvertStream2Json(connection.getResponseCode(), connection.getErrorStream());
        }
        // 断开连接
        connection.disconnect();
        return result;

    }

    /**
     * 将流转换成String
     *
     * @param inputStream
     * @return
     * @throws IOException 
     */
    private static String ConvertStream2Json(int code, InputStream inputStream) throws IOException {
        String jsonStr = "";
        if (inputStream == null) {
        	return "ResponseCode:" + code;
        }
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
        } catch (IOException e) {
            throw e;
        }
        return jsonStr;
    }
    
    /**
     * 通过HttpUrlConnection的方式发送Post请求
     * @param urlPath
     * @param param
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String doPostWithResponseCode(String urlPath, String param)
            throws IllegalStateException, IOException,
            NoSuchAlgorithmException, KeyManagementException {
        URL url = new URL(urlPath);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);

        connection.setRequestProperty("Content-Type", "application/json");// 以json的方式传递参数
        connection.setInstanceFollowRedirects(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(30000);
        connection.setRequestProperty("Charsert", "UTF-8");

        // POST请求
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        //System.out.println(bean.toString());
        if (param != null) {
            out.write(param.getBytes("UTF-8"));// 参数需要json格式(其实就是一个字符串)
        }
        out.flush();
        out.close();
        
        String result = "";
        if (connection.getResponseCode() == 200) {
            // 读取响应
            result = ConvertStreamAndResponseCode2Json(connection.getResponseCode(), connection.getInputStream());
        } else {
            result = ConvertStreamAndResponseCode2Json(connection.getResponseCode(), connection.getErrorStream());
        }
        // 断开连接
        connection.disconnect();
        return result;

    }
    /**
     * 将流转换成String
     *
     * @param inputStream
     * @return
     * @throws IOException 
     */
    private static String ConvertStreamAndResponseCode2Json(int code, InputStream inputStream) throws IOException {
        String jsonStr = "";
        if (inputStream == null) {
        	return "ResponseCode:" + code;
        }
        // ByteArrayOutputStream相当于内存输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        // 将输入流转移到内存输出流中
        try {
            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, len);
            }
            // 将内存流转换为字符串
            jsonStr = new String(out.toByteArray());
            if (StringUtils.isNotEmpty(jsonStr)) {
            	JSONObject result = JSONUtil.parseObj(jsonStr);
            	result.put("responseCode", code);
            	jsonStr = JSONUtil.toJsonStr(result);
			}
        } catch (IOException e) {
            throw e;
        }
        return jsonStr;
    }
}
