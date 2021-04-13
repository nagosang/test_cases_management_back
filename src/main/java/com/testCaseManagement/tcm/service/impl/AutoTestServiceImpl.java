package com.testCaseManagement.tcm.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.javafaker.Faker;
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
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Service
public class AutoTestServiceImpl implements AutoTestService {
    @Autowired
    AutoTestResultsMapper autoTestResultsMapper;

    @Autowired
    InterfaceMapper interfaceMapper;

    private Faker faker = new Faker();

    public AutoTestResults autoTest(HashMap<String,  ArrayList<JSONObject>> data, String method, String userId, Integer interfaceId){
        try{
            ArrayList<JSONObject> headerData = data.get("headerData");
            Consumer<HttpHeaders> headersConsumer = httpHeaders -> {
                for (int i=0;i<headerData.size();i++) {
                    String value = "";
                    if (headerData.get(i).getString("value") != null){
                        value = headerData.get(i).getString("value");
                    }
                    value = fakeParams(value);
                    httpHeaders.add(headerData.get(i).getString("parameterName"), value);
                    headerData.get(i).put("value", value);
                }
            };

            ArrayList<JSONObject> bodyJSONData = data.get("paramData");
            MultiValueMap<String,String> bodyData = new LinkedMultiValueMap<>();
            for(int i=0;i<bodyJSONData.size();i++){
                String value = "";
                if(bodyJSONData.get(i).getString("value") != null){
                    value = bodyJSONData.get(i).getString("value");
                }
                value = fakeParams(value);
                bodyData.add(bodyJSONData.get(i).getString("parameterName"), value);
                headerData.get(i).put("value",value);
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

    public void autoTestByTimes(HashMap<String, ArrayList<JSONObject>> data, String method, String userId, Integer interfaceId){
        try{
            for (int times =0; times<10;times++){
                ArrayList<JSONObject> headerData = data.get("headerData");
                Consumer<HttpHeaders> headersConsumer = httpHeaders -> {
                    for (int i=0;i<headerData.size();i++) {
                        String value = "";
                        if (headerData.get(i).getString("value") != null){
                            value = headerData.get(i).getString("value");
                        }
                        value = fakeParams(value);
                        httpHeaders.add(headerData.get(i).getString("parameterName"), value);
                        headerData.get(i).put("value", value);
                    }
                };

                ArrayList<JSONObject> bodyJSONData = data.get("paramData");
                MultiValueMap<String,String> bodyData = new LinkedMultiValueMap<>();
                for(int i=0;i<bodyJSONData.size();i++){
                    String value = "";
                    if(bodyJSONData.get(i).getString("value") != null){
                        value = bodyJSONData.get(i).getString("value");
                    }
                    value = fakeParams(value);
                    bodyData.add(bodyJSONData.get(i).getString("parameterName"), value);
                    bodyJSONData.get(i).put("value",value);
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
                    autoTestResults.setIsPass(2);

                    if( autoTestResultsMapper.insert(autoTestResults) > 0 ) {
                    }
                    else {
                        new Exception("发送错误请联系管理员");
                    }
                }
                else {
                    new Exception("此请求方式暂时不支持");
                }
            }
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

    private String fakeParams(String key) {
        // 判断key是否为密码类型
        if(Pattern.matches(".*(password|pwd).*", key)) {
            return faker.internet().password();
        }
        // 账号类型
        if(Pattern.matches(".*(username|account).*", key)) {
            int len = faker.number().numberBetween(5, 20);
            return faker.number().digits(len);
        }
        // 城市名称类型
        if(Pattern.matches(".*(city).*", key)) {
            return faker.address().cityName();
        }
        // 图片类型
        if(Pattern.matches(".*(avatar|img|image|picture).*", key)) {
            return faker.internet().avatar();
        }
        // 日期型
        if(Pattern.matches(".*(date|time|day).*", key)) {
            return String.valueOf(faker.date().past(10, TimeUnit.DAYS));
        }
        // 电话号码型
        if(Pattern.matches(".*(phone).*", key)) {
            return faker.phoneNumber().phoneNumber();
        }
        return key;
    }
}
