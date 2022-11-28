package com.onc.practicafinal.model.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.onc.practicafinal.model.entities.Animal
import com.onc.practicafinal.model.repository.db.AnimalsSqlHelper

class AnimalsRepository {
    companion object {
        lateinit var animalDB: AnimalsSqlHelper

        fun newInstance(context: Context): AnimalsRepository {
            animalDB = AnimalsSqlHelper(context)
            return AnimalsRepository()
        }
    }

    fun insert(animal: Animal): Long {
        return animalDB.insert(animal)
    }

    fun getAllAnimals(): ArrayList<Animal> {
        return animalDB.getAllAnimals()
    }

    fun updateAnimal(animal: Animal): Int {
        return animalDB.updateAnimal(animal)
    }

    fun deleteAnimal(animalId: Int): Int {
        return animalDB.deleteAnimal(animalId)
    }


     fun deleteDB() {
         animalDB.deleteDB()
    }

}