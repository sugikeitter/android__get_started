package com.example.keita.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String EXTRA_INT = "com.example.myfirstapp.INT";

    private TextView textViewRes;
    private String res = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewRes = (TextView) findViewById(R.id.textView);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        int i = 999;
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_INT, i);
        startActivity(intent);
    }

    public void httpGet(View view) {
        Request req = new Request.Builder()
                .url("https://google.co.jp")
                .get()
                .build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failMessage();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                res = response.body().string();
                textViewRes.setText(res);
            }
        });
    }

    private void failMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                textViewRes.setText("FAILED.");
            }
        });
    }
}
