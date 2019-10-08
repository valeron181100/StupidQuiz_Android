package com.weather_app.firstex;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.weather_app.firstex.models.IntegerProperty;
import com.weather_app.firstex.models.IntegerPropertyCaller;
import com.weather_app.firstex.models.Pair;
import com.weather_app.firstex.models.Question;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    public static final String TRANSFER_QUEST_EXTRA = "com.weather_app.firstex.question_extra";
    public static final String TRANSFER_CHEATING = "com.weather_app.firstex.cheating";
    public static final int TRANSFER_IS_CHEATING = 0;
    public static final String APP_TAG = "QUIZ";

    private ImageButton mYesButton;
    private ImageButton mNoButton;
    private ImageButton mBackButton;
    private TextView mQuestionDisplay;
    private ArrayList<Pair<Question, Boolean>> mQuestionBank;
    private IntegerProperty mCurrentQuestion;
    private ImageButton mTipButton;
    private ImageButton mForwardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTipButton = findViewById(R.id.tipButton);
        mQuestionDisplay = findViewById(R.id.questionDisplay);
        mBackButton = findViewById(R.id.backButton);
        mYesButton = findViewById(R.id.yesButton);
        mNoButton = findViewById(R.id.noButton);
        mForwardButton = findViewById(R.id.forwardButton);
        mQuestionBank = new ArrayList<>(3);
        mQuestionBank.add(new Pair<>(new Question(R.string.first_quest, true), false));
        mQuestionBank.add(new Pair<>(new Question(R.string.second_quest, false),false));
        mQuestionBank.add(new Pair<>(new Question(R.string.third_quest, true), false));
        mCurrentQuestion = new IntegerProperty(0);


        mCurrentQuestion.addOnChangeListener(new IntegerPropertyCaller() {
            @Override
            public void call(int oldVal, int nVal) {
                Log.d(APP_TAG, getResources().getResourceName(mQuestionBank.get(nVal).getKey().getTextResId()));
                mQuestionDisplay.setText(mQuestionBank.get(nVal).getKey().getTextResId());
                if (mQuestionBank.get(nVal).getValue()) {
                    mYesButton.setEnabled(false);
                    mNoButton.setEnabled(false);
                    mYesButton.getBackground().setAlpha(100);
                    mNoButton.getBackground().setAlpha(100);
                } else {
                    mYesButton.setEnabled(true);
                    mNoButton.setEnabled(true);
                    mYesButton.getBackground().setAlpha(255);
                    mNoButton.getBackground().setAlpha(255);
                }
            }
        });

        mCurrentQuestion.setValue(0);

        mForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentQuestion.inc();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mCurrentQuestion.dec();
            }
        });

        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentQuestion.getValue() != mQuestionBank.size()) {
                    if (checkAnswer(true)) {
                        Toast.makeText(MainActivity.this, "You are right!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "You are bad! Try again!", Toast.LENGTH_SHORT).show();
                    }
                    if (mCurrentQuestion.getValue() == mQuestionBank.size() - 1) {
                        Toast.makeText(MainActivity.this, "It is the last question!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        mCurrentQuestion.inc();
                    }
                }
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(mCurrentQuestion.getValue() < 3) {
                    if (checkAnswer(false)) {
                        Toast.makeText(MainActivity.this, "You are right!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "You are bad! Try again!", Toast.LENGTH_SHORT).show();
                    }
                    mCurrentQuestion.inc();
                }
            }
        });

        mTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TipActivity.class);
                intent.putExtra(TRANSFER_QUEST_EXTRA, mQuestionBank.get(mCurrentQuestion.getValue()).getKey().isAnswerFalse());
                startActivityForResult(intent, TRANSFER_IS_CHEATING);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(APP_TAG, "Result code is " + resultCode);
        if(resultCode == TRANSFER_IS_CHEATING && data != null){

            if(data.getBooleanExtra(TRANSFER_CHEATING, false)){
                mQuestionBank.get(mCurrentQuestion.getValue()).setValue(true);
                mYesButton.setEnabled(false);
                mNoButton.setEnabled(false);
            }

        }
    }

    private boolean checkAnswer(boolean isPressedTrue){
        if(mQuestionBank.get(mCurrentQuestion.getValue()).getKey().isAnswerFalse() == isPressedTrue){
            return true;
        }
        return false;
    }
}
