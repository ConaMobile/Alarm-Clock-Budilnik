package com.conamobile.alarmclockbudilnik

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.findNavController
import com.conamobile.alarmclockbudilnik.Adapters.RvAdapter
import com.conamobile.alarmclockbudilnik.Models.AlarmObekt
import com.conamobile.alarmclockbudilnik.Utils.MySharedPrefarance
import com.google.android.gms.ads.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    lateinit var root:View
    lateinit var rvAdapter: RvAdapter
    lateinit var mAdView : AdView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_home, container, false)

        //google ads
        MobileAds.initialize(root.context) {}
        mAdView = root.adView

        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

//        mAdView.adListener = object : AdListener() {
//            override fun onAdLoaded() {
//            }
//
//            override fun onAdFailedToLoad(adError: LoadAdError) {
//            }
//
//            override fun onAdOpened() {
//            }
//
//            override fun onAdClicked() {
//            }
//
//            override fun onAdClosed() {
//            }
//        }
        //google ads end

        root.add_time.setOnClickListener {
            root.findNavController().navigate(R.id.adderFragment)
        }

        return root
    }

    override fun onResume() {
        super.onResume()



        MySharedPrefarance.init(context)
        val list = MySharedPrefarance.alarmList
        rvAdapter = RvAdapter(list, object : RvAdapter.RvClick{
            override fun longClick(alarmObekt: AlarmObekt, position: Int) {
                MySharedPrefarance.init(context)
                val mL = MySharedPrefarance.alarmList
                mL.removeAt(position)
                MySharedPrefarance.alarmList = mL
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
                onResume()
            }
            override fun switchOnOff(alarmObekt: AlarmObekt, position: Int) {
                MySharedPrefarance.init(context)
                val ml = MySharedPrefarance.alarmList
                ml[position] = alarmObekt
                MySharedPrefarance.alarmList = ml
                onResume()
            }
        })
        root.rv.adapter = rvAdapter
    }
}