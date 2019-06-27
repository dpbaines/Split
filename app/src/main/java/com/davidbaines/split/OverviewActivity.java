package com.davidbaines.split;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.davidbaines.split.dbmanager.DbInterface;

import java.util.HashMap;

public class OverviewActivity extends AppCompatActivity {

    public static HashMap<String, String[]> workouts = new HashMap<String, String[]>();

    public static final String NEW_MESSAGE = "com.davidbaines.split.NEW_MESSAGE";
    public static final int NEW_MESSAGE_RESULT = 101;
    public String currentDay;

    private int currentId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView editText = findViewById(R.id.textView);

        String day[] = message.split("\n");
        getSupportActionBar().setTitle(day[1]);
        editText.setText(day[1]);
        currentDay = day[1];

        initWorkouts();
        createList(day[1]);
    }

    public void initWorkouts() {
        workouts.put("Push", new String[]{"Bench Press", "Incline Press", "Flys", "Decline Press", "Tricep Pushdown"});
        workouts.put("Pull", new String[]{"Pull Ups", "Deadlifts", "Lat Pulldowns", "Vertical Rows", "Bicep Curls"});
        workouts.put("Legs", new String[]{"Shoulder Press", "Lateral Raises", "Rear Delts", "Squats", "Lunges", "Calve Raises"});
    }

    public void alertBox(String day, String workout, TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        //builder.setMessage("Are you sure you would like to delete " + workout);
        final String workoutToBeDeleted = workout;
        final String dayToBeDeleted = day;
        final TextView tempTextView = textView;
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbInterface dbInterface = new DbInterface(getApplicationContext());
                dbInterface.deleteWorkout(dayToBeDeleted, workoutToBeDeleted);

                ((ViewGroup) tempTextView.getParent()).removeView(tempTextView);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void createCard(String dayWorkouts, int id) {
        LinearLayout layout = findViewById(R.id.overview_layout);
        LayoutInflater inflater = getLayoutInflater();
        View view;

        view = inflater.inflate(R.layout.workout_card, layout, false);
        CardView cardView = view.findViewById(R.id.card_view);
        TextView activityName = view.findViewById(R.id.workout_text);
        cardView.setId(40000 + id);
        activityName.setText(dayWorkouts + ": 0");
        activityName.setId(30000 + id);
        layout.addView(cardView);
        SeekBar seekBar = view.findViewById(R.id.repCounter);
        seekBar.setMax(12);
        seekBar.setId(20000 + id);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int seekBarId = seekBar.getId();
                TextView textView = findViewById(seekBarId + 10000);
                String dayMessage = textView.getText().toString().split(":")[0];
                textView.setText(dayMessage + ": " + progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int seekBarId = seekBar.getId();
                TextView textView = findViewById(seekBarId + 10000);
                String retainMessage = textView.getText().toString();
                seekBar.setProgress(0);
                textView.setText(retainMessage);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == NEW_MESSAGE_RESULT) {
                String newWorkout = data.getStringExtra("workoutResult");
                Toast toast = Toast.makeText(getApplicationContext(), "New Workout created: " + newWorkout, Toast.LENGTH_SHORT);
                toast.show();

                DbInterface dbInterface = new DbInterface(this);
                //dbInterface.addNewWorkout(newWorkout, currentDay);

                //createCard();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Cancelled...", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void createList(String day) {

        if(workouts.containsKey(day)) {
            String dayWorkouts[] = workouts.get(day);
            for(int i = 0; i<dayWorkouts.length; i++) {
                createCard(dayWorkouts[i], i);
                currentId++;
            }
        }
    }

    public void onFabClick(View view) {
        Intent newWorkout = new Intent(this, NewWorkoutActivity.class);
        newWorkout.putExtra(NEW_MESSAGE, currentDay);
        startActivityForResult(newWorkout, NEW_MESSAGE_RESULT);
    }
}
