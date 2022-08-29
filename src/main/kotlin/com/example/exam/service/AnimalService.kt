package com.example.exam.service

import com.example.exam.dataclass.AnimalRegisterDataInfo
import com.example.exam.dataclass.AnimalUpdateDataInfo
import com.example.exam.entity.AnimalEntity
import com.example.exam.repo.AnimalRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimalService(@Autowired private val animalRepo: AnimalRepo) {

    fun getAllAnimals() : List<AnimalEntity> {
        return animalRepo.findAll()
    }

    fun getAnimalById(id : Long) : AnimalEntity{
        return animalRepo.getById(id)
    }

    fun registerAnimal(animalDataInfo: AnimalRegisterDataInfo) : AnimalEntity{
        return animalRepo.save(AnimalEntity(
            name = animalDataInfo.name,
            type = animalDataInfo.type,
            breed = animalDataInfo.breed,
            age = animalDataInfo.age,
            health = animalDataInfo.health))
    }

    fun updateAnimalWithouthName(animalDataInfo: AnimalUpdateDataInfo) : AnimalEntity{
        animalDataInfo.id?.let {
            val updatedUser = animalRepo.getById(it)
            updatedUser.id = updatedUser.id
            updatedUser.name = updatedUser.name
            updatedUser.age = animalDataInfo.age
            updatedUser.breed = animalDataInfo.breed
            updatedUser.health = animalDataInfo.health
            updatedUser.type = animalDataInfo.type
            return animalRepo.save(updatedUser)
        }
        throw IllegalArgumentException("needs to have an id")
    }

    fun updateAnimal(animalDataInfo: AnimalUpdateDataInfo) : AnimalEntity{
        animalDataInfo.id?.let {
            val updatedUser = animalRepo.getById(it)
            updatedUser.id = updatedUser.id
            updatedUser.name = animalDataInfo.name
            updatedUser.age = animalDataInfo.age
            updatedUser.breed = animalDataInfo.breed
            updatedUser.health = animalDataInfo.health
            updatedUser.type = animalDataInfo.type
            return animalRepo.save(updatedUser)
        }
        throw IllegalArgumentException("needs to have an id")
    }

    fun deleteAnimal(id : Long){
        return animalRepo.deleteById(id)
    }
}