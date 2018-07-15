package com.brid.azis.vipgame.test.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.test.DataModel.DataCard

class MissionViewAdapter(private val context: Context, private val cards:List<DataCard>):
        RecyclerView.Adapter<MissionViewAdapter.CardViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            CardViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mission,parent,false))

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder:CardViewHolder, position: Int) {
        holder.bindCards(cards[position])
    }

    class CardViewHolder(view:View):RecyclerView.ViewHolder(view){

        val judul = view.findViewById<TextView>(R.id.tv_judulkartu)
        val petunjuk = view.findViewById<TextView>(R.id.tv_petunjukkartu)
        val tanggal = view.findViewById<TextView>(R.id.tv_tanggalmasukkartu)

        fun bindCards(cards:DataCard){
            judul.text = cards.judul
            petunjuk.text = cards.petunjuk
            tanggal.text = cards.tanggal
        }
    }
}