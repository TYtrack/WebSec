package com.example.dell.websec;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent=getIntent();
        String s1=intent.getStringExtra("URL");

        webView=(WebView)findViewById(R.id.web_View1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接
                Log.e("Main2",url);
                sendHttp(url);
                //sendT(url);
                if (url.contains("taobao")){
                    Toast.makeText(Main2Activity.this,"这是淘宝",Toast.LENGTH_SHORT).show();
                }
                return super.shouldOverrideUrlLoading(view,url);
            }
        });
        webView.loadUrl(s1);
        Log.e("Main2",s1);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
            webView.goBack();
            return  true;
        }else{
            onBackPressed();
        }
        return super.onKeyDown(keyCode,event);
    }
    public void sendHttp(String url){
        Log.e("SendHttp","ddddddddddddddddddddd\n");
        OkHttpClient client =new OkHttpClient();
        Log.e("SendHttp","bbbbbbbbbbbbbbbbbbbbb\n");
        FormBody.Builder formBuilder=new FormBody.Builder();
        Log.e("SendHttp","ccccccccccccccccccccc");
        formBuilder.add("url",url);


        Request request=new Request.Builder().url("http://119.29.13.142:5000/test").post(formBuilder.build()).build();
        Call call= client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Main2Activity.this,"Fail to submit the url",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res=response.body().string();
                Log.e("AAA",res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (res.equals("0")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main2Activity.this,"失败",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Main2Activity.this,"成功",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    public void sendT(String urlString){

        urlString = (urlString == null ? "Hello IdeasAndroid!" : urlString);
        try {
            Log.e("TCP2222222", "dddddddddddddddddddd");
            Socket ss = new Socket("119.29.13.142", 1060);
            Log.e("TCP2222222", "lllllllllllllllllllllll");
            OutputStream out = ss.getOutputStream();
            Log.e("TCP2222222", "zzzzzzzzzzzzzzzzzzzzzzz");
            out.write((urlString + "\n").getBytes("utf-8"));
            out.flush();
            Log.e("TCP2222222", "fffffffffffffffff");
            InputStream is = ss.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String akb = bufferedReader.readLine();
            Log.e("TCP1111111", akb);

            out.close();
            bufferedReader.close();
            ss.close();
        }catch (SocketException e){
            e.printStackTrace();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendA(String urlString){
        urlString = (urlString == null ? "Hello IdeasAndroid!" : urlString);
        int server_port = 1060;
        Log.e("Send",urlString);
        DatagramSocket s = null;
        try {
            s = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress local = null;
        try {
            // 换成服务器端IP
            local = InetAddress.getByName("119.29.13.142");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int msg_length = urlString.length();
        byte[] messageByte = urlString.getBytes();
        DatagramPacket p = new DatagramPacket(messageByte, msg_length, local, server_port);

        try {
            s.send(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
