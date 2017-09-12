package com.signme.signme.server;

import java.net.CookieHandler;
import java.net.CookiePolicy;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.RxJavaCallAdapterFactory;

/**
 * Created by dsm2016 on 2017-09-04.
 */
public class ApplicationController{
    private static APIinterface Interface;

    private static final int CONNECT_TIMEOUT=15;
    private static final int WRITE_TIMEOUT=15;
    private static final int READ_TIMEOUT=15;
    private static OkHttpClient client;
    private static final String SEVER_URL="http://13.124.15.202:80/";
    private static Retrofit retrofit=null;
    public synchronized static APIinterface getClient(){
        if(Interface==null){
            HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            //CookieManager cookieManager=new CookieManager(new PersistentCookieStore(context),CookiePolicy.ACCEPT_ALL);
            java.net.CookieManager cookieManager = new java.net.CookieManager();
            cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
            CookieHandler.setDefault(cookieManager);
            client=configureClient(new OkHttpClient().newBuilder())
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                     .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
             .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
            .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
            .addInterceptor(httpLoggingInterceptor) //http 로그 확인
            .build();


            Interface=new Retrofit.Builder()
                    .baseUrl(SEVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build().create(APIinterface.class);
        }

        return Interface;
    }
    public static OkHttpClient.Builder configureClient(final OkHttpClient.Builder builder){

            final TrustManager[] certs = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public X509Certificate[] getAcceptedIssuers() { return null; }
                        @Override
                        public void checkServerTrusted(final X509Certificate[] chain, final String authType) { }
                        @Override
                        public void checkClientTrusted(final X509Certificate[] chain, final String authType) { } }};
                         SSLContext ctx = null; try { ctx = SSLContext.getInstance("TLS"); ctx.init(null, certs, new SecureRandom()); }
        catch (final java.security.GeneralSecurityException ex) {
            ex.printStackTrace(); }
        try {
            final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(final String hostname, final SSLSession session) { return true; } };
                    builder.sslSocketFactory(ctx.getSocketFactory()).hostnameVerifier(hostnameVerifier); }
        catch (final Exception e) { e.printStackTrace(); }
        return builder;

        }
    }

