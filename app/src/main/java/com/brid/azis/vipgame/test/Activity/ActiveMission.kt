package com.brid.azis.vipgame.test.Activity

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.R.id.active_mission_list
import com.brid.azis.vipgame.R.layout.activity_active_mission
import com.brid.azis.vipgame.test.Adapter.MissionViewAdapter
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.Database.DbOnGoingMission
import com.brid.azis.vipgame.test.Model.Card
import kotlinx.android.synthetic.main.activity_active_mission.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class ActiveMission : AppCompatActivity() {

    private var cards: MutableList<DataCard> = mutableListOf()
    var db = DbOnGoingMission(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_active_mission)

        Toast.makeText(this,getString(R.string.TOAST_PETUNJUK), Toast.LENGTH_LONG).show()

        val list = findViewById<RecyclerView>(R.id.active_mission_list)
        initData()

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = MissionViewAdapter(this,cards)
    }

    private fun initData(){
        var data = db.readData()

        val dayDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


        for (i in 0..(data.size - 1)){
            cards.add(DataCard(data.get(i).title,data.get(i).instruction, " $dayDate"))
        }

        //logic date bermasalah


    }
}
