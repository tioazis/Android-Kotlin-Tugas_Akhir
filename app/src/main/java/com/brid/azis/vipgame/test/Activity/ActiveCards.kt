package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.layout.activity_active_cards
import com.brid.azis.vipgame.R.layout.pop_up_scan_teacher_card
import com.brid.azis.vipgame.test.Adapter.ActiveCardsViewAdapter
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.Database.DBOnGoingMissionHelper
import com.brid.azis.vipgame.test.Database.missionDB
import com.brid.azis.vipgame.test.Util.NFCutil
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class ActiveCards : AppCompatActivity() {

    var MNfcAdapter: NfcAdapter? = null
    var mNfcMessage: String? = null

    private var dataCards :MutableList<DataCard> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_active_cards)

        MNfcAdapter = NfcAdapter.getDefaultAdapter(this)

        Toast.makeText(this,getString(R.string.TOAST_PETUNJUK), Toast.LENGTH_LONG).show()

        val list = findViewById<RecyclerView>(R.id.active_mission_list)
        initData()

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = ActiveCardsViewAdapter(dataCards) { dataCard: DataCard -> cardsClicked(dataCard) }

    }

    private fun initData() {
        missionDB.use {
            val result = select(DataCard.TABLE_USERCARD)
            val cards = result.parseList(classParser<DataCard>())
            dataCards.addAll(cards)
        }

    }

    private fun cardsClicked(cards:DataCard){
        Toast.makeText(this,"Clicked: ${cards.title}",Toast.LENGTH_SHORT).show()

        mNfcMessage = NFCutil.retrieveNFCMessage(this.intent)
        Toast.makeText(this,mNfcMessage!!,Toast.LENGTH_SHORT).show()


        val window = PopupWindow(this)
        val view = layoutInflater.inflate(pop_up_scan_teacher_card,null)
        window.contentView = view
        val button = view.findViewById<Button>(R.id.btn_cancelCard)


        button.setOnClickListener {
            window.dismiss()
        }

        window.showAsDropDown(view)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        toast(mNfcMessage!!)
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



}
