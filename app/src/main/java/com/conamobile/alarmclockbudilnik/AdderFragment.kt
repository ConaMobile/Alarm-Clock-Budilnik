package com.conamobile.alarmclockbudilnik

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.conamobile.alarmclockbudilnik.Models.AlarmObekt
import com.conamobile.alarmclockbudilnik.Utils.MySharedPrefarance
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_adder.*
import kotlinx.android.synthetic.main.fragment_adder.view.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.text.SimpleDateFormat
import java.util.*
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK

import android.content.Intent
import androidx.core.app.NotificationCompat


class AdderFragment : Fragment() {

    lateinit var root: View
    var haftaArray = arrayListOf<Boolean>(false, false, false, false, false, false, false)
    lateinit var mAdView : AdView
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var mInterstitialAd : InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor", "UnspecifiedImmutableFlag", "ShortAlarm", "SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_adder, container, false)

        //notify
        createNotificationChannel()
        //notify end


        //native ads start

        MobileAds.initialize(root.context) {}
        mAdView = root.adView2

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

            //ads end

        //ads start

        loadInterstitial()
        loadInterstitial2()

        //ads end

        root.num_picker1.minValue = 0
        root.num_picker1.maxValue = 23

        root.num_picker2.minValue = 0
        root.num_picker2.maxValue = 59

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            root.num_picker1.textColor = Color.WHITE
            root.num_picker2.textColor = Color.WHITE
        }


        val soat = SimpleDateFormat("HH").format(Date())
        val min = SimpleDateFormat("mm").format(Date())

        root.num_picker1.value = soat.toInt()
        root.num_picker2.value = min.toInt()

        root.cacel_btn.setOnClickListener {

            showinter()

        }

        root.haf_1.setOnClickListener {
            if (!haftaArray[1]){
                root.haf_1.setBackgroundResource(R.drawable.shape)
                root.haf_1.setTextColor(Color.parseColor("#E8BF6A"))
                haftaArray[1] = true
            }else{
                root.haf_1.setBackgroundResource(R.drawable.shape_2)
                root.haf_1.setTextColor(Color.parseColor("#BABABA"))
                haftaArray[1] = false
            }
        }

        root.haf_2.setOnClickListener {
            if (!haftaArray[2]){
                root.haf_2.setBackgroundResource(R.drawable.shape)
                root.haf_2.setTextColor(Color.parseColor("#E8BF6A"))
                haftaArray[2] = true
            }else{
                root.haf_2.setBackgroundResource(R.drawable.shape_2)
                root.haf_2.setTextColor(Color.parseColor("#BABABA"))
                haftaArray[2] = false
            }
        }
        root.haf_3.setOnClickListener {
            if (!haftaArray[3]){
                root.haf_3.setBackgroundResource(R.drawable.shape)
                root.haf_3.setTextColor(Color.parseColor("#E8BF6A"))
                haftaArray[3] = true
            }else{
                root.haf_3.setBackgroundResource(R.drawable.shape_2)
                root.haf_3.setTextColor(Color.parseColor("#BABABA"))
                haftaArray[3] = false
            }
        }
        root.haf_4.setOnClickListener {
            if (!haftaArray[4]){
                root.haf_4.setBackgroundResource(R.drawable.shape)
                root.haf_4.setTextColor(Color.parseColor("#E8BF6A"))
                haftaArray[4] = true
            }else{
                root.haf_4.setBackgroundResource(R.drawable.shape_2)
                root.haf_4.setTextColor(Color.parseColor("#BABABA"))
                haftaArray[4] = false
            }
        }
        root.haf_5.setOnClickListener {
            if (!haftaArray[5]){
                root.haf_5.setBackgroundResource(R.drawable.shape)
                root.haf_5.setTextColor(Color.parseColor("#E8BF6A"))
                haftaArray[5] = true
            }else{
                root.haf_5.setBackgroundResource(R.drawable.shape_2)
                root.haf_5.setTextColor(Color.parseColor("#BABABA"))
                haftaArray[5] = false
            }
        }
        root.haf_6.setOnClickListener {
            if (!haftaArray[6]){
                root.haf_6.setBackgroundResource(R.drawable.shape)
                root.haf_6.setTextColor(Color.parseColor("#E8BF6A"))
                haftaArray[6] = true
            }else{
                root.haf_6.setBackgroundResource(R.drawable.shape_2)
                root.haf_6.setTextColor(Color.parseColor("#BABABA"))
                root.haf_6.setTextColor(Color.parseColor("#BABABA"))
                haftaArray[6] = false
            }
        }
        root.haf_7.setOnClickListener {
            if (!haftaArray[0]){
                root.haf_7.setBackgroundResource(R.drawable.shape)
                root.haf_7.setTextColor(Color.parseColor("#E8BF6A"))
                haftaArray[0] = true
            }else{
                root.haf_7.setBackgroundResource(R.drawable.shape_2)
                root.haf_7.setTextColor(Color.parseColor("#BABABA"))
                haftaArray[0] = false
            }
        }

