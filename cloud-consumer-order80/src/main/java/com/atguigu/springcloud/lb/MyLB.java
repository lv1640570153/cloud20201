package com.atguigu.springcloud.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
//@Component
public class MyLB implements LoadBalancer {
    private AtomicInteger atomicInteger=new AtomicInteger(0);
    public final int getAntIncrement(){
        int current;
        int next;
        do{
            current=atomicInteger.get();
            next=current>222222?0:current++;
        }while (!atomicInteger.compareAndSet(current,next));
        System.out.println("第几次访问："+next);
        return current;
    }

    @Override
    public ServiceInstance instance(List<ServiceInstance> serviceInstances) {
        int index=getAntIncrement()%serviceInstances.size();
        return serviceInstances.get(index);
    }
}
