package com.onc.practicafinal.view.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.onc.practicafinal.R
import com.onc.practicafinal.databinding.ActivityMainBinding
import com.onc.practicafinal.model.entities.Animal
import com.onc.practicafinal.model.entities.Sexo
import com.onc.practicafinal.model.entities.TipoHabitat
import com.onc.practicafinal.view.adapters.AnimalsRecyViewAdapter
import com.onc.practicafinal.view.fragments.AnimalListFragment
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(binding.containerLayout.id, AnimalListFragment(), "AnimalFragment")
            .commit()
  }
}