//        val bottomsheetFragment = BottomSheet_Fragment()

//        root.music_add.setOnClickListener {
//            bottomsheetFragment.show(childFragmentManager, "BottomSheet Dialog")
//        }

        root.tebranish_btn.setOnClickListener {

            if (switch_compat2.isChecked) {
                switch_compat2.isChecked = isDetached
            } else {
                switch_compat2.isChecked = isAdded
            }
        }

        val time = SystemClock.elapsedRealtime() + 1000
        val intent = Intent(context, MyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_IMMUTABLE)
        val alarmManager = activity?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            time,
            10000,
            pendingIntent
        )

        MySharedPrefarance.init(context)


        root.save_btn.setOnClickListener {

            if (haftaArray.contains(true)) {

                val soat = root.num_picker1.value
                val min = root.num_picker2.value

                println("$soat:$min")

                setAlarm()

                val list = MySharedPrefarance.alarmList
                list.add(AlarmObekt("", soat, min, true, root.switch_compat2.isChecked, haftaArray))
                MySharedPrefarance.alarmList = list

                findNavController().popBackStack()
                showinter2()

            }
            else{
                Snackbar.make(root, "No day of the week selected", 1000).show()
            }
        }

        return root
    }

    @SuppressLint("ShortAlarm", "UnspecifiedImmutableFlag")
    private fun setAlarm() {

        //sams
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotifyFragment::class.java)

            //samsend

        pendingIntent = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,1000,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

            //
//        val intent1 = Intent(
//            context,
//            HomeFragment::class.java
//        )
//        intent1.putExtra("yes", true)
//        intent1.addFlags(
//            Intent.FLAG_ACTIVITY_SINGLE_TOP
//                    or FLAG_ACTIVITY_NEW_TASK
//        )
//        val pendingIntent1 = PendingIntent.getActivity(
//            context, 0, intent1, PendingIntent.FLAG_ONE_SHOT
//        )
//        val intent2 = Intent(
//            context, HomeFragment::class.java
//        )
//        intent2.putExtra("no", false)
//        intent2.addFlags(
//            Intent.FLAG_ACTIVITY_SINGLE_TOP
//        )
//        val pendingintent2 = PendingIntent.getActivity(
//            context, 1, intent2,
//            PendingIntent.FLAG_ONE_SHOT
//        )
        //

    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name : CharSequence = "CharSquence"
            val description = "CHANNEL CONA"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("ConaMobile",name,importance)
            channel.description = description
            val manager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    private fun showinter() {
        if (mInterstitialAd!=null){
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    requireActivity().onBackPressed()
                }

                override fun onAdDismissedFullScreenContent() {
                    requireActivity().onBackPressed()
                }
            }
            mInterstitialAd?.show(requireActivity())
        }else{
            requireActivity().onBackPressed()
        }
    }

    private fun showinter2() {
        if (mInterstitialAd!=null){
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback(){
                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                }

                override fun onAdDismissedFullScreenContent() {
                    Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                }
            }
            mInterstitialAd?.show(requireActivity())
        }else{
            Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadInterstitial() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(root.context, getString(R.string.interstital_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }

    private fun loadInterstitial2() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(root.context, getString(R.string.interstital_id), adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }

}