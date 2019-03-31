package ro.pub.cs.systems.eim.practicaltest01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class PracticalTest01Service extends Service {
    private ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    /* apelata in mod automat in momentul in care serviciul este lansat
    * in executie prin metoda startService()*/
        Log.d("[Service]", "Service started");
        int firstNumber = intent.getIntExtra(Constants.FIRSTNUMBER, -1);
        int secondNumber = intent.getIntExtra(Constants.SECONDNUMBER, -1);
        processingThread = new ProcessingThread(this, firstNumber, secondNumber);
        processingThread.start();
        return Service.START_REDELIVER_INTENT; /* indică sistemului de operare Android faptul că serviciul trebuie repornit în situația în care este distrus */
    }

    @Override
    public IBinder onBind(Intent intent) {
        /* pentru interfata prin care se interactioneaza cu serviciul */
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }
}
