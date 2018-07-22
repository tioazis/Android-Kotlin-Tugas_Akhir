package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.ContactsContract
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.DataModel.DataPlayer
import com.brid.azis.vipgame.test.Database.DBOnGoingMissionHelper
import com.brid.azis.vipgame.test.SharedPreferences.MyPlayerPref
import com.brid.azis.vipgame.test.SharedPreferences.PREFERENCE_PLAYER_IS_LOGGED_IN
import com.brid.azis.vipgame.test.SharedPreferences.PREFERENCE_PLAYER_USERNAME

import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.db.*

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_splash_screen)

        val playerPref = MyPlayerPref(this)
        val isLoggedIn =playerPref.getPlayerIsLoggedIn(PREFERENCE_PLAYER_IS_LOGGED_IN)

        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        iv_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))

        Handler().postDelayed({
            iv_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                iv_logo.visibility = View.GONE

                if (isLoggedIn){
                    val intent = Intent(this,MainMenu::class.java)
                    intent.putExtra("username",playerPref.getPlayerUsername(PREFERENCE_PLAYER_USERNAME))
                    startActivity(intent)
                }
                else{
                    startActivity(Intent(this,PlayerLogin::class.java))
                }

                finish()
            },500)
        },2500)
    }

}
