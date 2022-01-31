package com.conamobile.alarmclockbudilnik

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*
import android.os.Vibrator
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.ActivityNavigator
import com.conamobile.alarmclockbudilnik.Utils.MySharedPrefarance

class MyReceiver : BroadcastReceiver() {

    private lateinit var pendingIntent: PendingIntent
    private lateinit var alarmManager: AlarmManager

    @SuppressLint("SimpleDateFormat", "UnspecifiedImmutableFlag")
    override fun onReceive(context: Context?, intent: Intent?) {

        MySharedPrefarance.init(context)
        val list = MySharedPrefarance.alarmList

        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val hh = SimpleDateFormat("HH").format(calendar.time).toInt()
        val mm = SimpleDateFormat("mm").format(calendar.time).toInt()
        val hafta = calendar[Calendar.DAY_OF_WEEK]
        val mediaPlayer = MediaPlayer.create(context, R.raw.default_music)
        val v = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        for (obekt in list) {

            println("$hh:$mm hafta: $hafta ${obekt.hafta[hafta-1]} now")

            if (hh == obekt.soat && mm == obekt.min && obekt.hafta[hafta-1] && obekt.isRun) {

                val handler = Handler(Looper.getMainLooper())
                val runnable = Runnable {
                    while (true){
                        Thread.sleep(500)
                        v.vibrate(500)
                        if (!mediaPlayer.isPlaying){
                            break
                        }
                    }
                }
                if (mediaPlayer.isPlaying) {
                    mediaPlayer.stop()
                    mediaPlayer.start()

                    //Notification

//                    val i = Intent(context, ActivityNavigator.Destination::class.java)
//                    val i = Intent(context, MyReceiver::class.java)
                    val i = Intent(context, NotifyFragment::class.java)
                    intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    val builder = NotificationCompat.Builder(context,"ConaMobile")
                        .setSmallIcon(R.drawable.notify_acitve)
                        .setContentTitle("Alarm Clock")
                        .setContentText("Tap to turn off alarm")
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)

                    val notificationManager = NotificationManagerCompat.from(context)
                    notificationManager.notify(123, builder.build())
                    //Notification end
                } else {
                    mediaPlayer.start()


                    //Notification

//                    val i = Intent(context, ActivityNavigator.Destination::class.java)
                    val i = Intent(context, NotifyFragment::class.java)
                    i.putExtra("yess","s")
                    intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                    pendingIntent = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                        PendingIntent.getActivity(
                            context,
                            0,
                            i,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    } else {

                        PendingIntent.getActivity(
                            context,
                            0,
                            i,
                            PendingIntent.FLAG_UPDATE_CURRENT
                        )

                    }

                    val builder = NotificationCompat.Builder(context,"ConaMobile")
                        .setSmallIcon(R.drawable.notify_acitve)
                        .setContentTitle("Alarm Clock")
                        .setContentText("Tap to turn off alarm")
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)

                    val notificationManager = NotificationManagerCompat.from(context)
                    notificationManager.notify(123, builder.build())

                    //Notification end
                }


                if (obekt.vibration) {
                    v.vibrate(500)
                    handler.postDelayed(runnable, 100)
                }
            }
        }
        Log.d("run", "mediPlayer")
    }
}