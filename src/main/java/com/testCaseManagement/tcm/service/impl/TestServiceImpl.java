package com.testCaseManagement.tcm.service.impl;

import com.testCaseManagement.tcm.service.TestService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public void test(){
        Mono<String> mono = WebClient
                //创建WenClient实例
                .create()
                //方法调用，WebClient中提供了多种方法
                .method(HttpMethod.GET)
                //请求url
                .uri("http://localhost:7777/test")
                //获取响应结果
                .retrieve()
                //将结果转换为指定类型
                .bodyToMono(String.class);
        //block方法返回最终调用结果，block方法是阻塞的
        System.out.println("响应结果：" + mono.block());
    }
}
