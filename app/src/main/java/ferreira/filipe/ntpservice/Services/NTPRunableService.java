package ferreira.filipe.ntpservice.Services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.Nullable;
import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class NTPRunableService extends Service implements Runnable {
    int index = 0;

    public void onCreate() {

        new Thread(NTPRunableService.this).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {
        try {
            DateNtp(this.SERVER_NAME[index]);
            index++;

            while(!isComputed()){
                DateNtp(this.SERVER_NAME[index]);
                index++;

                if(index >= this.SERVER_NAME.length){
                    index =0;
                }
            }

            long returnTime = timeInfo.getTime();
            Date time = new Date(returnTime);

            String command = "date "+DateFormat.format("MMddHHmmyyyy", time)+".00";

            Runtime runtime = Runtime.getRuntime();
            runtime.exec(command);
            runtime.exec("su -c "+command);
            Log.d("Thread","OK - "+DateFormat.format("MMddHHmmyyyy", time));

        } catch (Exception e) {
            Log.d("thread", "Deu ruim \n\r\r\n"+ e.getMessage());
        }
    }

    private static final String[] SERVER_NAME = { "a.st1.ntp.br", "ntp.br", "3.br.pool.ntp.org","c.ntp.br", "2.br.pool.ntp.org","d.st1.ntp.br", "1.br.pool.ntp.org","0.br.pool.ntp.org"};

    private volatile Date timeInfo;
    private volatile Long offset;

    public boolean isComputed()
    {
        return timeInfo != null;
    }

    public void DateNtp(String host) {
        try {
            Log.d("thread","Host " + host);
            TimeResult timeResult = getTime(host);

            if(timeResult.resultCode == 1){
                this.timeInfo = new Date(timeResult.time);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private TimeResult getTime(String host){
        int FAILURE_RESULT = 0;
        int SUCCESS_RESULT = 1;

        NTPUDPClient timeClient = new NTPUDPClient();
        timeClient.setDefaultTimeout(15_000);

        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            return new TimeResult(FAILURE_RESULT, -1, e.getMessage());
        }
        TimeInfo timeInfo = null;
        try {
            timeInfo = timeClient.getTime(inetAddress);
        } catch (IOException e) {
            return new TimeResult(FAILURE_RESULT, -1, e.getMessage());
        }
        long time = timeInfo.getMessage().getTransmitTimeStamp().getTime();
        return new TimeResult(SUCCESS_RESULT, time, "");
    }

    private class TimeResult{
        int resultCode;
        String failureMessage;
        long time;

        protected TimeResult(int resultCode, long time, String failureMessage){

            this.resultCode = resultCode;
            this.time = time;
            this.failureMessage = failureMessage;
        }
    }
}
