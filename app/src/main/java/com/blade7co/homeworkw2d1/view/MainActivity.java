package com.blade7co.homeworkw2d1.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blade7co.homeworkw2d1.R;
import com.blade7co.homeworkw2d1.util.Constants;

public class MainActivity extends AppCompatActivity {

    private TextView textName;
    private TextView text;
    private EditText etText;
    private Button btnReceive;
    private ScrollView scrollView;


    private String name;
    private String messages;
    private String editMessage;

    public static final int RESULT_CODE = 101;

    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textName = findViewById(R.id.tv_name_receive);
        text = findViewById(R.id.tv_messages_receive);
        etText = findViewById(R.id.et_text_receive);
        btnReceive = findViewById(R.id.btn_receive);
        scrollView = findViewById(R.id.scrollable);

        resumeData();

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });



        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etText.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                } else {
                    name += "Person X: \n\n";
                    messages += etText.getText().toString() + "\n\n";
                    etText.setText("");
                    Intent intent = new Intent(MainActivity.this, Activity2.class);
                    intent.putExtra(Constants.TEXTNAME, name);
                    intent.putExtra(Constants.TEXT, messages);
                    startActivityForResult(intent, RESULT_CODE);
                }

            }
        });

    }

    private void resumeData() {
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        name = sharedPreferences.getString(Constants.NAME_KEY, "");
        messages = sharedPreferences.getString(Constants.MESSAGE_KEY, "");
        editMessage = sharedPreferences.getString(Constants.EDIT_KEY, "");

        textName.setText(name);
        text.setText(messages);
        etText.setText(editMessage);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences.edit()
                .putString(Constants.NAME_KEY, name)
                .putString(Constants.MESSAGE_KEY, messages)
                .putString(Constants.EDIT_KEY, editMessage)
                .apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_CODE){
            name = data.getStringExtra(Constants.TEXTNAME);
            messages = data.getStringExtra(Activity2.DATA_TAG);
            textName.setText(name);
            text.setText(messages);
        }

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }
}