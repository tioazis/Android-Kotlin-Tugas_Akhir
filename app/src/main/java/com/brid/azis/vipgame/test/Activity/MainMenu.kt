package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.layout.activity_main_menu
import com.brid.azis.vipgame.test.Database.DbCardData
import com.brid.azis.vipgame.test.Model.Card
import com.brid.azis.vipgame.test.Util.NFCutil
import kotlinx.android.synthetic.main.activity_main_menu.*
import org.jetbrains.anko.toast



class MainMenu : AppCompatActivity() {

    //NFC Variabel
     var mNfcAdapter: NfcAdapter? = null
     var mNfcMessage: String? = null //pesan NFC

    var dbCard = DbCardData(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main_menu)
        toast("${tv_menyapa.text} ${tv_pemain_nama.text} Level Anda ${tv_pemain_level.text}")

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        mNfcMessage = NFCutil.retrieveNFCMessage(this.intent)
        toast(mNfcMessage!!)

        cardDataInit() //mengambil data dary resource array

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

        toast(intent?.action.toString())

        val newMessage: String?
        newMessage = NFCutil.retrieveNFCMessage(intent)
        toast("""${intent?.action.toString()} $newMessage""")


    }



    private fun cardDataInit(){
        val title = resources.getStringArray(R.array.card_title)
        val instruction = resources.getStringArray(R.array.card_instruction)
        val exp = resources.getIntArray(R.array.card_exp)
        val reward = resources.getIntArray(R.array.card_reward)
        val level = resources.getIntArray(R.array.card_level)

        var card:Card

        dbCard.deleteData()

        for(i in title.indices){
            var card = Card(title[i],instruction[i],1,exp[i],reward[i],level[i])
            dbCard.insertData(card)

        }
    }




}
