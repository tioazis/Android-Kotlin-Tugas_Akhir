package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brid.azis.vipgame.R
import kotlinx.android.synthetic.main.activity_player_register.*

class PlayerRegister : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_register)

        tv_regis_login.setOnClickListener(){
            startActivity(Intent(this,PlayerLogin::class.java))
            finish()
        }
    }
}
