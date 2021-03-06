package com.brid.azis.vipgame.test.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import com.brid.azis.vipgame.R
import com.brid.azis.vipgame.test.DataModel.DataCard

class CardsViewAdapter(val cards:List<DataCard>, val clickListener: (DataCard) -> Unit):
        RecyclerView.Adapter<CardsViewAdapter.CardViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val inflater  = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_mission,parent,false)

        return CardViewHolder(view)
    }

    override fun getItemCount(): Int = cards.size

    override fun onBindViewHolder(holder:CardViewHolder, position: Int) {
        holder.bindCards(cards[position],clickListener)

    }

    class CardViewHolder(view:View):RecyclerView.ViewHolder(view){

        val judul = view.findViewById<TextView>(R.id.tv_judulkartu)
        val petunjuk = view.findViewById<TextView>(R.id.tv_petunjukkartu)
        val tanggalMulai = view.findViewById<TextView>(R.id.tv_cardInputDate)
        val tanggalSelesai = view.findViewById<TextView>(R.id.tv_cardOutputDate)

        fun bindCards(cards:DataCard, clickListener: (DataCard) -> Unit){
            judul.text = cards.title
            petunjuk.text = cards.instruction
            tanggalMulai.text = cards.inputdate
            tanggalSelesai.text = cards.finishDate

            itemView.setOnClickListener{clickListener(cards)}


        }
    }
}