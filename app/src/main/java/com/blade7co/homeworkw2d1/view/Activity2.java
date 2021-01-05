package com.blade7co.homeworkw2d1.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.blade7co.homeworkw2d1.R;
import com.blade7co.homeworkw2d1.util.Constants;

import org.w3c.dom.Text;

public class Activity2 extends AppCompatActivity {

    public static final String DATA_TAG = "data_tag";
    public static final String TAG = "TAG_X";

    private TextView textName;
    private TextView text;
    private EditText editText;
    private Button buttonSend;
    private ScrollView scrollView;

    private String name;
    private String receivedMessages;
    private String editMessage;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        textName = findViewById(R.id.tv_name_send);
        text = findViewById(R.id.tv_messages_send);
        editText = findViewById(R.id.et_text_send);
        buttonSend = findViewById(R.id.btn_send);
        scrollView = findViewById(R.id.scrollable);

        resumeData();

        name = getIntent().getExtras().getString(Constants.TEXTNAME);
        receivedMessages = getIntent().getExtras().getString(Constants.TEXT);

        Log.d(TAG, "ReceiveMassage: " + name);
        Log.d(TAG, "ReceiveMassage: " + receivedMessages);

        textName.setText(name);
        text.setText(receivedMessages);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });


        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().isEmpty()) {
                    Toast.makeText(Activity2.this, "Please enter a message", Toast.LENGTH_SHORT).show();
                } else {
                    name += "Person Y: \n\n";
                    receivedMessages += editText.getText().toString() + "\n\n";
                    Intent intent = new Intent();
                    intent.putExtra(Constants.TEXTNAME, name);
                    intent.putExtra(DATA_TAG, receivedMessages);
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });
    }

    private void resumeData() {
        sharedPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        name = sharedPreferences.getString(Constants.NAME_SEND_KEY, "");
        receivedMessages = sharedPreferences.getString(Constants.MESSAGE_SEND_KEY, "");
        editMessage = sharedPreferences.getString(Constants.EDIT_KEY, "");

        textName.setText(name);
        text.setText(receivedMessages);
        editText.setText(editMessage);
    }

    protected void onStop() {
        super.onStop();
        sharedPreferences.edit()
                .putString(Constants.NAME_SEND_KEY, name)
                .putString(Constants.MESSAGE_SEND_KEY, receivedMessages)
                .putString(Constants.EDIT_KEY, editMessage)
                .apply();
    }
}