package dk.sdu.lahan14.activityrecognition;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class HandleDataIntentService extends IntentService {


    public HandleDataIntentService() {
        super("HandleDataIntentService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            DetectedActivity detected = result.getMostProbableActivity();
            broadcastIntent(detected.getType());

        }
    }

    public void broadcastIntent(int detected) {
        Intent intent = new Intent();
        intent.setAction("PLOT_UPDATE");
        intent.putExtra("activity", detected);
        sendBroadcast(intent);
    }
}
