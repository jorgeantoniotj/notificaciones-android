package com.jorgeantoniotj.notificacionesandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    private ArrayAdapter adapter;
    private ListView list;
    private NotificationManager notifyMgr;
    private boolean segundaVez = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //list= (ListView) findViewById(android.R.);

        String items[]={
                "Notificación Simple",
                "Notificación + Acción",
                "Notificación + Actualización",
                "Notificación + Aviso",
                "Notificación + Progreso",
                "Notificación Big View"
        };


        adapter= new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                items);

        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

        notifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void notification1(int id, int iconId, String titulo, String contenido) {

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(iconId)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(),
                                R.mipmap.ic_launcher
                                )
                        )
                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setColor(getResources().getColor(R.color.colorPrimary));


        // Construir la notificación y emitirla
        notifyMgr.notify(id, builder.build());
    }


    public void notification2(int id, int iconId, String titulo, String contenido ) {

        // Creación del builder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(iconId)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(),
                                R.mipmap.ic_launcher
                                )
                        )
                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setColor(getResources().getColor(R.color.colorPrimary));

        // Nueva instancia del intent apuntado hacia Eslabon
        Intent intent = new Intent(this, Eslabon.class);

        // Crear pila
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Añadir actividad padre
        stackBuilder.addParentStack(Eslabon.class);

        // Referenciar Intent para la notificación
        stackBuilder.addNextIntent(intent);

        // Obtener PendingIntent resultante de la pila
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Asignación del pending intent
        builder.setContentIntent(resultPendingIntent);

        // Remover notificacion al interactuar con ella
        builder.setAutoCancel(true);

        // Construir la notificación y emitirla
        notifyMgr.notify(id, builder.build());
    }

    /*
    USA ESTE CÓDIGO PARA PROBAR CON UNA ACTIVIDAD ESPECIAL

    public void notification2(int id, int iconId, String titulo, String contenido ) {

        // Creación del builder
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(iconId)
                        .setContentTitle(titulo)
                        .setContentText(contenido);

        // Nueva instancia del intent apuntado hacia DetalleNotificacion
        Intent intent = new Intent(this, DetalleNotificacion.class);

        // Añadir banderas para aislar la activivdad
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
        Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Asignación del pending intent
        builder.setContentIntent(pendingIntent);

        // Construir la notificación y emitirla
        notifyMgr.notify(id, builder.build());
    }*/

    public void notification3() {

        String GRUPO_NOTIFICACIONES = "notificaciones_similares";
        Notification notificacion;

        // Comprobar si ya fue presionado el item
        if(!segundaVez) {
            notificacion = new NotificationCompat.Builder(this)
                    .setContentTitle("Mensaje Nuevo")
                    .setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setContentText("Carlos Arándano   ¿Donde estás?")
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .build();

            // Activar la bandera
            segundaVez = true;

        }else {

            notificacion = new NotificationCompat.Builder(this)
                    .setContentTitle("2 mensajes nuevos")
                    .setSmallIcon(android.R.drawable.ic_dialog_email)
                    .setNumber(2)
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setStyle(
                            new NotificationCompat.InboxStyle()
                                    .addLine("Carlos Arándano   ¿Si lo viste?")
                                    .addLine("Ximena Claus   Nuevo diseño del logo")
                                    .setBigContentTitle("2 mensajes nuevos")
                    )
                    .setGroup(GRUPO_NOTIFICACIONES)
                    .setGroupSummary(true)
                    .build();
        }

        notifyMgr.notify(3, notificacion);
    }

    public void notification4(int id, int iconId, String titulo, String contenido ) {

        // Estructurar la notificación
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(iconId)
                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);

        // Crear intent
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Crear pending intent
        PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        // Asignar intent y establecer true para notificar como aviso
        builder.setFullScreenIntent(fullScreenPendingIntent, true);

        // Construir la notificación y emitirla
        notifyMgr.notify(id, builder.build());
    }

    public void notification5(final int id, int iconId, String titulo, String contenido) {

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(iconId)
                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setColor(getResources().getColor(R.color.colorPrimary));

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int i;

                        /*// Ciclo para la simulación de progreso
                        for (i = 0; i <= 100; i += 5) {
                            // Setear 100% como medida máxima
                            builder.setProgress(100, i, false);
                            // Emitir la notificación
                            notifyMgr.notify(id, builder.build());
                            // Retardar la ejecución del hilo
                            try {
                                // Retardo de 1s
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                Log.d(TAG, "Falló sleep(1000) ");
                            }
                        }*/

                        /*
                        ACTUALIZACIÓN DE LA NOTIFICACION
                         */

                        // Desplegar mensaje de éxito al terminar
                        builder.setContentText("Indicador de Actividad")
                                // Quitar la barra de progreso
                                .setProgress(0, 0, true);
                        notifyMgr.notify(id, builder.build());
                    }
                }

        ).start();

    }

    public void notification6(int id, int iconId, String titulo, String contenido) {

        Intent intent = new Intent(this, Eslabon.class);
        intent.setAction(Intent.ACTION_VIEW);
        PendingIntent piDismiss = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(iconId)
                        .setLargeIcon(BitmapFactory.decodeResource(
                                getResources(),
                                R.mipmap.ic_launcher
                                )
                        )
                        .setContentTitle(titulo)
                        .setContentText(contenido)
                        .setColor(getResources().getColor(R.color.colorPrimary))
                        .setStyle(
                                new NotificationCompat.BigTextStyle()
                                        .bigText(contenido + ", si quieres nos " +
                                                "reunimos mañana y te enseño los " +
                                                "detalles de aplicación"))
                        .addAction(android.R.drawable.ic_dialog_email,
                                "RESPONDER", piDismiss)
                        .addAction(android.R.drawable.ic_input_delete,
                                "IGNORAR", null)
                        .setAutoCancel(true);

        Notification notification = mBuilder.build();

        // Construir la notificación y emitirla
        notifyMgr.notify(id, notification);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d(TAG, "onItemClick(): item " + position);

        switch(position){
            case 0:
                notification1(
                        1,
                        android.R.drawable.ic_dialog_email,
                        "Ximena Claus",
                        ":D ¡Ya tengo el nuevo logo!"
                );
                break;
            case 1:
                notification2(
                        2,
                        android.R.drawable.ic_dialog_email,
                        "Ximena Claus",
                        ":D ¡Ya tengo el nuevo logo!"
                );
                break;
            case 2:
                notification3();
                break;
            case 3:
                notification4(
                        4,
                        android.R.drawable.ic_dialog_alert,
                        "Urgente",
                        "Confirmar cita de negocios con Carlos"
                );
                break;
            case 4:
                notification5(
                        5,
                        android.R.drawable.stat_notify_sync,
                        "Sincronizando la Aplicación",
                        "Progreso"
                );
                break;
            case 5:
                notification6(
                        6,
                        android.R.drawable.ic_dialog_email,
                        "Ximena Claus",
                        ":D ¡Ya tengo el nuevo logo!..."
                );
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_cancel) {
            // Opción para cancelar todas las notificaciones emitidas
            notifyMgr.cancelAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
