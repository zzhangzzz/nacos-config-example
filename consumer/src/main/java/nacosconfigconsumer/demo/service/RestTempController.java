package nacosconfigconsumer.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/9/27 5:04 下午
 * info :  使用 @LoadBalance + RestTemplate 实现服务调用
 */
@RestController
public class RestTempController {

    @LoadBalanced
    @Autowired
    public RestTemplate restTemplate;

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("call/echo/{message}")
    public String callEcho(@PathVariable String message) {
        // 访问provider中的 /echo/{message}
        System.out.println(111);
        return restTemplate.getForObject("http://nacos-config-sample-zzz/echo/" + message, String.class);
    }
}
