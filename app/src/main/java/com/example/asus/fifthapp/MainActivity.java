package com.example.asus.fifthapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Asus on 27.02.2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText address, subject, emailtext;
    private Button send, call;
    private String sendEmail, myTheme, message;
    String number;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //проверка полей на наличие данных
            case R.id.button_send:
                getStings();

                if (TextUtils.isEmpty(sendEmail) ) {
                    Toast.makeText(this, Constant.ERROR_EMPTY_FIELD, Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(myTheme)){
                    Toast.makeText(this, Constant.ERROR_EMPTY_THEME, Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(message)){
                    Toast.makeText(this, Constant.ERROR_EMPTY_MESSAGE, Toast.LENGTH_SHORT).show();
                }else {
                    sendEmail();
                }
                break;
            case R.id.button_call:
                toCall();
                break;

        }
    }

    // инициализация текста из Edit
    private void findViews() {
        address = (EditText) findViewById(R.id.Edit_1st);
        subject = (EditText) findViewById(R.id.Edit_2nd);
        emailtext = (EditText) findViewById(R.id.Edit_3th);
        send = (Button) findViewById(R.id.button_send);
        call = (Button) findViewById(R.id.button_call);
    }

    // присваиваем переменным текст из Edit
    private void getStings() {
        sendEmail = address.getText().toString();
        myTheme = subject.getText().toString();
        message = emailtext.getText().toString();
    }
    // передаем нажатие
    private void setListeners() {
        send.setOnClickListener(this);
        call.setOnClickListener(this);
    }
    // отправляем письмо
    private void sendEmail() {
        final Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("plain/text");
        sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{sendEmail});
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, myTheme);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(sendIntent);
    }

    // совершаем звонок
    private void toCall() {
        number = String.format("tel:%s", Constant.EXTRA_NUMBER);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, Constant.PERMISSIONS_REQUEST_CALL_PHONE);
            return;
        }
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(number)));
    }

}


