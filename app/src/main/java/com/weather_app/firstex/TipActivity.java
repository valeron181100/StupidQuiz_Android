package com.weather_app.firstex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TipActivity extends AppCompatActivity {

    private Button mTipButton;
    private TextView mAnswerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        mTipButton = findViewById(R.id.showTipButton);
        mAnswerView = findViewById(R.id.answerView);

        mTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerResId = getIntent().getBooleanExtra(MainActivity.TRANSFER_QUEST_EXTRA, false);
                mAnswerView.setText(String.valueOf(answerResId));
                Intent intent = new Intent(TipActivity.this, MainActivity.class);
                intent.putExtra(MainActivity.TRANSFER_CHEATING, true);

                setResult(MainActivity.TRANSFER_IS_CHEATING, intent);
            }
        });


    }
}
