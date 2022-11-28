package com.onc.practicafinal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.onc.practicafinal.model.entities.Animal
import com.onc.practicafinal.model.repository.AnimalsRepository

class AnimalListViewModel(app : Application) :AndroidViewModel(app) {

        private val animalRepository = AnimalsRepository.newInstance(app.applicationContext)
        val animalList = MutableLiveData<ArrayList<Animal>>()

        fun getAnimalList() {
            val animals = animalRepository.getAllAnimals()
            animalList.postValue(animals)
        }

        fun insertItem (animal : Animal)
        {
          animalRepository.insert(animal)
          getAnimalList()
        }

        fun updateItem(animal: Animal): Int {
            return animalRepository.updateAnimal(animal)
        }

        fun deleteItem(animalId: Int): Int {
            return animalRepository.deleteAnimal(animalId)
        }

    fun deleteDb() {
        animalRepository.deleteDB()
    }

}