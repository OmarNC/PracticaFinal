package com.onc.practicafinal.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.onc.practicafinal.R
import com.onc.practicafinal.databinding.FragmentAnimalDetailBinding
import com.onc.practicafinal.databinding.FragmentAnimalListBinding
import com.onc.practicafinal.model.entities.Animal
import com.onc.practicafinal.model.entities.Sexo
import com.onc.practicafinal.model.entities.TipoHabitat
import com.onc.practicafinal.viewmodel.AnimalListViewModel

//
class AnimalDetailFragment : Fragment() {

    private lateinit var binding : FragmentAnimalDetailBinding
    private val animalViewModel : AnimalListViewModel by viewModels()

    private var animal: Animal? = null

    companion object {
        @JvmStatic
        fun newInstance(animal: Animal) =
            AnimalDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("ARG_ANIMAL", animal)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animal = it.getSerializable("ARG_ANIMAL") as Animal
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_animal_detail, container, false)
        binding = FragmentAnimalDetailBinding.inflate(layoutInflater, container, false)

        bind()

        return binding.root
        //return  view
    }

    fun bind(){

        //Vicular todos los campos
        binding.tvId.text = animal?.id.toString()
        binding.tvNombre.text =animal?.nombre
        binding.tvDescripcion.text =animal?.descripcion
        binding.tvSexo.text =animal?.sexo.toString()
        binding.tvPeso.text =animal?.peso.toString()
        binding.tvTipoHabitat.text =animal?.tipoHabitat.toString()
        binding.tvFechaIng.text =animal?.fechaIngreso

        //Cargar la imagen
        animal?.srcImage?.let {
            Glide.with(this).load(it)
                .placeholder(R.drawable.dog_48px)
                .into(binding.imgFoto)
        }

        //Establecer los mensajes de los botones
        binding.btnEditar.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.containerLayout, AnimalEditFragment.newInstance(animal), "AnimalEditFragment")
                .addToBackStack("AnimalEditFragment")
                .commit()
        }



        binding.fbtnEliminar.setOnClickListener {
            if (animal != null) {
                animalViewModel.deleteItem( animal!!.id)
                removeFragment()
            }
        }
        binding.btnAtras.setOnClickListener {
                removeFragment()
            }
        }

    fun removeFragment() {
        parentFragmentManager.beginTransaction().remove(this).commit()
        parentFragmentManager.popBackStack()
    }

}