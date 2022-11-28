package com.onc.practicafinal.model.entities

import java.io.Serializable
import java.time.LocalDate

data class Animal (
    val id: Int = 0,
    val nombre : String,
    val descripcion : String,
    val sexo : Sexo,
    val peso  : Double,
    val tipoHabitat : TipoHabitat,
    val srcImage : String,
    val fechaIngreso : String
    ): Serializable
