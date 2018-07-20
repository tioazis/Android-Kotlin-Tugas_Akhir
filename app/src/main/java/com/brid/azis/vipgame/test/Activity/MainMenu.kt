package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.layout.activity_main_menu
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.Database.DBOnGoingMissionHelper
import com.brid.azis.vipgame.test.Database.missionDB
import com.brid.azis.vipgame.test.Util.NFCutil
import com.facebook.stetho.Stetho
import kotlinx.android.synthetic.main.activity_main_menu.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*


class MainMenu : AppCompatActivity() {

    //NFC Variabel
     var mNfcAdapter: NfcAdapter? = null
     var mNfcMessage: String? = null //pesan NFC

    private var dbCard = DBOnGoingMissionHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main_menu)

        overridePendingTransition(R.anim.fade_in,R.anim.fade_out) // splash

        toast("${tv_greetings.text} ${tv_playerName.text} Level Anda ${tv_playerLevel.text}")

        Stetho.initializeWithDefaults(this)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        mNfcMessage = NFCutil.retrieveNFCMessage(this.intent)

        toast(mNfcMessage!!)

        //cardDataInit() //mengambil data dary resource array

        btn_db.setOnClickListener{
            startActivity(Intent(this,InsertDbTest::class.java))
        }

        btn_misi_aktif.setOnClickListener{
            startActivity(Intent(this,ActiveCards::class.java))
        }

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

    override  fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        val newMessage: String?
        newMessage = NFCutil.retrieveNFCMessage(intent)
        toast("""${intent?.action.toString()} $newMessage""")

        addCardData(newMessage.toString().toInt())

        val messageWrittenSuccessfully = NFCutil.createNFCMessage("3", intent)
        toast(messageWrittenSuccessfully.ifElse("Sukses menginput data", "Input data gagal, silahkan ulangi"))



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
                SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date()),
                false,
                "")
        toast(card.title!![id].toString())
        addToUserCardDB(card)

    }

    private fun addToUserCardDB(card: DataCard){
        try{
            missionDB.use {
                insert(DataCard.TABLE_USERCARD,
                        DataCard.CARD_ID to card.id,
                        DataCard.CARD_TITLE to card.title,
                        DataCard.CARD_INSTRUCTION to card.instruction,
                        DataCard.CARD_TYPE to card.cardType,
                        DataCard.CARD_LEVEL to card.level,
                        DataCard.CARD_EXP to card.rewardExp,
                        DataCard.CARD_REWARD to card.rewardPoint,
                        DataCard.CARD_ISDONE to card.isDone,
                        DataCard.CARD_INPUTDATE to card.inputdate,
                        DataCard.CARD_CHECKBY to card.checkBy)
            }
            Toast.makeText(this,"Data Berhasil Masuk", Toast.LENGTH_SHORT)
        } catch (e: SQLiteConstraintException){
            Toast.makeText(this,"Data Tidak Masuk : $e", Toast.LENGTH_SHORT)
        }
    }

    fun <T> Boolean.ifElse(primaryResult: T, secondaryResult: T) = if (this) primaryResult else secondaryResult

}
