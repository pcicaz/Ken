package com.sc.test.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class HttpsPostUtil {
    
    private static HttpsPostUtil util = null;
    
    private HttpsPostUtil() {
        try {
            initHttpsURLConnection("tomcat", "D:\\Program Files\\apache-tomcat-7.0.59\\tomcat.cert", "D:\\Program Files\\apache-tomcat-7.0.59\\tomcat.cert");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static HttpsPostUtil getInstance() {
        if (util == null)
            util = new HttpsPostUtil();
        return util;
    }
    
    /**
     * 获得KeyStore.
     * 
     * @param keyStorePath 密钥库路径
     * @param password 密码
     * @return 密钥库
     * @throws Exception
     */
    private KeyStore getKeyStore(String password, String keyStorePath)
        throws Exception {
        // 实例化密钥库
        KeyStore ks = KeyStore.getInstance("JKS");
        // 获得密钥库文件流
        FileInputStream is = new FileInputStream(keyStorePath);
        // 加载密钥库
        ks.load(is, password.toCharArray());
        // 关闭密钥库文件流
        is.close();
        return ks;
    }
    
    /**
     * 获得SSLSocketFactory.
     * 
     * @param password 密码
     * @param keyStorePath 密钥库路径
     * @param trustStorePath 信任库路径
     * @return SSLSocketFactory
     * @throws Exception
     */
    private SSLContext getSSLContext(String password, String keyStorePath, String trustStorePath)
        throws Exception {
        // 实例化密钥库
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // 获得密钥库
        KeyStore keyStore = getKeyStore(password, keyStorePath);
        // 初始化密钥工厂
        keyManagerFactory.init(keyStore, password.toCharArray());
        
        // 实例化信任库
        TrustManagerFactory trustManagerFactory =
            TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // 获得信任库
        KeyStore trustStore = getKeyStore(password, trustStorePath);
        // 初始化信任库
        trustManagerFactory.init(trustStore);
        // 实例化SSL上下文
        SSLContext ctx = SSLContext.getInstance("TLS");
        // 初始化SSL上下文
        ctx.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
        // 获得SSLSocketFactory
        return ctx;
    }
    
    /**
     * 初始化HttpsURLConnection.
     * 
     * @param password 密码
     * @param keyStorePath 密钥库路径
     * @param trustStorePath 信任库路径
     * @throws Exception
     */
    private void initHttpsURLConnection(String password, String keyStorePath, String trustStorePath)
        throws Exception {
        // 实例化主机名验证接口
        HostnameVerifier hnv = new TestHostnameVerifier();
        SSLContext sslCtx = getSSLContext(password, keyStorePath, trustStorePath);
        if (sslCtx != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslCtx.getSocketFactory());
        }
        HttpsURLConnection.setDefaultHostnameVerifier(hnv);
    }
    
    /**
     * 发送请求.
     * 
     * @param httpsUrl 请求的地址
     * @param xmlStr 请求的数据
     */
    public HttpsURLConnection httpsPostConnection(String httpsUrl) {
        HttpsURLConnection urlCon = null;
        try {
            urlCon = (HttpsURLConnection)(new URL(httpsUrl)).openConnection();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return urlCon;
    }
}
