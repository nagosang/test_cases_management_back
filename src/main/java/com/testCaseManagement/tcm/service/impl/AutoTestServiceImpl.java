package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.testCaseManagement.tcm.entity.AutoTestResults;
import com.testCaseManagement.tcm.mapper.AutoTestResultsMapper;
import com.testCaseManagement.tcm.mapper.InterfaceMapper;
import com.testCaseManagement.tcm.service.AutoTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

@Service
public class AutoTestServiceImpl implements AutoTestService {
    @Autowired
    AutoTestResultsMapper autoTestResultsMapper;

    @Autowired
    InterfaceMapper interfaceMapper;

    public AutoTestResults autoTest(HashMap<String,  JSONObject[]> data, String method, String userId, Integer interfaceId){
        try{

            JSONObject[] headerData = data.get("headerData");
            Consumer<HttpHeaders> headersConsumer = httpHeaders -> {
                for (int i=0;i<headerData.length;i++) {
                    httpHeaders.add(headerData[i].get("parameterName").toString(), headerData[i].getString("value"));
                }
            };

            JSONObject[] bodyJSONData = data.get("paramData");
            MultiValueMap<String,String> bodyData = new LinkedMultiValueMap<>();
            for(int i=0;i<bodyJSONData.length;i++){
                bodyData.add(bodyJSONData[i].getString("parameterName"), bodyJSONData[i].getString("value"));
            }

            String url = interfaceMapper.selectById(interfaceId).getInterfaceRoute();

            if (method.equals("get")) {
                Mono<ClientResponse> mono = WebClient
                        //创建WenClient实例
                        .create()
                        //方法调用，WebClient中提供了多种方法
                        .method(HttpMethod.GET)
                        //请求url
                        .uri(url)
                        // Header
                        .headers(headersConsumer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromFormData(bodyData))
//                        .syncBody(bodyData)
                        //获取响应结果
                        .exchange();
                //block方法返回最终调用结果，block方法是阻塞的
                ClientResponse response = mono.block();

                HttpStatus statusCode = response.statusCode(); // 获取响应码
                int statusCodeValue = response.rawStatusCode(); // 获取响应码值
                ClientResponse.Headers headers = response.headers(); // 获取响应头

                // 获取响应体
                Mono<String> resultMono = response.bodyToMono(String.class);
                String body = resultMono.block();

                // 输出结果
//                System.out.println("statusCode：" + statusCode);
//                System.out.println("statusCodeValue：" + statusCodeValue);
//                System.out.println("headers：" + headers.asHttpHeaders());
//                System.out.println("body：" + body);

                AutoTestResults autoTestResults = new AutoTestResults();
                autoTestResults.setInterfaceId(interfaceId);
                autoTestResults.setMethod("get");

                autoTestResults.setStatusCode(statusCode.toString());
                autoTestResults.setStatusCodeValue(statusCodeValue);
                autoTestResults.setHeaders(headers.asHttpHeaders().toString());
                autoTestResults.setBody(body);

                autoTestResults.setTestHeader(JSON.toJSONString(headerData));
                autoTestResults.setTestBody(JSON.toJSONString(bodyJSONData));
                autoTestResults.setTestCookie(JSON.toJSONString(data.get("cookieData")));

                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                autoTestResults.setDate(ft.format(dNow));

                autoTestResults.setTestUser(userId);

                if( autoTestResultsMapper.insert(autoTestResults) > 0 ) {
                    return autoTestResults;
                }
                else {
                    new Exception("发送错误请联系管理员");
                }
            }
            else if (method.equals("post")) {
                Mono<ClientResponse> mono = WebClient
                        //创建WenClient实例
                        .create()
                        //方法调用，WebClient中提供了多种方法
                        .method(HttpMethod.POST)
                        //请求url
                        .uri(url)
                        // Header
                        .headers(headersConsumer)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromFormData(bodyData))
//                        .syncBody(bodyData)
                        //获取响应结果
                        .exchange();
                //block方法返回最终调用结果，block方法是阻塞的
                ClientResponse response = mono.block();

                HttpStatus statusCode = response.statusCode(); // 获取响应码
                int statusCodeValue = response.rawStatusCode(); // 获取响应码值
                ClientResponse.Headers headers = response.headers(); // 获取响应头

                // 获取响应体
                Mono<String> resultMono = response.bodyToMono(String.class);
                String body = resultMono.block();

                // 输出结果
//                System.out.println("statusCode：" + statusCode);
//                System.out.println("statusCodeValue：" + statusCodeValue);
//                System.out.println("headers：" + headers.asHttpHeaders());
//                System.out.println("body：" + body);

                AutoTestResults autoTestResults = new AutoTestResults();
                autoTestResults.setInterfaceId(interfaceId);
                autoTestResults.setMethod("post");

                autoTestResults.setStatusCode(statusCode.toString());
                autoTestResults.setStatusCodeValue(statusCodeValue);
                autoTestResults.setHeaders(headers.asHttpHeaders().toString());
                autoTestResults.setBody(body);

                autoTestResults.setTestHeader(JSON.toJSONString(headerData));
                autoTestResults.setTestBody(JSON.toJSONString(bodyJSONData));
                autoTestResults.setTestCookie(JSON.toJSONString(data.get("cookieData")));

                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
                autoTestResults.setDate(ft.format(dNow));

                autoTestResults.setTestUser(userId);

                if( autoTestResultsMapper.insert(autoTestResults) > 0 ) {
                    return autoTestResults;
                }
                else {
                    new Exception("发送错误请联系管理员");
                }
            }
            else {
                new Exception("此请求方式暂时不支持");
            }
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Boolean confirmTestResult(int id){
        try {
            AutoTestResults autoTestResults =  autoTestResultsMapper.selectById(id);
            autoTestResults.setIsPass(1);
            if(autoTestResultsMapper.updateById(autoTestResults) > 0) {
                return true;
            }
            else{
                new Exception("发生错误");
                return false;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public HashMap<String, Object> getTestResultsList(int interfaceId, int page){
        try {
            QueryWrapper<AutoTestResults> autoTestResultsQueryWrapper = new QueryWrapper<>();
            autoTestResultsQueryWrapper.eq("interfaceId", interfaceId);
            HashMap<String, Object> reObj = new HashMap<>();
            reObj.put("count", autoTestResultsMapper.selectList(autoTestResultsQueryWrapper).size());
            reObj.put("tableData", autoTestResultsMapper.queryTestResultsByPage(interfaceId,page));
            return reObj;
        }
        catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
