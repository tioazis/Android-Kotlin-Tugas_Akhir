package com.brid.azis.vipgame.test.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.layout.activity_active_cards
import com.brid.azis.vipgame.test.Adapter.ActiveCardsViewAdapter
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.Database.DbCardData
import com.brid.azis.vipgame.test.Database.DbOnGoingMission
import java.text.SimpleDateFormat
import java.util.*

class ActiveCards : AppCompatActivity() {

    private var cards: MutableList<DataCard> = mutableListOf()
    var db = DbCardData(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_active_cards)

        Toast.makeText(this,getString(R.string.TOAST_PETUNJUK), Toast.LENGTH_LONG).show()

        val list = findViewById<RecyclerView>(R.id.active_mission_list)
        initData()

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = ActiveCardsViewAdapter(cards) { dataCard: DataCard -> cardsClicked(dataCard) }

    }

    private fun initData() {
        var data = db.readData()
        val dayDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        for (i in 0..(data.size - 1)){
            cards.add(DataCard(data.get(i)._id,data.get(i).title,data.get(i).instruction, " $dayDate"))
        }

        /* TODO : perbaiki sistem pengambilan kalender*/


    }

    private fun cardsClicked(cards:DataCard){
        Toast.makeText(this,"Clicked: ${cards.judul}",Toast.LENGTH_SHORT).show()
    }




}
