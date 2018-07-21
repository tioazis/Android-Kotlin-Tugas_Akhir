package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brid.azis.vipgame.R
import kotlinx.android.synthetic.main.activity_login_player.*
import kotlinx.android.synthetic.main.activity_player_register.*

class PlayerLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_player)

        overridePendingTransition(R.anim.fade_in,R.anim.fade_out) // splash

        tv_register.setOnClickListener(){
            startActivity(Intent(this,PlayerRegister::class.java))
        }

        btn_reset.setOnClickListener(){
            et_login_password.setText("")
            et_login_username.setText("")
        }

        btn_submit.setOnClickListener(){
            startActivity(Intent(this,MainMenu::class.java))

        }


    }


}
