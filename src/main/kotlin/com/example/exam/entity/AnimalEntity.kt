package com.example.exam.entity

import javax.persistence.*

@Entity
@Table(name = "animals")
class AnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animals_animal_id_seq")
    @SequenceGenerator(
        name = "animals_animal_id_seq",
        sequenceName = "animals_animal_id_seq",
        allocationSize = 1
    )
    @Column(name = "animal_id")
    var id:Long? = null,

    @Column(name = "animal_name")
    var name:String? = null,

    @Column(name = "animal_type")
    var type:String? = null,

    @Column(name = "animal_breed")
    var breed:String? = null,

    @Column(name = "animal_age")
    var age:Long? = null,

    @Column(name = "animal_health")
    var health:String? = null

){
    override fun toString(): String {
        return "AnimalEntity(id=$id, name=$name, type=$type, breed=$breed, age=$age, health=$health)"
    }
}