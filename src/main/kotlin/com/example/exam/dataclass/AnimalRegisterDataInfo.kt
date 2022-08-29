package com.example.exam.dataclass

data class AnimalRegisterDataInfo(
    val name : String? = null,
    val type : String? = null,
    val breed : String? = null,
    val age : Long? = null,
    val health: String? = null)