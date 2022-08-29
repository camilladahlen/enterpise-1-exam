package com.example.exam.unittests

import com.example.exam.dataclass.AnimalRegisterDataInfo
import com.example.exam.dataclass.AnimalUpdateDataInfo
import com.example.exam.entity.AnimalEntity
import com.example.exam.repo.AnimalRepo
import com.example.exam.service.AnimalService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class AnimalServiceUnitTest {

    private val animalRepo = mockk<AnimalRepo>()
    private val animalService = AnimalService(animalRepo)

    @Test
    fun testGetAllAnimals(){
        val animalOne = AnimalEntity(name = "heldiggrisen babe", type="pig", breed = "luckybreed", health = "greatness")
        val animalTwo = AnimalEntity(name = "uheldiggrisen babe", type="pig", breed = "unluckybreed", health = "sadness")
        val animalThree = AnimalEntity(name = "ikke-eksisterende heldiggrisen babe", type="pig", breed = "unluckybreed", health = "sadness")

        every { animalRepo.findAll()  } answers {
                mutableListOf(animalOne, animalTwo)
        }

        val animals = animalService.getAllAnimals()

        assert(animals.size == 2)

        assert(animals.contains(animalOne))
        assert(animals.contains(animalTwo))

        assert(!animals.contains(animalThree))
    }

    @Test
    fun testGetAnimalById(){
        val animalOne = AnimalEntity(name = "heldiggrisen babe", type="pig", breed = "luckybreed", health = "greatness")
        val animalTwo = AnimalEntity(name = "uheldiggrisen babe", type="pig", breed = "unluckybreed", health = "sadness")

        every { animalRepo.getById(1) } answers {
            animalOne
        }
        every { animalRepo.getById(2) } answers {
            animalTwo
        }
        val animal = animalService.getAnimalById(1)
        val diffIdAnimal = animalService.getAnimalById(2)
        assert(animal == animalOne)
        assert(diffIdAnimal == animalTwo)

        assert(animal != diffIdAnimal)
    }

    @Test
    fun testRegisterNewAnimal(){
        every { animalRepo.save(any()) } answers {
            firstArg()
        }

        val animal = animalService.registerAnimal(AnimalRegisterDataInfo(name = "unnamed bastard", breed = "unknown", age = 1000))

        assert(animal.name == "unnamed bastard")
        assert(animal.health == null)
    }

    @Test
    fun testShouldUpdateAnimal(){
        val animalOne = AnimalEntity(name = "heldiggrisen babe", type="pig", breed = "luckybreed", health = "greatness")
        animalOne.id = 1
        every { animalRepo.getById(1) } answers {
            animalOne
        }

        every { animalRepo.save(any()) } answers {
            firstArg()
        }

        val upAn = animalService.updateAnimalWithouthName(AnimalUpdateDataInfo(1, type = "human"))
        assert(upAn.equals(animalOne))
        assert(upAn.id == animalOne.id)
        assert(animalOne.type == "human")
    }


}