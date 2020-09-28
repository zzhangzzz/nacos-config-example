package nacosconfigconsumer.demo;

import com.alibaba.cloud.EchoService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/* 本地注册服务调用
@SpringBootApplication
@RestController
@EnableDiscoveryClient
*/

/**
 * springcloud dubbo服务注册调用
 */
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
public class ConsumerApplication {

    @Reference
    private EchoService echoService;

    @GetMapping("/echo")
    public String echo(String message) {
        return echoService.echo(message);
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

}
