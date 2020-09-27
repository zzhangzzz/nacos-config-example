package com.alibaba.cloud.nacosconfigexample.DTO;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/9/27 2:32 下午
 * info :
 */

@RefreshScope
@ConfigurationProperties(prefix = "user")
public class User implements InitializingBean, DisposableBean {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    /**
     * 标记表明是否是nacos config引起的 修改
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("[after_properties_set()]" + toString());
    }


    @Override
    public void destroy() throws Exception {
        System.out.println("[destory()]" + toString());

    }
}
