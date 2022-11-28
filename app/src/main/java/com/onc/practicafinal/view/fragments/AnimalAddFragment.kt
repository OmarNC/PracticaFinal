package com.onc.practicafinal.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide

import com.onc.practicafinal.R
import com.onc.practicafinal.databinding.FragmentAnimalAddBinding
import com.onc.practicafinal.model.entities.Animal
import com.onc.practicafinal.model.entities.Sexo
import com.onc.practicafinal.model.entities.TipoHabitat
import com.onc.practicafinal.model.repository.AnimalsRepository
import com.onc.practicafinal.viewmodel.AnimalListViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

private const val ARG_ANIMAL = "ARG_ANIMAL"

class AnimalAddFragment : Fragment() {

    private lateinit var binding : FragmentAnimalAddBinding
    private val animalViewModel : AnimalListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimalAddBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Spinner Sexo
        val datosSexo = arrayListOf(Sexo.MACHO.toString(), Sexo.HEMBRA.toString(), Sexo.SIN_DEFINIR.toString())
        val adaptadorSexo = ArrayAdapter(this.requireContext(), android.R.layout.simple_dropdown_item_1line, datosSexo)
        binding.sprSexo.adapter = adaptadorSexo


        //Spinner Habitat
        val datosHabitat = arrayListOf(TipoHabitat.TERRESTRE.toString(), TipoHabitat.AEROTERRESTRE.toString(), TipoHabitat.ACUATICO.toString(), TipoHabitat.DESCONOCIDO.toString())
        val adaptadorHabitat = ArrayAdapter(this.requireContext(), android.R.layout.simple_dropdown_item_1line, datosHabitat)
        binding.sprHabitat.adapter = adaptadorHabitat

        bind()


    }

    fun bind()
    {
        binding.etSrc.setOnFocusChangeListener { view, tieneFoco ->
            if(!tieneFoco){
                val srcImg : String? = binding.etSrc.text.toString()
                srcImg?.let {
                    Glide.with(this).load(it)
                        .placeholder(R.drawable.dog_48px)
                        .into(binding.imgFoto)
                }
            }
        }

        binding.imFecha.setOnClickListener {

            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)

            val dialogFecha = DatePickerDialog(this.requireContext(), {
                view, yy, mm, dd ->
                binding.tvFechaIng.text = "${dd}/${mm}/${yy}"
            }, y,m,d)

            dialogFecha.show()
        }


        binding.btnAceptar.setOnClickListener {
            val srcImage :String = binding.etSrc.text.toString()
            val name :String = binding.etNombre.text.toString()
            val descripcion :String = binding.etDescripcion.text.toString()
            val sexo :String = binding.sprSexo.selectedItem.toString()
            val peso :String = binding.etPeso.text.toString()
            val tipoHabitat :String = binding.sprHabitat.selectedItem.toString()
            val fecha :String = binding.tvFechaIng.text.toString()


            val animal : Animal = Animal(0, name, descripcion, Sexo.valueOf(sexo),
                peso.toDouble(), TipoHabitat.valueOf(tipoHabitat), srcImage, fecha)

            animalViewModel.insertItem(animal)

            removeFragment()

            Log.v("ProyectoFinal:"," Animal: ${animal}")

        }

        binding.btnCancelar.setOnClickListener {
            removeFragment()
        }

    }

    fun removeFragment() {
        parentFragmentManager.beginTransaction().remove(this).commit()
        parentFragmentManager.popBackStack()
    }

}