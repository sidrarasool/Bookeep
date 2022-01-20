package com.example.bookeep;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainMenuActivity extends AppCompatActivity {
    Button btn_debt, btn_dev, btn_payment, btn_project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btn_debt = findViewById(R.id.btn_debt);
        btn_dev = findViewById(R.id.btn_devs);
        btn_payment = findViewById(R.id.btn_payments);
        btn_project = findViewById(R.id.btn_projects);

        btn_debt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getBaseContext(), DebtActivity.class);
                //startActivity(intent);
            }
        });

        btn_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getBaseContext(), ProjectActivity.class);
                //startActivity(intent);
            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getBaseContext(), PaymentActivity.class);
                //startActivity(intent);
            }
        });

        btn_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getBaseContext(), DeveloperActivity.class);
                //startActivity(intent);
            }
        });
    }

    public void onBackPressed(){}
}

