package com.nonobank.platformuser.component;

import com.nonobank.apps.HttpClient;
import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Created by tangrubei on 2018/3/15.
 */
@Component
public class RemoteComponent {


    @Autowired
    private HttpClient httpClient;


    /**
     * 依据系统名称来获取url映射关系
     *
     * @return
     */
    public String initRemoteSystemRoleUrl(String url) throws IOException, HttpException {
        CloseableHttpClient client = httpClient.getHttpClient();
        CloseableHttpResponse closeableHttpResponse = httpClient.doGetSend(client, null, url, null);
        String repstr = HttpClient.getResBody(closeableHttpResponse);
        return repstr;
    }
}
