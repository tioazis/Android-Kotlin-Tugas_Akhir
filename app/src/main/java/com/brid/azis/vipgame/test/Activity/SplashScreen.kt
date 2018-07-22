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

import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.jetbrains.anko.db.*

class SplashScreen : AppCompatActivity() {

    var IS_LOGGED_IN:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()

        setContentView(R.layout.activity_splash_screen)


        overridePendingTransition(R.anim.fade_in,R.anim.fade_out)
        iv_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_in))

        Handler().postDelayed({
            iv_logo.startAnimation(AnimationUtils.loadAnimation(this,R.anim.splash_out))
            Handler().postDelayed({
                iv_logo.visibility = View.GONE

                if (IS_LOGGED_IN == 1){
                    startActivity(Intent(this,MainMenu::class.java))
                }
                else{
                    startActivity(Intent(this,PlayerLogin::class.java))
                }

                finish()
            },500)
        },2500)
    }

}
