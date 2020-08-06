package com.ai.io;

import java.io.InputStream;

/**
 * @author songkang
 * @date 2020/8/5
 */
public final class Resources {

    private Resources() {
    }

    // 加载字节流
    public static InputStream getResourceAsSteam(String path){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }

}
