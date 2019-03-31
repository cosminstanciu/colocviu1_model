package ro.pub.cs.systems.eim.practicaltest01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01SecondaryActivity extends AppCompatActivity {
    private TextView totalNoOfClicks = null;
    private Button okButton = null;
    private Button cancelButton = null;

    private ButtonOnClickListener btnOnClickListener = new ButtonOnClickListener();

    private class ButtonOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.ok_button:
                    setResult(RESULT_OK);
                    break;
                case R.id.cancel_button:
                    setResult(RESULT_CANCELED);
                    break;
            }
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_secondary);
        totalNoOfClicks = (TextView)findViewById(R.id.number_of_clicks_text_view);
        okButton = (Button)findViewById(R.id.ok_button);
        cancelButton = (Button)findViewById(R.id.cancel_button);

        okButton.setOnClickListener(btnOnClickListener);
        cancelButton.setOnClickListener(btnOnClickListener);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey(Constants.TOTAL_COUNT)) {
            int noOfClicks = intent.getIntExtra(Constants.TOTAL_COUNT, -1);
            totalNoOfClicks.setText(String.valueOf(noOfClicks));
        }
    }
}
