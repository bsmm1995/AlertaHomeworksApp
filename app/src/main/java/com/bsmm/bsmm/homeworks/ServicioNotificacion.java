package com.bsmm.bsmm.homeworks;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.ArrayList;

import control.GestorDB;
import control.Metodos;

public class ServicioNotificacion extends Service {

    public static ArrayList<String> alertas;
    public static ArrayList<Integer> listaAlertas;

    private NotificationManager notificationManager;
    private static final int ID_NOTIFICACION = 2;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        alertas = new ArrayList<>();
        listaAlertas = new ArrayList<>();
        alertas = new GestorDB(getBaseContext()).getAllTimesHomeworks();
    }

    private void convertToInt() {
        for (String a : alertas) {
            String[] part = a.split(":");
            int val = Integer.parseInt(part[0]) * 60;
            val += Integer.parseInt(part[1]);

            listaAlertas.add(val);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        convertToInt();

        int currentTime = new Metodos().getCurrentTimeInMinutos();

       // Toast.makeText(getBaseContext(), "   edit " + (currentTime) + "   " + (listaAlertas.size()), Toast.LENGTH_SHORT).show();

        for (int val : listaAlertas) {
          // Toast.makeText(getBaseContext(), val + "   edit " + (currentTime + 10) + "   " + (currentTime + 30) + "  " + (currentTime + 60), Toast.LENGTH_SHORT).show();

            if (val == (currentTime + 60) || val == (currentTime + 30) || val == (currentTime + 15)) {
                Toast.makeText(getBaseContext(), val + "   " + (currentTime + 60) + "   " + (currentTime + 30) + "  " + (currentTime + 15), Toast.LENGTH_SHORT).show();
                long[] vibrate = {0, 100, 100};
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext())
                        .setSmallIcon(R.drawable.ic_action_alerta)
                        .setContentTitle("Alerta Homeworks")
                        .setContentText("Recuerde completar sus tareas...")
                        .setVibrate(vibrate)
                        .setWhen(System.currentTimeMillis());

                Intent notificationIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);

                notificationManager.notify(ID_NOTIFICACION, builder.build());
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
