package dk.sdu.lahan14.activityrecognition;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.Task;


public class MainActivity extends AppCompatActivity {



    TextView clock;
    long startTime = 0;
    Button b;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            clock.setText(String.format("%d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        clock = findViewById(R.id.timeLabel);
        b = (Button) findViewById(R.id.startBtn);
    }

    public void startClick(View view) {

        Intent plotIntent = new Intent(this, PlotActivity.class);
        startActivity(plotIntent);

        if (b.getText().equals("stop")) {
            timerHandler.removeCallbacks(timerRunnable);
            b.setText("start");
        } else {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
            b.setText("stop");
        }

    }

}
