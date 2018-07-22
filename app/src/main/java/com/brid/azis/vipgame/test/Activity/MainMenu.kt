package com.brid.azis.vipgame.test.Activity

import android.app.Dialog
import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.layout.activity_main_menu
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.DataModel.DataPlayer
import com.brid.azis.vipgame.test.Database.DBOnGoingMissionHelper
import com.brid.azis.vipgame.test.Database.DBPlayerHelper
import com.brid.azis.vipgame.test.Database.missionDB
import com.brid.azis.vipgame.test.Database.playerDB
import com.brid.azis.vipgame.test.SharedPreferences.MyPlayerPref
import com.brid.azis.vipgame.test.Singleton.CurrentPlayer
import com.brid.azis.vipgame.test.Util.NFCutil
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.pop_up_validation_buy_card.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.time.Clock
import java.util.*
import kotlin.math.log


class MainMenu : AppCompatActivity() {

    //NFC Variabel
    var mNfcAdapter: NfcAdapter? = null
    var mNfcMessage: String? = null //pesan NFC

    var curPlayer : CurrentPlayer? = null

    var state :Int = 0
    private lateinit var scanBuyDialog : Dialog
    private lateinit var logOutDialog : Dialog

    private val activity = this@MainMenu
    private lateinit var dbCardHelper : DBOnGoingMissionHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main_menu)

        val playerPref = MyPlayerPref(this)
        val passedUsername:String = intent.getStringExtra("username")

        scanBuyDialog = Dialog (this)
        scanBuyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        scanBuyDialog.setContentView(R.layout.pop_up_validation_buy_card)

        logOutDialog = Dialog (this)
        logOutDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        logOutDialog.setContentView(R.layout.pop_up_validation_log_out)

        dbCardHelper = DBOnGoingMissionHelper(activity)
        Log.d("debug", "DBOnGOingMission Helper Created")

        curPlayer = applicationContext as CurrentPlayer
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        Stetho.initializeWithDefaults(this)

        initDataDbToSingleton(passedUsername)


        btn_db.setOnClickListener{
            startActivity(Intent(this,InsertDbTest::class.java))
        }

        btn_unfinished_activity.setOnClickListener{
            startActivity(Intent(this,CardsUnfinished::class.java))
        }

        btn_finished_activity.setOnClickListener{
            startActivity(Intent(this,CardsFinished::class.java))
        }

        lay_menu_point.setOnClickListener(){
            Toast.makeText(this,"Poin anda saat ini ${tv_menu_playerPoints.text.toString()}",Toast.LENGTH_SHORT).show()
        }

        btn_menu_log_out.setOnClickListener(){

            logOutDialog.show()

            val buttonLogOut = logOutDialog.findViewById<View>(R.id.btn_popup_buy_log_out)
            val buttonCancel = logOutDialog.findViewById<View>(R.id.btn_popup_cancel_log_out)

            buttonLogOut.setOnClickListener {
                curPlayer!!.ResetAll()
                playerPref.resetPlayerPref()
                startActivity(Intent(this,SplashScreen::class.java))
                finish()

            }

            buttonCancel.setOnClickListener {
                logOutDialog.cancel()

            }



        }

//        dbCardHelper.createTableCard()

        toast("${tv_greetings.text} ${tv_menu_playerName.text} Level Anda ${tv_menu_playerLevel.text}")
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter?.let {
            NFCutil.enableNFCInForegroundWhileRead(it, this,javaClass)
        }
    }

    override fun onPause() {
        super.onPause()

        mNfcAdapter?.let {
            NFCutil.disableNFCInForeground(it, this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val point = resources.getIntArray(R.array.card_reward)

        val newMessage: String?
        newMessage = NFCutil.retrieveNFCMessage(intent)

        val buttonBuy = scanBuyDialog.findViewById<View>(R.id.btn_popup_buy_validation)
        val buttonCancel = scanBuyDialog.findViewById<View>(R.id.btn_popup_cancel_validation)

        val pointNow = curPlayer!!.GetPlayerPoint()
        val price = point[newMessage.toString().toInt()]
//        toast("""${intent?.action.toString()} $newMessage""")

        if (pointNow - price >=0){
            scanBuyDialog.show()
        }

        else{
            Toast.makeText(this,"Point anda tidak cukup untuk membeli kartu",Toast.LENGTH_SHORT).show()
        }

        buttonBuy.setOnClickListener {
            addCardData(newMessage.toString().toInt())
            curPlayer!!.lessPointBy(point[newMessage.toString().toInt()])
            setCurrentPlayerDataToDatabase(curPlayer!!.GetPlayerEmail())
            restartActivity(intent)
        }
        buttonCancel.setOnClickListener(){
            scanBuyDialog.cancel()
        }



    }


    private fun addCardData(id:Int){
        val title = resources.getStringArray(R.array.card_title)
        val instruction = resources.getStringArray(R.array.card_instruction)
        val exp = resources.getIntArray(R.array.card_exp)
        val reward = resources.getIntArray(R.array.card_reward)
        val level = resources.getIntArray(R.array.card_level)

        var card = DataCard(id,
                title[id],
                instruction[id],
                1,
                level[id],
                reward[id],
                exp[id],
                SimpleDateFormat("dd-MM-yyyy hh:mm" , Locale.getDefault()).format(Date()),
                getString(R.string.not_yet_started),
                false,
                "")
        addToUserCardDB(card)

    }

    private fun addToUserCardDB(card: DataCard){
        dbCardHelper.addCard(card)


    }

    private fun initDataDbToSingleton(username:String){
        var player : DataPlayer? = null

        playerDB.use {
            val result = select(DataPlayer.TABLE_PLAYER).
                    whereArgs("${DataPlayer.PLAYER_USERNAME} = '$username'")
            player = result.parseSingle(classParser())
        }

        curPlayer!!.SetPlayerEmail(player!!.username)
        curPlayer!!.SetPlayerPassword(player!!.password)
        curPlayer!!.SetPlayerName(player!!.name)
        curPlayer!!.SetPlayerPoint(player!!.point)
        curPlayer!!.SetPlayerLevel(player!!.level)
        curPlayer!!.SetPlayerExp(player!!.exp)
        curPlayer!!.SetPlayerMissionCompleted(player!!.missionCompleted)

        initViewFromSingleton()


    }

    private fun initViewFromSingleton(){
        tv_menu_playerLevel.text = curPlayer!!.GetPlayerLevel().toString()
        tv_menu_playerPoints.text = curPlayer!!.GetPlayerPoint().toString()
        tv_menu_playerName.text = curPlayer!!.GetPlayerName()
    }

    private fun setCurrentPlayerDataToDatabase(username: String){


        playerDB.use {
            update(DataPlayer.TABLE_PLAYER,
                    DataPlayer.PLAYER_POINT to curPlayer!!.GetPlayerPoint(),
                    DataPlayer.PLAYER_EXP to curPlayer!!.GetPlayerExp(),
                    DataPlayer.PLAYER_LEVEL to curPlayer!!.GetPlayerLevel(),
                    DataPlayer.PLAYER_POINT to curPlayer!!.GetPlayerPoint(),
                    DataPlayer.PLAYER_PASSWORD to curPlayer!!.GetPlayerMissionCompleted()).
                    whereArgs("${DataPlayer.PLAYER_USERNAME} = '$username'").exec()
        }
    }

    fun <T> Boolean.ifElse(primaryResult: T, secondaryResult: T) = if (this) primaryResult else secondaryResult

    fun restartActivity(intent: Intent?){
        finish()
        startActivity(getIntent())
    }


}
