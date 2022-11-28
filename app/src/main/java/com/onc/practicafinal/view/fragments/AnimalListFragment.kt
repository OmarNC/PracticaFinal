package com.onc.practicafinal.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.onc.practicafinal.R
import com.onc.practicafinal.databinding.ActivityMainBinding
import com.onc.practicafinal.databinding.FragmentAnimalAddBinding
import com.onc.practicafinal.databinding.FragmentAnimalListBinding
import com.onc.practicafinal.model.entities.Animal
import com.onc.practicafinal.model.entities.Sexo
import com.onc.practicafinal.model.entities.TipoHabitat
import com.onc.practicafinal.view.adapters.AnimalsRecyViewAdapter
import com.onc.practicafinal.viewmodel.AnimalListViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class AnimalListFragment : Fragment() {

    private val animalViewModel : AnimalListViewModel by viewModels()
    private lateinit var binding: FragmentAnimalListBinding
    private lateinit var adapter : AnimalsRecyViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("ProyectoFinal:","Se ha iniciado el fragment")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimalListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.v("ProyectoFinal:","onViewCreated")

        //Agregar AnimalRepository para obtener los datos
        adapter = AnimalsRecyViewAdapter(arrayListOf())



        //Evento para hacer clic en un item del RecycleView
        adapter.onItemClick = {animal ->
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.containerLayout, AnimalDetailFragment.newInstance(animal), "AnimalDetailFragment")
                .addToBackStack("AnimalDetailFragment")
                .commit()
            //Inflate el frgamento de detalle
        }

        //Configyración de los elementos del REcycleView
        binding.rvListaAnimales.itemAnimator =  DefaultItemAnimator()
        binding.rvListaAnimales.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvListaAnimales.adapter = adapter

        //Establecer el observador para los cambios en el Repository (Owner)
        animalViewModel.animalList.observe(viewLifecycleOwner, Observer {
            adapter.updateItems(it)
        })

        //Establecer el evento para agregar nuevos animales
        binding.fbtnAgregar.setOnClickListener{ view
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.containerLayout, AnimalAddFragment(), "AnimalAddFragment")
                .addToBackStack("AnimalAddFragment")
                .commit()
        }

        animalViewModel.getAnimalList()

    }


    override fun onStart() {
        super.onStart()
        Log.v("ProyectoFinal:","onStart")

    }


    fun getDataFake() : ArrayList<Animal>
    {
        val src : String = "https://www.hdwallpaperspulse.com/wp-content/uploads/2013/02/image00333.jpg"
        val formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy")
        val  lista = ArrayList<Animal>()
        lista.add(
            Animal(1, "animal 1", "nada", Sexo.SIN_DEFINIR, 20.0,
            TipoHabitat.DESCONOCIDO, src, LocalDate.now().toString())
        )
        lista.add(
            Animal(1, "animal 2", "nada", Sexo.SIN_DEFINIR, 20.0,
            TipoHabitat.DESCONOCIDO, src, LocalDate.now().toString())
        )
        lista.add(
            Animal(1, "animal 3", "nada", Sexo.SIN_DEFINIR, 20.0,
            TipoHabitat.DESCONOCIDO, src, LocalDate.now().toString())
        )

        return lista
    }
}