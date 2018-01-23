package com.xiangshangban.att_simple.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Http通信工具类(用来调用"操作日志")
 */
public class HttpRequestFactory {

    public static void main(String []args){
        Map map = new HashMap<>();
        map.put("operateEmpId","EMPID");
        map.put("operateEmpCompanyId","COMPANYID");
        map.put("operateType","1");
        map.put("operateContent","单元测试");
        sendRequet("http://192.168.0.128:8096/log/operateLog/addOperateLog",map);
    }

    /**
     * 项目间的http通信请求(apache.httpClient)
     * 类似webService时的post方式访问其他的服务
     * @param sendurl
     * @param data
     * @return
     */
    public static String sendRequet(String sendurl, Object data) {
        //创建httpClient客户端
        CloseableHttpClient client = HttpClients.createDefault();
        //设置请求的方式为post（并添加请求的URL）
        HttpPost post = new HttpPost(sendurl);
        //发送JSON格式的请求数据（参数）
        StringEntity myEntity = new StringEntity( JSON.toJSONString(data,false),
                ContentType.APPLICATION_JSON);// 构造请求数据
        post.setEntity(myEntity);// 设置请求体
        String responseContent = null; // 响应内容
        CloseableHttpResponse response = null;
        try {
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取相应的内容
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (client != null)
                        client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseContent;
    }
}
