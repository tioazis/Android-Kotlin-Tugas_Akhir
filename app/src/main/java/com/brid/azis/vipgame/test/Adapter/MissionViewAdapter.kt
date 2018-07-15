package com.brid.azis.vipgame.test.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.test.DataModel.DataCard
import org.jetbrains.anko.sdk25.coroutines.onClick

class MissionViewAdapter( val cards:List<DataCard>, val clickListener: (DataCard) -> Unit):
        RecyclerView.Adapter<MissionViewAdapter.CardViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_mission,parent,false)

        return CardViewHolder(view)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder:CardViewHolder, position: Int) {
        (holder as CardViewHolder).bindCards(cards[position],clickListener)

    }

    class CardViewHolder(view:View):RecyclerView.ViewHolder(view){

        val judul = view.findViewById<TextView>(R.id.tv_judulkartu)
        val petunjuk = view.findViewById<TextView>(R.id.tv_petunjukkartu)
        val tanggal = view.findViewById<TextView>(R.id.tv_tanggalmasukkartu)

        fun bindCards(cards:DataCard, clickListener: (DataCard) -> Unit){
            judul.text = cards.judul
            petunjuk.text = cards.petunjuk
            tanggal.text = cards.tanggal

            itemView.setOnClickListener{clickListener(cards)}


        }
    }
}