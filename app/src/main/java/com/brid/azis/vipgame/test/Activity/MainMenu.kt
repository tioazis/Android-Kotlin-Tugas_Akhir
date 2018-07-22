package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Debug
import android.util.Log
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
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.time.Clock
import java.util.*


class MainMenu : AppCompatActivity() {

    //NFC Variabel
     var mNfcAdapter: NfcAdapter? = null
     var mNfcMessage: String? = null //pesan NFC

     var curPlayer : CurrentPlayer? = null

    private val activity = this@MainMenu
    private lateinit var dbCardHelper : DBOnGoingMissionHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main_menu)

        val playerPref = MyPlayerPref(this)
        val passedUsername:String = intent.getStringExtra("username")

        dbCardHelper = DBOnGoingMissionHelper(activity)
        Log.d("debug", "DBOnGOingMission Helper Created")

        curPlayer = applicationContext as CurrentPlayer
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        Stetho.initializeWithDefaults(this)

        initDataDbToSingleton(passedUsername)
        initViewFromSingleton()


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
            curPlayer!!.ResetAll()
            playerPref.resetPlayerPref()
            startActivity(Intent(this,SplashScreen::class.java))
            finish()
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

        val newMessage: String?
        newMessage = NFCutil.retrieveNFCMessage(intent)
        toast("""${intent?.action.toString()} $newMessage""")

        addCardData(newMessage.toString().toInt())

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
        Log.d("debug", "Add card")


//        try{
//            missionDB.use {
//                insert(DataCard.TABLE_USERCARD,
//                        DataCard.CARD_ID to card.id,
//                        DataCard.CARD_TITLE to card.title,
//                        DataCard.CARD_INSTRUCTION to card.instruction,
//                        DataCard.CARD_TYPE to card.cardType,
//                        DataCard.CARD_LEVEL to card.level,
//                        DataCard.CARD_EXP to card.rewardExp,
//                        DataCard.CARD_REWARD to card.rewardPoint,
//                        DataCard.CARD_ISDONE to card.isDone,
//                        DataCard.CARD_INPUTDATE to card.inputdate,
//                        DataCard.CARD_FINISHDATE to card.finishDate,
//                        DataCard.CARD_CHECKBY to card.checkBy)
//            }
//            Toast.makeText(this,"Data Berhasil Masuk", Toast.LENGTH_SHORT)
//        } catch (e: SQLiteConstraintException){
//            Toast.makeText(this,"Data Tidak Masuk : $e", Toast.LENGTH_SHORT)
//        }
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


    }

    private fun initViewFromSingleton(){
        tv_menu_playerLevel.text = curPlayer!!.GetPlayerLevel().toString()
        tv_menu_playerPoints.text = curPlayer!!.GetPlayerPoint().toString()
        tv_menu_playerName.text = curPlayer!!.GetPlayerName()
    }

    fun <T> Boolean.ifElse(primaryResult: T, secondaryResult: T) = if (this) primaryResult else secondaryResult


}
