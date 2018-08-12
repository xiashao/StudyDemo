package com.example.a54370.okhttp;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.send_request);
        textView=findViewById(R.id.response_text);
        button.setOnClickListener(this);
    }
    private void sendRequestWithHttpURLConnection(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                   /* Request request = new Request.Builder()
                            .url("http://www.baidu.com")
                            .build();*/
                    FormBody.Builder requestBody=new FormBody.Builder()
                            .add("name","admin")
                            .add("password","123456");
                    FormBody formBody=requestBody.build();
                    Request.Builder builder = new Request.Builder();
                    Request request1 = builder.url("http://www.baidu.com").post(formBody).build();
                    Response response=client.newCall(request1).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showResponse(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(s);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.send_request)
        {
            sendRequestWithHttpURLConnection();
        }
    }
}
