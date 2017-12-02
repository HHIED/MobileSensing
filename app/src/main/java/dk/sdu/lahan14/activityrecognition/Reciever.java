package dk.sdu.lahan14.activityrecognition;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lasse on 23-11-2017.
 */

public class Reciever extends BroadcastReceiver{

    public List<Integer> activity = new ArrayList<>();
    public interface OnMyActivityResult {
        public void onMyActivityResult(String sender, int point);
    }
    private OnMyActivityResult listener = null;

    @Override
    public void onReceive(Context context, Intent intent) {
            int i = intent.getIntExtra("activity", 0);
            activity.add(i);
            if(listener!=null) {
                listener.onMyActivityResult("receiver", i);
            }
    }

    public int[] activitiesToArray() {
        Integer[] array = activity.toArray(new Integer[activity.size()]);
        int[] intArray = new int[activity.size()];
        for(int i=0; i<array.length; i++) {
            intArray[i] =  array[i];
        }
        return intArray;
    }

    public void setMyActivityResult(Context context) {
        this.listener = (OnMyActivityResult) context;
    }
}
