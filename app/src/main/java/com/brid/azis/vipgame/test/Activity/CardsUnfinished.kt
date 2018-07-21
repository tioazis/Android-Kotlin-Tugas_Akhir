package com.brid.azis.vipgame.test.Activity

import android.app.Dialog
import android.content.Intent
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Window
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.layout.activity_cards_unfinished
import com.brid.azis.vipgame.test.Adapter.CardsViewAdapter
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.Database.missionDB
import com.brid.azis.vipgame.test.Util.NFCutil
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import java.text.SimpleDateFormat
import java.util.*

class CardsUnfinished : AppCompatActivity() {

    var MNfcAdapter: NfcAdapter? = null
    var mNfcMessage: String? = null

    private var stateOfRead:Int = 0
    private var cardsId:Int? = null
    private lateinit var scanDialog :Dialog

    private var dataCards :MutableList<DataCard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_cards_unfinished)

        scanDialog = Dialog (this)
        scanDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        scanDialog.setContentView(R.layout.pop_up_scan_teacher_card)

        MNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        Toast.makeText(this,getString(R.string.TOAST_PETUNJUK), Toast.LENGTH_LONG).show()

        val listOfCards = findViewById<RecyclerView>(R.id.active_mission_list)
        initData()

        listOfCards.layoutManager = LinearLayoutManager(this)
        listOfCards.adapter = CardsViewAdapter(dataCards) { dataCard: DataCard -> cardsClicked(dataCard) }
    }

    private fun initData() {
        dataCards.clear()
        missionDB.use {
            val result = select(DataCard.TABLE_USERCARD).whereArgs("(${DataCard.CARD_ISDONE} = 0)")
            val cards = result.parseList(classParser<DataCard>())
            dataCards.addAll(cards)
        }
    }

    private fun cardsClicked(cards:DataCard){
        Toast.makeText(this,"Clicked: ${cards.id}",Toast.LENGTH_SHORT).show()
        cardsId = cards.id.toString().toInt()


        scanTeacherCard(cardsId)
    }



    private fun scanTeacherCard(cardsID:Int?){


        val buttonPopup = scanDialog.findViewById<View>(R.id.btn_cancelCard)

        buttonPopup.setOnClickListener{
            scanDialog.cancel()
        }

        scanDialog.show()

        stateOfRead = 1

        scanDialog.setOnDismissListener {
            stateOfRead = 0
        }

    }

    override fun onResume() {
        super.onResume()
        MNfcAdapter?.let {
            NFCutil.enableNFCInForegroundWhileRead(it, this, javaClass)
        }
    }

    override fun onPause() {
        super.onPause()

        MNfcAdapter?.let {
            NFCutil.disableNFCInForeground(it, this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val newMessage: String?

        if (stateOfRead == 1){
            newMessage = NFCutil.retrieveNFCMessage(intent)
            Toast.makeText(this,"${intent?.action.toString()} $newMessage",Toast.LENGTH_SHORT).show()

            if(newMessage =="guru")

            missionDB.use {
                update(DataCard.TABLE_USERCARD,DataCard.CARD_ISDONE to "1",
                        DataCard.CARD_FINISHDATE to SimpleDateFormat("dd-MM-yyyy hh:mm" , Locale.getDefault()).format(Date()))
                        .whereArgs("${DataCard.CARD_ID} = ${cardsId.toString()}")
                        .exec()
            }

            scanDialog.cancel()
        }
    }

}
