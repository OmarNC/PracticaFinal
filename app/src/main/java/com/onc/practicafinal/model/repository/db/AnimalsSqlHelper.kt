package com.onc.practicafinal.model.repository.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.onc.practicafinal.model.entities.Animal
import com.onc.practicafinal.model.entities.Sexo
import com.onc.practicafinal.model.entities.TipoHabitat
import java.time.LocalDate
import kotlin.collections.ArrayList


class AnimalsSqlHelper (context : Context): SQLiteOpenHelper(context, DATEBASE_NAME, null, DATEBASE_VERSION) {

    companion object {
        private const val DATEBASE_VERSION = 1
        private const val DATEBASE_NAME = "animals.db"

        //Nombre de la tabla
        private const val TBL_ANIMALS = "tbl_animals"

        //Nombre de los campos de la tabla de animales
        private const val ID = "id"
        private const val NAME = "name"
        private const val DESCRIPTION = "description"
        private const val PESO = "peso"
        private const val TIPO_HABITAT = "tipo_habitat"
        private const val FECHA_INGRESO = "fecha_ingreso"
        private const val SEXO = "sexo"
        private const val SRC_IMAGE = "src_image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val strSqlCreate = "CREATE TABLE $TBL_ANIMALS ("+
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$NAME TEXT, " +
                "$DESCRIPTION TEXT, " +
                "$SEXO TEXT, " +
                "$PESO REAL, " +
                "$TIPO_HABITAT TEXT, " +
                "$SRC_IMAGE TEXT, " +
                "$FECHA_INGRESO TEXT)"
        db?.execSQL(strSqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val strSqlUpdate = "DROP TABLE IF EXISTS $TBL_ANIMALS"
        db?.execSQL(strSqlUpdate)
        onCreate(db)
    }

    fun deleteDB() {
        val strSqlUpdate = "DROP TABLE IF EXISTS $TBL_ANIMALS"
        val db = writableDatabase
        db?.execSQL(strSqlUpdate)
    }


    fun insert(animal: Animal): Long
    {
        val db = writableDatabase
        val contentValue = ContentValues().apply{
            put(NAME, animal.nombre)
            put(DESCRIPTION, animal.descripcion)
            put(SEXO, animal.sexo.toString())
            put(PESO, animal.peso)
            put(TIPO_HABITAT, animal.tipoHabitat.toString())
            put(SRC_IMAGE, animal.srcImage)
            put(FECHA_INGRESO, animal.fechaIngreso)
        }
        val result = db.insert(TBL_ANIMALS, null, contentValue)
        db.close()
        return result
    }


    fun getAllAnimals() : ArrayList<Animal>{
        val animalsList = arrayListOf<Animal>()
        var strQuery = "SELECT * FROM $TBL_ANIMALS"
        val db = readableDatabase

        val cursor : Cursor?

        try {
            cursor = db.rawQuery(strQuery, null)
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return  animalsList
        }

        var id: Int = 0
        var name : String = ""
        var descripcion : String = ""
        var sexo : Sexo
        var peso : Double
        var tipoHabitat : TipoHabitat
        var srcImage : String = ""
        var strFechaIngreso : String


        with(cursor) {
            while(cursor.moveToNext())
            {
                id = getInt(cursor.getColumnIndexOrThrow(ID))
                name = getString(cursor.getColumnIndexOrThrow(NAME))
                descripcion = getString(cursor.getColumnIndexOrThrow(DESCRIPTION))
                //
               // val s = getString(cursor.getColumnIndexOrThrow(SEXO))
                sexo = Sexo.valueOf(getString(cursor.getColumnIndexOrThrow(SEXO)))
                peso = getDouble(cursor.getColumnIndexOrThrow(PESO))
                tipoHabitat = TipoHabitat.valueOf(getString(cursor.getColumnIndexOrThrow(
                    TIPO_HABITAT
                )))

                srcImage = getString(cursor.getColumnIndexOrThrow(SRC_IMAGE))

                strFechaIngreso = getString(cursor.getColumnIndexOrThrow(FECHA_INGRESO))


                //sexo = Sexo.MACHO

                val animal = Animal(id, name,  descripcion, sexo, peso, tipoHabitat, srcImage, strFechaIngreso)
                animalsList.add(animal)
            }
        }
        cursor.close()

        return  animalsList
    }


    fun updateAnimal(animal: Animal) : Int
    {
        var db = writableDatabase
        var conentValue = ContentValues().apply {
            put(NAME, animal.nombre)
            put(DESCRIPTION, animal.descripcion)
            put(PESO, animal.peso)
            put(TIPO_HABITAT, animal.tipoHabitat.toString())
            put(FECHA_INGRESO, animal.fechaIngreso)
            put(SEXO, animal.sexo.toString())
            put(SRC_IMAGE, animal.srcImage)
        }


        val result = db.update(TBL_ANIMALS, conentValue, "id=?" , arrayOf("${animal.id}"))
        db.close()
        return result
    }

    fun deleteAnimal(animalId: Int) : Int {
        val db = writableDatabase
        val result = db.delete(TBL_ANIMALS, "id=?", arrayOf("$animalId"))
        db.close()
        return result
    }

}