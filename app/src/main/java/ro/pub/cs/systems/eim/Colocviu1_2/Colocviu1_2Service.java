package ro.pub.cs.systems.eim.Colocviu1_2;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Colocviu1_2Service extends Service {

    private Handler handler;
    private Runnable broadcastRunnable;
    private static final int INTERVAL = 2000; // 2 secunde

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();

        // Configurăm sarcina de trimis broadcast la fiecare 2 secunde
        broadcastRunnable = new Runnable() {
            @Override
            public void run() {
                // Obținem data și ora curente
                String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                // Creăm și trimitem un intent de broadcast cu data și ora curente
                Intent broadcastIntent = new Intent("ro.pub.cs.systems.eim.Colocviu1_2.SEND_DATE_TIME");
                broadcastIntent.putExtra("DATE_TIME", currentDate);
                sendBroadcast(broadcastIntent);

                // Afișăm în log
                System.out.println("Broadcast sent: " + currentDate);

                // Reprogramăm rularea la fiecare 2 secunde
                handler.postDelayed(this, INTERVAL);
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int sum = intent != null ? intent.getIntExtra("SUM_RESULT", 0) : 0;

        // Pornim doar dacă suma depășește 10
        if (sum > 10) {
            handler.post(broadcastRunnable); // Începem rularea sarcinii la fiecare 2 secunde
        }

        return START_STICKY; // Serviciul va continua să ruleze până este distrus
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(broadcastRunnable); // Oprirea repetării sarcinii la distrugerea serviciului
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null; // Acest serviciu nu permite legături
    }
}
