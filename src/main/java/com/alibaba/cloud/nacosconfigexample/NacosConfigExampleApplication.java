package com.alibaba.cloud.nacosconfigexample;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacosconfigexample.DTO.User;
import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.StringReader;
import java.util.Properties;

@SpringBootApplication
@RestController
@RefreshScope
@EnableConfigurationProperties(User.class)
@EnableDiscoveryClient
public class NacosConfigExampleApplication {

    /**
     * 分布式配置范例
     * http://139.196.203.133:8848/nacos/?spm=a2ck6.20206201.0.0.74651fd6dVYhTt#/configdetail?serverId=center&dataId=nacos-config-sample-zzz&group=DEFAULT_GROUP&namespace=sandbox-configuration&edasAppName=&searchDataId=nacos-config-sample-zzz&searchGroup=&pageSize=10&pageNo=1
     */
    @Value("${user.name}")
    private String userName;

    @Value("${user.age}")
    private Integer userAge;

    @Autowired
    private User user;

    /**
     * 试用nacos config 实现bean的@Value 属性刷新
     *
     * curl http://127.0.0.1:8080/user
     *
     * 问题 忘记这是一个普通的springboot工程 没加
     * @RestController
     * @RefreshScope
     * 加了之后即可
     */
    @RequestMapping("/user")
    public String user() {
        return String.format("[HTTP] user name : %s, age : %d", userName, userAge);
    }

    @PostConstruct
    public void init() {
        System.out.printf("init user name : %s, age %d%n", userName, userAge);
    }

    @PreDestroy
    public void destory() {
        System.out.printf("[destory] user name : %s, age : %d%n", userName, userAge);
    }


    /**
     * 使用 nacosconfig 坚挺bean 属性动态刷新
     *
     * Autowired 注入 nacosConfigmanager
     * runner中监听 configService 根据dataid+group
     */
    @Autowired
    private NacosConfigManager nacosConfigManager;

    /**
     * 也可以 @NacosValue 支持粒度更新
     * @return
     */
    @Bean
    public ApplicationRunner runner() {
        return args -> {
            String dataId = "nacos-config-sample.properties";
            String group = "DEFAULT_GROUP";
            nacosConfigManager.getConfigService().addListener(dataId, group, new AbstractListener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    System.out.println("[listener------]" + configInfo);

                    System.out.println("[Before] ----" + user);

                    Properties properties = new Properties();
                    try {
                        properties.load(new StringReader(configInfo));
                        String name = properties.getProperty("user.name");
                        int age = Integer.valueOf(properties.getProperty("user.age"));
                        user.setAge(age);
                        user.setName(name);
                    } catch (Exception e) {
                        System.out.println("error----" + e);
                    }

                    System.out.println("[after_listener]-----" + user);
                }
            });
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(NacosConfigExampleApplication.class, args);
    }

}
