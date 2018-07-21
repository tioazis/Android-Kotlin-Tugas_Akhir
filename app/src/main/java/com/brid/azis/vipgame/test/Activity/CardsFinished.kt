package com.brid.azis.vipgame.test.Activity

import android.content.Context
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.test.Adapter.CardsViewAdapter
import com.brid.azis.vipgame.test.DataModel.DataCard
import com.brid.azis.vipgame.test.Database.missionDB
import kotlinx.android.synthetic.main.pop_up_scan_teacher_card.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class CardsFinished : AppCompatActivity() {


    private var dataCards :MutableList<DataCard> = mutableListOf()
    private var cardsId:Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cards_finished)

        val listOfCards = findViewById<RecyclerView>(R.id.nonactive_mission_list)
        initData()

        listOfCards.layoutManager = LinearLayoutManager(this)
        listOfCards.adapter = CardsViewAdapter(dataCards) { dataCard: DataCard -> cardsClicked(dataCard) }

    }

    private fun initData() {
        dataCards.clear()
        missionDB.use {
            val result = select(DataCard.TABLE_USERCARD).whereArgs("(${DataCard.CARD_ISDONE} = 1)")
            val cards = result.parseList(classParser<DataCard>())
            dataCards.addAll(cards)
        }
    }

    private fun cardsClicked(cards:DataCard){
        Toast.makeText(this,"Clicked: ${cards.id}",Toast.LENGTH_SHORT).show()
        cardsId = cards.id.toString().toInt()
    }




}
