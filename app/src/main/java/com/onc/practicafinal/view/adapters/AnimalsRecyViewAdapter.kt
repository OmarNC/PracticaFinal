package com.onc.practicafinal.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.onc.practicafinal.R
import com.onc.practicafinal.databinding.RecycleviewItemAnimalBinding
import com.onc.practicafinal.model.entities.Animal

class AnimalsRecyViewAdapter(private  val items : ArrayList<Animal>):RecyclerView.Adapter<AnimalsRecyViewAdapter.AnimalViewHolder>() {

    var onItemClick : ((Animal) -> Unit)? = null

    class AnimalViewHolder(val binding: RecycleviewItemAnimalBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(animal : Animal, onItemClick: ((Animal) -> Unit)?){


            binding.tvId.text = animal.id.toString()
            binding.tvNombre.text = animal.nombre
            binding.tvDescripcion.text = animal.descripcion

            animal?.srcImage?.let{
                Glide.with(binding.root).load(animal.srcImage)
                    .placeholder(R.drawable.dog_48px)
                    .into(binding.imgFoto)
            }

            binding.idCard.setOnClickListener {
                onItemClick?.invoke(animal)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
        val binding = RecycleviewItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
        holder.bind(items[position], onItemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newItems: ArrayList<Animal>)
    {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }


}