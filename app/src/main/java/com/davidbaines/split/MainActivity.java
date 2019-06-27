package com.davidbaines.split;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.davidbaines.split.dbmanager.DbInterface;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ArrayList<Integer> weights;
    public ArrayList<String> days;

    public static final String EXTRA_MESSAGE = "com.davidbaines.split.DAY";
    public static final int NEW_DAY_RESPONSE = 100;

    ViewPager viewPager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDays();
        viewPager = findViewById(R.id.view_pager);
        WorkoutViewPagerAdapter adapter = new WorkoutViewPagerAdapter(getSupportFragmentManager(), days.toArray());
        viewPager.setAdapter(adapter);
    }

    public void alertBox(String day, TextView textView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirm Delete");
        //builder.setMessage("Are you sure you would like to delete " + day);
        final String dayToBeDeleted = day;
        final TextView tempTextView = textView;
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DbInterface dbInterface = new DbInterface(getApplicationContext());
                dbInterface.deleteDay(dayToBeDeleted);

                ((ViewGroup) tempTextView.getParent()).removeView(tempTextView);
            }
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void addBox(String text) {
        /*LinearLayout parentLayout = findViewById(R.id.day_layout);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view;

        final String tempText = text;

        view = layoutInflater.inflate(R.layout.text_layout, parentLayout, false);
        Button textView = view.findViewById(R.id.text);
        textView.setText(text);
        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                alertBox(tempText.split("\n")[1], (TextView) v);
                return false;
            }
        });

        parentLayout.addView(textView);*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == NEW_DAY_RESPONSE) {
                String dayName = data.getStringExtra("dayResult");
                Toast toast = Toast.makeText(getApplicationContext(), "New Day created: " + dayName, Toast.LENGTH_SHORT);
                toast.show();

                DbInterface dbInterface = new DbInterface(this);
                dbInterface.addNewWorkoutDay(dayName, 0);

                addBox(0 + "\n" + dayName);
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Cancelled...", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void createNewDay(View view) {
        Intent newDayIntent = new Intent(this, NewDayActivity.class);
        startActivityForResult(newDayIntent, NEW_DAY_RESPONSE);
    }

    public void initDays() {
        DbInterface dbInterface = new DbInterface(this);
        //dbInterface.resetDatabase();
        //dbInterface.addNewWorkoutDay("Push", 135);
        //dbInterface.addNewWorkoutDay("Pull", 215);
        //dbInterface.addNewWorkoutDay("Legs", 150);
        Log.d("SQLINFO", dbInterface.getDays().toString());
        days = dbInterface.getDays();
        weights = dbInterface.getMax();
    }

    public void openDay(View view) {
        Button button = (Button) view;
        String buttonPressed = button.getText().toString();

        Intent openOverview = new Intent(this, OverviewActivity.class);
        openOverview.putExtra(EXTRA_MESSAGE, buttonPressed);
        startActivity(openOverview);
    }
}
