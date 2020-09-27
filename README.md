# nacos-config-example
一个分享学习nacos 经历的工程 有错误记录及知识点整理  
先熟悉操作 后添加底层原理记录

训练营原文档地址：  
https://start.aliyun.com/article/sca7lesson/outconfig  
想自己练习的同学可以从文档进入，阿里云也提供了沙盒方便调试


参加了个一下Springcloud alibaba训练营， 因为公司主要是自研框架+一些商用架构相对比较混乱，之前没系统接触过spring-cloud家族，也算是挺好的学习机会  

下文主要是关键节点的记录 + 一些底层概念，方便以后复习  
ps 以前不爱用md，用熟了之后。。。。 真香


#1 基础搭建
1、官网下载nacos （quickstart）
/nacos/bin  sh startup.sh -m standalone

2、新建springboot工程加入nacos / 自己添加依赖  
~~~~   
<groupId>com.alibaba.cloud</groupId>    
<artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>  

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-dependencies</artifactId>
    <version>${spring-cloud-alibaba.version}</version>
    <type>pom</type>
    <scope>import</scope>
</dependency>

~~~~ 

感谢阿里云的公共Nacos Server 可以体验一下远程
 http://139.196.203.133:8848/nacos/   
 查看控制台(账号名/密码为 nacos-configuration/nacos-configuration
 
因为要使用远程配置， 先要修改bootstarp.properties
~~~~
spring.cloud.nacos.config.server-addr=139.196.203.133:8848
spring.cloud.nacos.config.username=nacos-configuration
spring.cloud.nacos.config.password=nacos-configuration
spring.cloud.nacos.config.namespace=sandbox-configuration
~~~~

启动文件类 NacosConfigExampleApplication提供了3种方式  
1、@Value直接读取、动态刷新  
2、实现@ConfigurationProperties 来Bean属性动态刷新  
3、使用Nacos config 监听 Bean属性动态刷行 （也可以@NacosValue

组别的配置
~~~~
# 配置支持自定义扩展的Dataid 配置
# 默认组 不支持动态更新
#spring.cloud.nacos.config.extension-configs[0].data-id=ext-config-common01.properties
#
## 不在默认组 不支持动态更新
#spring.cloud.nacos.config.extension-configs[1].data-id=ext-config-common02.properties
#spring.cloud.nacos.config.extension-configs[1].group=GLOBALE_GROUP
#
## 不再默认组 支持动态更新
#spring.cloud.nacos.config.extension-configs[2].data-id=ext-config-common02.properties
#spring.cloud.nacos.config.extension-configs[2].group=GLOBALE_GROUP
#spring.cloud.nacos.config.extension-configs[2].refresh=true

#spring.cloud.nacos.config.extension-configs[n] n越大，优先级越高
#spring.cloud.nacos.config.extension-configs[n].data-id 必须带文件扩展名，可以properties、yaml/yml
~~~~

配置优先级问题
~~~~
# 配置的优先级
#A: 通过 spring.cloud.nacos.config.shared-configs[n].data-id支持多个共享 Data Id 的配置
#B: 通过 spring.cloud.nacos.config.extension-configs[n].data-id的方式支持多个扩展 Data Id 的配置
#C: 通过内部相关规则(应用名、应用名+ Profile )自动生成相关的 Data Id 配置

#优先级 A < B < C

更多高级配置

配置项	                Key	                                 默认值	                说明
服务端地址	        spring.cloud.nacos.config.server-addr		Nacos Server 启动监听的ip地址和端口
配置对应的 DataId	        spring.cloud.nacos.config.name		先取 prefix，再取 name，最后取 spring.application.name
配置对应的 DataId	        spring.cloud.nacos.config.prefix		先取 prefix，再取 name，最后取 spring.application.name
配置内容编码	        spring.cloud.nacos.config.encode		读取的配置内容对应的编码
GROUP	                spring.cloud.nacos.config.group	DEFAULT_GROUP	配置对应的组
文件扩展名	        spring.cloud.nacos.config.fileExtension	properties	配置项对应的文件扩展名，目前支持 properties 和 yaml(yml)
获取配置超时时间	        spring.cloud.nacos.config.timeout	3000	客户端获取配置的超时时间(毫秒)
接入点	                spring.cloud.nacos.config.endpoint		地域的某个服务的入口域名，通过此域名可以动态地拿到服务端地址
命名空间	                spring.cloud.nacos.config.namespace		常用场景之一是不同环境的配置的区分隔离，例如开发测试环境和生产环境的资源（如配置、服务）隔离等
AccessKey	        spring.cloud.nacos.config.accessKey		当要上阿里云时，阿里云上面的一个云账号名
SecretKey	        spring.cloud.nacos.config.secretKey		当要上阿里云时，阿里云上面的一个云账号密码
Nacos Server对应的 context path	       spring.cloud.nacos.config.contextPath		Nacos Server 对外暴露的 context path
集群	                spring.cloud.nacos.config.clusterName		配置成Nacos集群名称
共享配置	                spring.cloud.nacos.config.sharedDataids		共享配置的 DataId, “,” 分割
共享配置动态刷新	        spring.cloud.nacos.config.refreshableDataids		共享配置中需要动态刷新的 DataId, “,” 分割
自定义 Data Id 配置	spring.cloud.nacos.config.extConfig		属性是个集合，内部由 ConfigPOJO 组成。Config有 3 个属性，分别是 dataId, group以及 refresh
~~~~  

http://127.0.0.1:8081/actuator/nacos-config   
可从服务器查看当前nacos 配置信息 （json格式)


#### 记录几个过程中的小问题：
#####1 一开始配置时，按照文档走 不理解配置中心的dataId 概念，实际是工程application-id ,误配导致读取失败通过报错信息定位到是配置异常，反查文档后理解
#####2 小问题： 想通过curl请求查看 忘记加@RestController注解。。 Dubbo接口写多了
