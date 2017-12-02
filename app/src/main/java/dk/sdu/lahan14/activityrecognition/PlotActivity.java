package dk.sdu.lahan14.activityrecognition;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYSeriesFormatter;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionClient;
import com.google.android.gms.tasks.Task;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlotActivity extends AppCompatActivity implements Reciever.OnMyActivityResult {

    private GraphView graph;
    private Reciever receiver;
    XYSeries series1;
    ArrayList<Integer> yValues;
    ActivityRecognitionClient client;
    PendingIntent pending;
    private int x = 0;
    private ArrayList<Integer> xvals;
    boolean first = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.simple_xy_plot_example);
        IntentFilter filter = new IntentFilter("PLOT_UPDATE");
        receiver = new Reciever();
        this.registerReceiver(receiver, filter);
        receiver.setMyActivityResult(this);
        client = ActivityRecognition.getClient(this);
        Intent intent = new Intent(this, HandleDataIntentService.class);
        pending = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Task task = client.requestActivityUpdates(10000, pending);
        PixelUtils.init(this);


        filter.addAction("PLOT_UPDATE");

        // initialize our XYPlot reference:
        graph = findViewById(R.id.graph);
        yValues = new ArrayList<>();
        xvals = new ArrayList<>();
        if(first) {
            xvals.add(x);
            x++;
            yValues.add(0);

            first = false;
        }

    }

    private void drawPlot() {
        DataPoint[] data = new DataPoint[yValues.size()];
        for(int i=0; i<yValues.size(); i++) {
            data[i] = new DataPoint(xvals.get(i), yValues.get(i));
        }
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(data);

        series.setSpacing(0);
        graph.addSeries(series);



    }



    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("PLOT_UPDATE");
        filter.addAction("PLOT_UPDATE");
        this.registerReceiver(receiver, filter);
        Task task = client.requestActivityUpdates(1000, pending);
    }

    @Override
    public void onPause() {
        super.onPause();
        this.unregisterReceiver(receiver);



    }

    @Override
    public void onMyActivityResult(String sender, int point) {
        Log.d("Test", Integer.toString(point));
        yValues.add(point);
        xvals.add(x);
        x++;
        drawPlot();
    }
}
