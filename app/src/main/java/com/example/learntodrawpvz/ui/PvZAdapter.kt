package com.example.learntodrawpvz.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learntodrawpvz.R
import com.example.learntodrawpvz.databinding.PlantSelectItemBinding
import com.example.learntodrawpvz.entities.PvZ
import com.example.learntodrawpvz.extension.getResIdFromName

class PvZAdapter(
    val context: Context,
    val onItemClick: (Int) -> Unit
) : RecyclerView.Adapter<PvZAdapter.PvZHolder>() {

    private val pvzList = ArrayList(PvZ.values().map { it.pvZModel })

    inner class PvZHolder(private val binding: PlantSelectItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bindView(position: Int) {
            binding.imgPlant.setImageResource(context.getResIdFromName(pvzList[position].avatar))
            binding.imgDifficulty.setImageResource(context.getResIdFromName(pvzList[position].level))
            binding.tvNamePlant.text = pvzList[position].name
            binding.tvSteps.text = context.getString(R.string.step_format, pvzList[position].steps)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PvZHolder {
        val binding = PlantSelectItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PvZHolder(binding)
    }

    override fun onBindViewHolder(holder: PvZHolder, position: Int) {
        holder.bindView(position)
    }

    override fun getItemCount() = pvzList.size
}