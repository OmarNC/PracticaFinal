package com.onc.practicafinal.view.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.onc.practicafinal.R
import com.onc.practicafinal.databinding.FragmentAnimalEditBinding
import com.onc.practicafinal.model.entities.Animal
import com.onc.practicafinal.model.entities.Sexo
import com.onc.practicafinal.model.entities.TipoHabitat
import com.onc.practicafinal.viewmodel.AnimalListViewModel
import java.util.*


private const val ARG_ANIMAL = "ARG_ANIMAL"

class AnimalEditFragment : Fragment() {

    private lateinit var binding : FragmentAnimalEditBinding
    private val animalViewModel : AnimalListViewModel by viewModels()

    private var animal: Animal? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animal = it.getSerializable(ARG_ANIMAL)  as Animal
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAnimalEditBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindView()
    }

    fun bindView() {
        binding.etSrc.setText(animal?.srcImage)
        binding.etNombre.setText(animal?.nombre)
        binding.etDescripcion.setText(animal?.descripcion)
        binding.etPeso.setText(animal?.peso.toString())
        binding.tvFechaIng.text = animal?.fechaIngreso


        //Spinner Sexo
        val datosSexo = arrayListOf(Sexo.MACHO.toString(), Sexo.HEMBRA.toString(), Sexo.SIN_DEFINIR.toString())
        val adaptadorSexo = ArrayAdapter(this.requireContext(), android.R.layout.simple_dropdown_item_1line, datosSexo)
        binding.sprSexo.adapter = adaptadorSexo
        binding.sprSexo.setSelection(adaptadorSexo.getPosition(animal?.sexo.toString()))

        //Spinner Habitat
        val datosHabitat = arrayListOf(TipoHabitat.TERRESTRE.toString(), TipoHabitat.AEROTERRESTRE.toString(), TipoHabitat.ACUATICO.toString(), TipoHabitat.DESCONOCIDO.toString())
        val adaptadorHabitat = ArrayAdapter(this.requireContext(), android.R.layout.simple_dropdown_item_1line, datosHabitat)
        binding.sprHabitat.adapter = adaptadorHabitat
        binding.sprHabitat.setSelection(adaptadorHabitat.getPosition(animal?.tipoHabitat.toString()))


        //Cargar la imagen <<REVISAR>>
        animal?.srcImage?.let {
            Glide.with(this).load(it)
                .placeholder(R.drawable.dog_48px)
                .into(binding.imgFoto)
        }


     /*   binding.imgFoto.setOnTouchListener { view, motionEvent ->
            when(motionEvent.action){
                MotionEvent.ACTION_UP -> true
                MotionEvent.ACTION_DOWN -> true
                else -> true
            }
        true
        }*/


        //Establece el evento cuando se cambia la URL
        binding.etSrc.setOnFocusChangeListener { view, tieneFoco ->
            if (!tieneFoco) {
                val srcImg: String? = binding.etSrc.text.toString()
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

            val dialogFecha = DatePickerDialog(this.requireContext(), { view, yy, mm, dd ->
                binding.tvFechaIng.text = "${dd}/${mm}/${yy}"
            }, y, m, d)

            dialogFecha.show()
        }


        binding.btnAceptar.setOnClickListener {
            val id :Int = if (animal != null) animal!!.id else 0
            val srcImage: String = binding.etSrc.text.toString()
            val name: String = binding.etNombre.text.toString()
            val descripcion: String = binding.etDescripcion.text.toString()

            val sexo: String = binding.sprSexo.selectedItem.toString()
            val peso: String = binding.etPeso.text.toString()

            val tipoHabitat: String = binding.sprHabitat.selectedItem.toString()
            val fecha: String = binding.tvFechaIng.text.toString()


            val newAnimal = Animal(
                id, name, descripcion, Sexo.valueOf(sexo),
                peso.toDouble(), TipoHabitat.valueOf(tipoHabitat), srcImage, fecha
            )

            animalViewModel.updateItem(newAnimal)
            removeFragment()

        }

        //Establece el evento del boton cancelar
        binding.btnCancelar.setOnClickListener {
            removeFragment()
        }
    }

    fun removeFragment() {
        parentFragmentManager.beginTransaction().remove(this).commit()
        parentFragmentManager.popBackStack()
    }


    companion object {
    @JvmStatic
    fun newInstance(animal: Animal?) =
        AnimalEditFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_ANIMAL, animal)
            }
        }
    }
}