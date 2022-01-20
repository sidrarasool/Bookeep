package com.example.bookeep;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button btn_submit;
    EditText et_pin;
    private final String original_pin = "1234";
    private String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_submit = findViewById(R.id.btn_submit);
        et_pin = findViewById(R.id.et_pin);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPin();
            }
        });
    }

    private void checkPin(){
        pin = et_pin.getText().toString();

        if(!pin.isEmpty()) {
            if(pin.equals(original_pin)) {
               Intent intent = new Intent(this, MainMenuActivity.class);
               startActivity(intent);
            }
        }

        finish();
    }
}