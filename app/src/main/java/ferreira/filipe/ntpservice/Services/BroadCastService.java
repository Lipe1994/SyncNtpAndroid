package ferreira.filipe.ntpservice.Services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

public class BroadCastService extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            Log.e("BroadCast Received", "ON BOOT COMPLETE");

            try {
                Intent it = new Intent(context.getApplicationContext(), NTPRunableService.class);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    context.startForegroundService(it);
                } else {
                    context.startService(it);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}