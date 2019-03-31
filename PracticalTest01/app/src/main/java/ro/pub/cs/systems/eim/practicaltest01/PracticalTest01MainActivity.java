package ro.pub.cs.systems.eim.practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PracticalTest01MainActivity extends AppCompatActivity {
    private EditText leftEditText = null;
    private EditText rightEditText = null;
    private Button leftButton = null;
    private Button rightButton = null;
    private Button navigateToSecondary = null;

    private buttonClickListener  btnClickListener = null;
    private int serviceStatus = Constants.SERVICE_STOPPED;

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /* În momentul în care o intenție cu difuzare are aceeași acțiune ca cea indicată în filtrul de intenții asociat ascultătorului respectiv, este apelată în mod automat metoda de callback onReceive(context, intent).*/
            Log.d("[Message]", intent.getStringExtra(Constants.MESSAGE));
        }
    }

    /* Un ascultător pentru intenții cu difuzare trebuie să aibă asociat și un filtru de intenții. Acesta conține acțiunile asociate intențiilor cu difuzare, pe care ascultătorul respectiv trebuie să le proceseze.*/
    private IntentFilter intentFilter = new IntentFilter();

    private class buttonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            int leftNoOfClicks = Integer.parseInt(leftEditText.getText().toString());
            int rightNoOfClicks = Integer.parseInt(rightEditText.getText().toString());
            switch (view.getId()) {
                case R.id.left_button:
                    leftEditText.setText(String.valueOf(leftNoOfClicks + 1));
                    break;

                case R.id.right_button:
                    rightEditText.setText(String.valueOf(rightNoOfClicks + 1));
                    break;

                case R.id.navigate_to_secondary_activity_button:
                    /* se creeaza intent-ul catre SecondaryActivity*/
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    int totalNoOfClicks = leftNoOfClicks + rightNoOfClicks;
                    intent.putExtra(Constants.TOTAL_COUNT, totalNoOfClicks);
                    startActivityForResult(intent, Constants.SECONDARY_ACTIVITY_REQUEST_CODE);

            }
            if ((leftNoOfClicks + rightNoOfClicks > Constants.PRAG) && (serviceStatus == Constants.SERVICE_STOPPED)) {
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra(Constants.FIRSTNUMBER, leftNoOfClicks);
                Log.d("[total no of clicks]",leftNoOfClicks + " " + rightNoOfClicks);
                intent.putExtra(Constants.SECONDNUMBER, rightNoOfClicks);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }

        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(Constants.LEFT_COUNT, leftEditText.getText().toString());
        savedInstanceState.putString(Constants.RIGHT_COUNT, rightEditText.getText().toString());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_main);

        leftEditText = (EditText)findViewById(R.id.left_edit_text);
        rightEditText = (EditText)findViewById(R.id.right_edit_text);
        leftButton = (Button)findViewById(R.id.left_button);
        rightButton = (Button)findViewById(R.id.right_button);

        leftEditText.setText(String.valueOf(0));
        rightEditText.setText(String.valueOf(0));
        btnClickListener = new buttonClickListener();
        leftButton.setOnClickListener(btnClickListener);
        rightButton.setOnClickListener(btnClickListener);


        // restore text fields state
        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey(Constants.LEFT_COUNT)) {
                leftEditText.setText(savedInstanceState.getString(Constants.LEFT_COUNT));
            } else {
                leftEditText.setText(String.valueOf(0));
            }
            if(savedInstanceState.containsKey(Constants.RIGHT_COUNT)) {
                rightEditText.setText(savedInstanceState.getString(Constants.RIGHT_COUNT));
            } else {
                rightEditText.setText(String.valueOf(0));
            }
        }

        navigateToSecondary = (Button)findViewById(R.id.navigate_to_secondary_activity_button);
        navigateToSecondary.setOnClickListener(btnClickListener);

        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // restore text fields state
        if (savedInstanceState != null) {
            if(savedInstanceState.containsKey(Constants.LEFT_COUNT)) {
                leftEditText.setText(savedInstanceState.getString(Constants.LEFT_COUNT));
            } else {
                leftEditText.setText(String.valueOf(0));
            }
            if(savedInstanceState.containsKey(Constants.RIGHT_COUNT)) {
                rightEditText.setText(savedInstanceState.getString(Constants.RIGHT_COUNT));
            } else {
                rightEditText.setText(String.valueOf(0));
            }
        }
    }

    /* Se apeleaza automat la revenirea din intent */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Constants.SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    /* oprirea serviciului */
    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* activare ascultator pentru intentii cu difuzare */
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        /* activare ascultator pentru intentii cu difuzare */
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }
}
