package com.example.exam.dataclass

data class AnimalUpdateDataInfo(
    val id : Long,
    val name : String? = null,
    val type : String? = null,
    val breed : String? = null,
    val age : Long? = null,
    val health: String? = null)