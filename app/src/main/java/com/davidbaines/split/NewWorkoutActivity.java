package com.davidbaines.split;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NewWorkoutActivity extends AppCompatActivity {

    String day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_workout);

        Intent intent = getIntent();
        day = intent.getStringExtra(OverviewActivity.NEW_MESSAGE);
    }

    public void onSubmit(View view) {
        TextView text = findViewById(R.id.newWorkoutText);
        String workoutName = text.getText().toString();

        Intent result = new Intent();
        result.putExtra("workoutResult", workoutName);
        setResult(Activity.RESULT_OK, result);
        finish();
    }
}
