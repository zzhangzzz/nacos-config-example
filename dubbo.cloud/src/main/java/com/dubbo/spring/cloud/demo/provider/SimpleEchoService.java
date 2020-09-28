package com.dubbo.spring.cloud.demo.provider;

import com.alibaba.cloud.EchoService;

/**
 * @author zhang.xu
 * email nagisaww.zhang@beibei.com
 * 2020/9/28 10:10 上午
 * info :
 */
public class SimpleEchoService implements EchoService {

    @Override
    public String echo(String s) {
        return "[ECHO] " + s;
    }

}
