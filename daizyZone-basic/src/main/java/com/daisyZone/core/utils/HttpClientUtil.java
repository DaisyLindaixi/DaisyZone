package com.daisyZone.core.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;

/**
 * HttpClient调用远程API
 * 
 * 为了让阿里云上的项目能正常访问公司内网数据库
 * 
 * @author jiaqi
 * @date 2016年11月16日 下午1:27:50
 * @version V1.0.0
 */
public class HttpClientUtil {

  private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

  /**
   * 根据url调用远程API返回
   * 
   * @author jiaqi
   * @param url
   * @return String
   * @throws Exception
   */
  public static String doGet(String url) throws Exception {
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse httpResponse = null;
    String result = null;
    try {
      httpClient = HttpClientBuilder.create().build();
      // 设置请求和传输超时时间
      RequestConfig requestConfig =
          RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
      HttpGet httpGet = new HttpGet(url);
      httpGet.setConfig(requestConfig);
      httpGet.addHeader("content-type", "application/json");
      httpGet.addHeader("Accept", "application/json");

      httpResponse = httpClient.execute(httpGet);
      StatusLine statusLine = httpResponse.getStatusLine();
      HttpEntity httpEntity = httpResponse.getEntity();
      result = EntityUtils.toString(httpEntity);
      if (statusLine.getStatusCode() >= 300) {
        JsonObject returnData = new JsonParser().parse(result).getAsJsonObject();
        throw new Exception(returnData.get("message").toString());
      }
      if (httpEntity == null) {
        throw new Exception("连接失败，无响应结果");
      }
    } finally {
      if (httpClient != null) {
        try {
          httpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      if (httpResponse != null) {
        try {
          httpResponse.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return result;
  }

  /**
   * 发送post请求
   * 
   * @author jiaqi
   * @param url
   * @param stringObj 请求参数JSON
   * @return
   * @throws Exception
   */
  public static String doPost(String url, String stringObj) throws Exception {
    CloseableHttpClient httpClient = null;
    CloseableHttpResponse httpResponse = null;
    String result = null;

    // 封装数据
    try {
      httpClient = HttpClientBuilder.create().build();
      HttpPost httpPost = new HttpPost(url);
      // 设置请求和传输超时时间
      RequestConfig requestConfig =
          RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
      httpPost.setConfig(requestConfig);

      httpPost.addHeader("Content-Type", "application/json");
      httpPost.addHeader("Accept", "application/json");
      httpPost.setEntity(new StringEntity(stringObj));

      httpResponse = httpClient.execute(httpPost);
      int code = httpResponse.getStatusLine().getStatusCode();
      HttpEntity httpEntity = httpResponse.getEntity();
      if (code == 200) {
        result = EntityUtils.toString(httpEntity);
      } else {
        JsonObject returnData = new JsonParser().parse(result).getAsJsonObject();
        throw new Exception(returnData.get("message").toString());
      }
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      throw new Exception(e);
    } catch (ClientProtocolException e) {
      e.printStackTrace();
      throw new Exception(e);
    } catch (IOException e) {
      e.printStackTrace();
      throw new Exception(e);
    } finally {
      if (httpClient != null) {
        try {
          httpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
          throw new Exception(e);
        }
      }
      if (httpResponse != null) {
        try {
          httpResponse.close();
        } catch (IOException e) {
          e.printStackTrace();
          throw new Exception(e);
        }
      }
    }

    return result;

  }

  /**
   * 异步post请求
   * 
   * @author huangjingqing
   * @param url
   * @return
   * @throws Exception
   */
  public static String doAsyncGet(String url) throws Exception {
    CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
    String result = null;
    final CountDownLatch latch = new CountDownLatch(1);
    httpClient.start();
    HttpGet httpGet = new HttpGet(url);
    // 设置请求和传输超时时间
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).build();
    // RequestConfig requestConfig =
    // RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
    httpGet.setConfig(requestConfig);
    httpGet.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
    httpGet.addHeader(HttpHeaders.ACCEPT, "application/json");
    httpClient.execute(httpGet, new FutureCallback<HttpResponse>() {
      public void completed(final HttpResponse response) {
        latch.countDown();
        LOGGER.debug(httpGet.getRequestLine() + "->" + response.getStatusLine());
        try {
          String content = EntityUtils.toString(response.getEntity(), "UTF-8");
          LOGGER.debug(" response content is : " + content);
          close(httpClient);
        } catch (IOException e) {
          LOGGER.error("异步请求成功，尝试获取返回信息发生io异常", e);
        }
      }

      public void failed(final Exception ex) {
        latch.countDown();
        LOGGER.error("异步请求失败" + httpGet.getRequestLine() + "->" + ex);
        close(httpClient);
      }

      private void close(CloseableHttpAsyncClient httpClient) {
        try {
          httpClient.close();
        } catch (IOException e) {
          LOGGER.error("异步请求关闭发生io异常", e);
        }
      }

      public void cancelled() {
        latch.countDown();
        LOGGER.warn("异步请求取消" + httpGet.getRequestLine() + " cancelled");
        close(httpClient);
      }

    });
    return result;
  }
  
  /**
   * 异步post请求
   * 
   * @author huangjingqing
   * @param url
   * @param stringObj
   * @return
   * @throws Exception
   */
  public static String doAsyncPost(String url, String stringObj) throws Exception {
    CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
    String result = null;
    final CountDownLatch latch = new CountDownLatch(1);
    httpClient.start();
    HttpPost httpPost = new HttpPost(url);
    // 设置请求和传输超时时间
    RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(5000).build();
    // RequestConfig requestConfig =
    // RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
    httpPost.setConfig(requestConfig);
    httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");
    httpPost.addHeader(HttpHeaders.ACCEPT, "application/json");
    // 有body设置body
    if (StringUtils.isNotEmpty(stringObj)) {
      StringEntity entity = new StringEntity(stringObj, "UTF-8");
      httpPost.setEntity(entity);
    }
    httpClient.execute(httpPost, new FutureCallback<HttpResponse>() {
      public void completed(final HttpResponse response) {
        latch.countDown();
        LOGGER.debug(httpPost.getRequestLine() + "->" + response.getStatusLine());
        try {
          String content = EntityUtils.toString(response.getEntity(), "UTF-8");
          LOGGER.debug(" response content is : " + content);
          close(httpClient);
        } catch (IOException e) {
          LOGGER.error("异步请求成功，尝试获取返回信息发生io异常", e);
        }
      }

      public void failed(final Exception ex) {
        latch.countDown();
        LOGGER.error("异步请求失败" + httpPost.getRequestLine() + "->" + ex);
        close(httpClient);
      }

      private void close(CloseableHttpAsyncClient httpClient) {
        try {
          httpClient.close();
        } catch (IOException e) {
          LOGGER.error("异步请求关闭发生io异常", e);
        }
      }

      public void cancelled() {
        latch.countDown();
        LOGGER.warn("异步请求取消" + httpPost.getRequestLine() + " cancelled");
        close(httpClient);
      }

    });
    return result;
  }

  public static void main(String[] args) throws Exception {
    // 测试异步post
    // Map<String, Object> paramsMap = new HashMap<String, Object>();
    // Calendar calendar = Calendar.getInstance();
    // Date endDate = calendar.getTime();
    // calendar.add(Calendar.DATE, -20);
    // Date beginDate = calendar.getTime();
    // paramsMap.put("beginDate", beginDate);
    // paramsMap.put("endDate", endDate);
    // paramsMap.put("isCover", 0);
    // paramsMap.put("companyCode", null);
    // paramsMap.put("pids", null);
    // Gson gsonObj = new GsonBuilder().setDateFormat(ConstantCore.FORMAT_TYPE_DATETIME).create();
    // String jsonString = gsonObj.toJson(paramsMap);
    // String url =
    // "http://localhost:8084/weic_indicator_synch/indicatorUIApi/synchTimePeriodApply";
    // HttpClientUtil.doAsyncPost(url, jsonString);
    // HttpClientUtil.doAsyncPost(url, jsonString);
    HttpClientUtil.doAsyncPost("http://192.168.50.150:8071/api/push",
        "{\"title\":\"新的流程待办\",\"alert\":\"[龚金风]定义名称：陕西新华请假流程流程环节[行政部归档]\",\"otherMsg\":{\"taskInstId\":\"5812511\"},\"userIds\":[352176479309664256],\"clientCode\":\"WEIC\"}");
  }

}
