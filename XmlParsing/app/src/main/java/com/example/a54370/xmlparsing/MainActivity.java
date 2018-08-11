package com.example.a54370.xmlparsing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
                HttpURLConnection connection=null;
                BufferedReader bufferedReader=null;
                try {
                    URL url=new URL("http://www.baidu.com");
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(2000);
                    connection.setReadTimeout(2000);
                    InputStream inputStream=connection.getInputStream();
                    bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuffer response =new StringBuffer();
                    String line;
                    while((line=bufferedReader.readLine())!=null){
                        response.append(line);
                    }
                    showResponse(response.toString());
                    connection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
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
