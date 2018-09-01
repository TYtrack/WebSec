package com.example.dell.websec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    private Button button1;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.Text_view1);
        button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlText=textView.getText().toString();
                if (!urlText.contains("http://") ||!urlText.contains("https://"))
                    urlText="http://"+urlText;
                Intent intent1=new Intent (MainActivity.this,Main2Activity.class);
                Toast.makeText(MainActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                intent1.putExtra("URL",urlText);
                Log.e("Main",urlText);
                startActivity(intent1);

            }
        });

    }

}
