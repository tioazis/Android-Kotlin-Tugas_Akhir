package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.layout.activity_insert_db_test
import com.brid.azis.vipgame.R.layout.activity_main_menu
import com.brid.azis.vipgame.test.Util.NFCutil
import kotlinx.android.synthetic.main.activity_insert_db_test.*
import kotlinx.android.synthetic.main.activity_main_menu.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast


class MainMenu : AppCompatActivity() {

    //NFC Variabel
    private var mNfcAdapter: NfcAdapter? = null
    private var mNfcMessage: String? = null //pesan NFC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main_menu)

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        mNfcMessage = NFCutil.retrieveNFCMessage(this.intent)
        toast(mNfcMessage!!)

        btn_db.setOnClickListener{
            startActivity(Intent(this,InsertDbTest::class.java))
        }

        btn_misi_aktif.setOnClickListener{
            startActivity(Intent(this,ActiveMission::class.java))
        }



    }

    override fun onResume() {
        super.onResume()
        toast("on resume")
        mNfcAdapter?.let {
            NFCutil.enableNFCInForeground(it, this, javaClass)
        }
    }

    override fun onPause() {
        super.onPause()
        toast("on pause")

        mNfcAdapter?.let {
            NFCutil.disableNFCInForeground(it, this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        toast(mNfcMessage!! + " test ")
    }




}
