package io.jenkins.plugins;

public class Api {
    private static final String DEBUG_API_URL = "http://gw.api.tbsandbox.com/router/rest";
    private static final String RELEASE_API_URL = "http://gw.api.taobao.com/router/rest";
    public static String getApi(){
        if (C.DEBUG){
            return DEBUG_API_URL;
        }else{
            return RELEASE_API_URL;
        }
    }
}
