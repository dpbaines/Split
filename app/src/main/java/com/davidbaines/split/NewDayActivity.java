package com.davidbaines.split;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NewDayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_day);
    }

    public void onNewDay(View view) {
        EditText editText = findViewById(R.id.newDayText);
        String userInput = editText.getText().toString();

        Intent result = new Intent();
        result.putExtra("dayResult", userInput);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
