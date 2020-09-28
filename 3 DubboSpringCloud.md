## 3 SpringcloudAlibaba分布式服务调用

本篇主要是通过dubbo spring cloud实现spring cloud分布式调用  
dubbo spring cluod 替换spring cloud分布式服务调用底层协议  
dubbo spring cloud服务订阅 + 元数据 + actuator

训练营官方教程文档  
https://start.aliyun.com/article/sca7lesson/rpc  

自己搭建的流程 跟官网文档稍微有点不同，很多东西之前没接触过蛮有意思  

1、自己组依赖创建了工程里dubbo.cloud module 对应官网文档快速上手创建dubbo-provider-sample部分  

2、api-cloud文件夹对应创建 artifact - dubbo-sample-api  
这里自己搭的时候遇到了几个问题，之前不太懂maven的自组，只是使用，一开始直接在工程目录进行  
 ~~~~
 mvn archetype:generate -DgroupId=com.alibaba.cloud -DartifactId=dubbo-sample-api -Dversion=0.0.1-SNAPSHOT -DinteractiveMode=false  
~~~~  
果然错误了, 本着教程都是最简单的实现形式仔细过了一遍文档，另外创建了一个文件夹，ok  

之后是需要将dubbo-sample-api部署到本地maven仓库，一开始未进入api-cloud目录里，提示需要pom文件，进入后
~~~~
mvn clean install 
~~~~
顺利将dubbo-sample-api部署到本地仓库

