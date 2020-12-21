package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entities.CommonResult;
import com.atguigu.springcloud.entities.Payment;
import com.atguigu.springcloud.service.PaymentService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@RestController
public class PaymentController {
    @Resource
    PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;
    @Resource
    private DiscoveryClient discoveryClient;
    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody  Payment payment){
        int result=paymentService.create(payment);
        System.out.println("*****插入结果："+result);
        int a=1;
        if(result > 0){
            return new CommonResult(200,"插入成功"+serverPort,result);
        }
        else{
            return new CommonResult(444,"插入失败，你失败了");
        }
    }
    @GetMapping(value = "/payment/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        System.out.println("*****插入结果："+payment);
        if (payment!=null){  //说明有数据，能查询成功
            return new CommonResult(200,"查询成功"+serverPort,payment);
        }else {
            return new CommonResult(444,"没有对应记录，查询ID："+id,null);
        }
    }
 /*   @GetMapping(value = "/payment/discovery")
    public Object discovey(){
        List<String> list=discoveryClient.getServices();
        for (String str:
             list) {
            System.out.println("Servcies:"+str);
        }
        List<ServiceInstance> instances=discoveryClient.getInstances("CLOUD-PAYMENT-SERVCIE");
        for (ServiceInstance instance:instances
             ) {
            System.out.println(instance.getServiceId()+"\t"+instance.getHost()+"\t"+instance.getPort()+"\t"+instance.getUri());

        }
        return this.discoveryClient;
    }*/
   /* @GetMapping(value = "/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }*/

}
