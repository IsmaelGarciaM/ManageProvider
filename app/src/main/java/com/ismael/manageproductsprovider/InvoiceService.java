package com.ismael.manageproductsprovider;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.ismael.manageproductsprovider.provider.ManageProductContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ismael on 27/02/17.
 */

public class InvoiceService extends Service {
    int i = 0;
    String date;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Iniciando servicio...", Toast.LENGTH_SHORT).show();
        Cursor c = getContentResolver().query(ManageProductContract.Invoice.CONTENT_URI, ManageProductContract.Invoice.PROJECTION, null, null, null);

        long now = System.currentTimeMillis();
        long idate = 0;

        while(c.moveToPosition(i++)){
            date = c.getString(4);

            String dtStart = date;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = format.parse(dtStart);
                idate = date.getTime();

            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(now - idate > 48*60*60*1000 ) {
                String status = c.getString(5);
                    if (status.equals("Activo")) {
                        int idInvoice = c.getInt(0);
                        Intent intent1  = new Intent(getApplicationContext(), HomeActivity.class);
                        intent1.putExtra("idInvoice", idInvoice);

                        PendingIntent pI = PendingIntent.getActivity(getApplicationContext(), 0, intent1, PendingIntent.FLAG_ONE_SHOT);
                        NotificationCompat.Builder nc = new NotificationCompat.Builder(getApplicationContext());
                        nc.setContentText("El pedido " + idInvoice + " se realizó hace más de 2 días");
                        nc.setContentTitle("PEDIDO ATRASADO");
                        nc.setSmallIcon(R.mipmap.ic_launcher);
                        nc.setDefaults(Notification.FLAG_AUTO_CANCEL);

                        //Añadir vibracion, sonido y tal a la notificacion
                        nc.setDefaults(Notification.DEFAULT_LIGHTS);
                        nc.setDefaults(Notification.DEFAULT_SOUND);
                        nc.setDefaults(Notification.DEFAULT_VIBRATE);

                        //Añadir la notificacion al intent
                        nc.setContentIntent(pI);

                        NotificationManager nm = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        nm.notify(0, nc.build());
                        stopSelf();
                    }
            }
        }

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Finalizando servicio...", Toast.LENGTH_SHORT).show();

    }
}
