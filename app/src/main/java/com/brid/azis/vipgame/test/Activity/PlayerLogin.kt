package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.Toast
import com.brid.azis.vipgame.R
import kotlinx.android.synthetic.main.activity_player_login.*
import com.brid.azis.vipgame.test.Database.DBPlayerHelper
import com.brid.azis.vipgame.test.SharedPreferences.MyPlayerPref
import com.brid.azis.vipgame.test.SharedPreferences.PREFERENCE_PLAYER_IS_LOGGED_IN
import com.brid.azis.vipgame.test.SharedPreferences.PREFERENCE_PLAYER_USERNAME
import com.brid.azis.vipgame.test.Util.InputValidation


class PlayerLogin : AppCompatActivity() {

    private val activity = this@PlayerLogin

    private lateinit var dbPlayerHelper: DBPlayerHelper
    private lateinit var inputValidation: InputValidation
    lateinit var playerPref: MyPlayerPref


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_login)

        overridePendingTransition(R.anim.fade_in,R.anim.fade_out) // splash
        playerPref = MyPlayerPref(this)



        inputValidation = InputValidation(activity)
        dbPlayerHelper = DBPlayerHelper(activity)



        tv_register.setOnClickListener(){
            startActivity(Intent(this,PlayerRegister::class.java))
            finish()
        }

        btn_reset.setOnClickListener(){
           emptyInputEditText()
        }

        btn_submit.setOnClickListener(){
            verifyFromSQLite()

        }


    }

    private fun verifyFromSQLite() {

        if (!inputValidation!!.isInputEditTextFilled(et_login_username!!, lay_login_username!!, getString(R.string.error_message_email))) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(et_login_password!!, lay_login_password!!, getString(R.string.error_message_email))) {
            return
        }

        if (dbPlayerHelper!!.checkUser(et_login_username!!.text.toString().trim { it <= ' ' }, et_login_password!!.text.toString().trim { it <= ' ' })) {


            playerPref.setPlayerIsLoggedIn(true)
            playerPref.setPlayerUsername(et_login_username!!.text.toString().trim { it <= ' ' })

            val accountsIntent = Intent(activity, MainMenu::class.java)
            accountsIntent.putExtra("username", et_login_username!!.text.toString().trim { it <= ' ' })
            startActivity(accountsIntent)
            emptyInputEditText()
            finish()



        } else {

            // Snack Bar to show success message that record is wrong
            Toast.makeText(this, "Gagal Masuk, silahkan perbaiki data anda", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        et_login_username!!.text = null
        et_login_password!!.text = null
    }


}
