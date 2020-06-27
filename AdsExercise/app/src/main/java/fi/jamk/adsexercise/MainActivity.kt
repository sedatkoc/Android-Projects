package fi.jamk.adsexercise

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.ads.AdListener
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import androidx.annotation.NonNull
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*

import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdCallback
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback



class MainActivity : AppCompatActivity() {
  lateinit var imageDice: ImageView
    lateinit var mAdView : AdView
    var count =0
    private lateinit var rewardedAd: RewardedAd
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* find the random number */
        val random = (1..6).shuffled().first()
        imageDice = findViewById(R.id.image_dice)
        image_dice.setOnClickListener(View.OnClickListener {
            rollDice()
        })


/* Banner Ads */
        val adView = AdView(this)
        adView.adSize = AdSize.BANNER
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
/* Banner Ads */
        rewardedAd = RewardedAd(this, "ca-app-pub-3940256099942544/5224354917")
        val adLoadCallback = object: RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                Log.d("TAG", "reward ad loaded")
            }
            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                Log.d("TAG", "reward ad failed to load")
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)

        btnLoad.setOnClickListener {
            if (rewardedAd.isLoaded) {
                val activityContext: Activity =this
                val adCallback = object: RewardedAdCallback() {
                    override fun onRewardedAdOpened() {
                        // Ad opened.
                    }
                    override fun onRewardedAdClosed() {
                        // Ad closed.
                    }
                    fun onUserEarnedReward(@NonNull reward: RewardItem) {
                        // User earned reward.
                        Log.d("TAG", "reward earned")
                    }

                    override fun onUserEarnedReward(p0: com.google.android.gms.ads.rewarded.RewardItem) {
                        TODO("Not yet implemented")
                    }

                    override fun onRewardedAdFailedToShow(errorCode: Int) {
                        // Ad failed to display.
                    }
                }
                rewardedAd.show(activityContext, adCallback)
            }
            else {
                Log.d("TAG", "The rewarded ad wasn't loaded yet.")
            }
        }
/* Rewarded Ads */

    }
    fun createAndLoadRewardedAd(): RewardedAd {
        val rewardedAd = RewardedAd(this, "ca-app-pub-3940256099942544/5224354917")
        val adLoadCallback = object: RewardedAdLoadCallback() {
            override fun onRewardedAdLoaded() {
                // Ad successfully loaded.
            }
            override fun onRewardedAdFailedToLoad(errorCode: Int) {
                // Ad failed to load.
            }
        }
        rewardedAd.loadAd(AdRequest.Builder().build(), adLoadCallback)
        return rewardedAd
    }
    fun onRewardedAdClosed() {
        this.rewardedAd = createAndLoadRewardedAd()
    }

    fun rollDice(){
        var x=(1..6).shuffled().first()

        if (x==1)imageDice.setImageResource(R.drawable.dice1)
        if (x==2)imageDice.setImageResource(R.drawable.dice2)
        if (x==3)imageDice.setImageResource(R.drawable.dice3)
        if (x==4)imageDice.setImageResource(R.drawable.dice4)
        if (x==5)imageDice.setImageResource(R.drawable.dice5)
        if (x==6)imageDice.setImageResource(R.drawable.dice6)

        var numeric = true
        var string = user_input_text.text
        numeric = string.matches("-?\\d+(\\.\\d+)?".toRegex())
        val num=string.toString().toInt()

        count++
        if (numeric) {
            val num=string.toString().toInt()
            if(num==x){
                text_result.text="You got it right! numOfTry="+count
                count=0
            }
            else{
                text_result.text="nOfAttempt "+count+" Unlucky! random no"+x
            }
        }
        else {
            println("$string is not a number")
        }

    }

}
