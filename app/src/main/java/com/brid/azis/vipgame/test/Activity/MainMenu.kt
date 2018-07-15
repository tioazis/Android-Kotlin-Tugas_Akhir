package com.brid.azis.vipgame.test.Activity

import android.content.Intent
import android.nfc.NfcAdapter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.brid.azis.vipgame.R.layout.activity_main_menu
import com.brid.azis.vipgame.test.Util.NFCutil
import kotlinx.android.synthetic.main.activity_main_menu.*
import org.jetbrains.anko.toast


class MainMenu : AppCompatActivity() {

    //NFC Variabel
    private var mNfcAdapter: NfcAdapter? = null
    private var mNfcMessage: String? = null //pesan NFC

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_main_menu)
        toast("${tv_menyapa.text} ${tv_pemain_nama.text} Level Anda ${tv_pemain_level.text}")

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        mNfcMessage = NFCutil.retrieveNFCMessage(this.intent)
        toast(mNfcMessage!!)

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
            NFCutil.enableNFCInForeground(it, this, javaClass)
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
        toast(mNfcMessage!! + " test ")
    }




}
