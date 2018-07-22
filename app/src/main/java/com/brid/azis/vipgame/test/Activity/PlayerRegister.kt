package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.id.tv_regis_login
import com.brid.azis.vipgame.test.DataModel.DataPlayer
import com.brid.azis.vipgame.test.Database.DBPlayerHelper
import com.brid.azis.vipgame.test.Util.InputValidation
import com.facebook.stetho.Stetho

import org.jetbrains.anko.toast

class PlayerRegister : AppCompatActivity() {
    private val activity = this@PlayerRegister

    private lateinit var lay_regis_fullname: TextInputLayout
    private lateinit var lay_regis_username: TextInputLayout
    private lateinit var lay_regis_password: TextInputLayout
    private lateinit var lay_regis_confirmpassword: TextInputLayout

    private lateinit var et_regis_username: TextInputEditText
    private lateinit var et_regis_password: TextInputEditText
    private lateinit var et_regis_fullname: TextInputEditText
    private lateinit var et_regis_confirmpassword: TextInputEditText

    private lateinit var btn_regis_signup :Button
    private lateinit var tv_regis_login :TextView

    private lateinit var dbPlayerHelper: DBPlayerHelper
    private lateinit var inputValidation: InputValidation



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_register)

        Stetho.initializeWithDefaults(this)

        initViews()

        dbPlayerHelper = DBPlayerHelper(activity)
        inputValidation = InputValidation(activity)

        tv_regis_login.setOnClickListener(){
            startActivity(Intent(this,PlayerLogin::class.java))
            finish()
        }

        btn_regis_signup.setOnClickListener(){
            enterData()

        }

    }

    private fun initViews(){
        lay_regis_fullname = findViewById<View>(R.id.lay_regis_fullname  ) as TextInputLayout
        lay_regis_username = findViewById<View>(R.id.lay_regis_username  ) as TextInputLayout
        lay_regis_password = findViewById<View>(R.id.lay_regis_password  ) as TextInputLayout
        lay_regis_confirmpassword = findViewById<View>(R.id.lay_regis_confirmpassword  ) as TextInputLayout

        et_regis_fullname = findViewById<View>(R.id.et_regis_fullname  ) as TextInputEditText
        et_regis_username = findViewById<View>(R.id.et_regis_username  ) as TextInputEditText
        et_regis_password = findViewById<View>(R.id.et_regis_password  ) as TextInputEditText
        et_regis_confirmpassword = findViewById<View>(R.id.et_regis_confirmpassword  ) as TextInputEditText

        tv_regis_login = findViewById<View>(R.id.tv_regis_login  ) as TextView
        btn_regis_signup = findViewById<View>(R.id.btn_regis_signup  ) as Button
    }

  private fun enterData(){
      if (!inputValidation!!.isInputEditTextFilled(et_regis_fullname, lay_regis_fullname, getString(R.string.error_message_name))) {
          return
      }
      if (!inputValidation!!.isInputEditTextFilled(et_regis_username, lay_regis_username, getString(R.string.error_message_email))) {
          return
      }
      if (!inputValidation!!.isInputEditTextFilled(et_regis_password, lay_regis_password, getString(R.string.error_message_password))) {
          return
      }
      if (!inputValidation!!.isInputEditTextMatches(et_regis_password, et_regis_confirmpassword,
                      lay_regis_confirmpassword, getString(R.string.error_password_match))) {
          return
      }

      if (!dbPlayerHelper!!.checkUser(et_regis_username!!.text.toString().trim())) {

          var user = DataPlayer(
                  username = et_regis_username!!.text.toString().trim(),
                  password = et_regis_password!!.text.toString().trim(),
                  name = et_regis_fullname!!.text.toString().trim(),
                  point = 9,
                  exp = 1,
                  level = 1,
                  missionCompleted = 0   )

          dbPlayerHelper!!.addUser(user)

          // Snack Bar to show success message that record saved successfully
          Toast.makeText(this,"Data sukses dimasukkan",Toast.LENGTH_SHORT).show()
          startActivity(Intent(this,PlayerLogin::class.java))
          finish()
          emptyInputEditText()


      } else {
          // Snack Bar to show error message that record already exists
          Toast.makeText(this,"Data sudah ada",Toast.LENGTH_SHORT).show()

      }
  }

    private fun emptyInputEditText() {
        et_regis_username!!.text = null
        et_regis_fullname!!.text = null
        et_regis_password!!.text = null
        et_regis_confirmpassword!!.text = null
    }




}
