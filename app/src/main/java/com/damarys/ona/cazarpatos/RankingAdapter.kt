package com.damarys.ona.cazarpatos

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RankingAdapter(private val dataSet: ArrayList<Player>) : RecyclerView.Adapter<RecyclerView.ViewHolder>()  {
    private val TYPE_HEADER : Int = 0

    // ViewHolder para el encabezado
    class ViewHolderHeader(view : View) : RecyclerView.ViewHolder(view){
        val textViewPosicion: TextView = view.findViewById(R.id.textViewPosicion)
        val textViewPatosCazados: TextView = view.findViewById(R.id.textViewPatosCazados)
        val textViewUsuario: TextView = view.findViewById(R.id.textViewUsuario)
    }

    // ViewHolder para los elementos del ranking
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageViewMedal: ImageView = view.findViewById(R.id.imageViewMedal)
        val textViewPosicion: TextView = view.findViewById(R.id.textViewPosicion)
        val textViewPatosCazados: TextView = view.findViewById(R.id.textViewPatosCazados)
        val textViewUsuario: TextView = view.findViewById(R.id.textViewUsuario)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutId = if (viewType == TYPE_HEADER) R.layout.ranking_list else R.layout.ranking_list
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return if (viewType == TYPE_HEADER) ViewHolderHeader(view) else ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            // Si es el encabezado, se subraya el texto
            holder.textViewPosicion.text = "#"
            holder.textViewPosicion.paintFlags = holder.textViewPosicion.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            holder.textViewPosicion.setTextColor(holder.textViewPosicion.context.getColor(R.color.colorPrimaryDark))
            holder.textViewPatosCazados.paintFlags = holder.textViewPatosCazados.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            holder.textViewPatosCazados.setTextColor(holder.textViewPatosCazados.context.getColor(R.color.colorPrimaryDark))
            holder.textViewUsuario.paintFlags = holder.textViewUsuario.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            holder.textViewUsuario.setTextColor(holder.textViewUsuario.context.getColor(R.color.colorPrimaryDark))
        } else if (holder is ViewHolder) {
            val player = dataSet[position - 1]

            // Asignar nombre y puntaje
            holder.textViewPosicion.text = (position).toString()
            holder.textViewPatosCazados.text = player.huntedDucks.toString()
            holder.textViewUsuario.text = player.username

            // Asignar medalla basada en el ranking
            when (position) {
                1 -> holder.imageViewMedal.setImageResource(R.drawable.gold) // Oro
                2 -> holder.imageViewMedal.setImageResource(R.drawable.silver) // Plata
                3 -> holder.imageViewMedal.setImageResource(R.drawable.bronze) // Bronce
                else -> holder.imageViewMedal.setImageDrawable(null) // Sin medalla
            }
        }
    }

    override fun getItemCount() = dataSet.size + 1
}
