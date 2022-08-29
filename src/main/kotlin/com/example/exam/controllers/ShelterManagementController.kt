package com.example.exam.controllers

import com.example.exam.dataclass.AnimalRegisterDataInfo
import com.example.exam.dataclass.AnimalUpdateDataInfo
import com.example.exam.entity.AnimalEntity
import com.example.exam.service.AnimalService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/shelter")
class ShelterManagementController(@Autowired private val animalService : AnimalService) {

    @GetMapping("/all")
    fun getAllAnimals() : ResponseEntity<List<AnimalEntity>> {
        return ResponseEntity.ok().body(animalService.getAllAnimals())
    }

    @GetMapping("/{animal_id}")
    fun getAnimalById(@PathVariable animal_id : Long?) : ResponseEntity<AnimalEntity>{
        animal_id?.let {
            return ResponseEntity.ok().body(animalService.getAnimalById(it))
        }
        throw Exception("No id found in the URL")
    }

    @PostMapping("/register")
    fun registerNewAnimal(@RequestBody animalDataInfo: AnimalRegisterDataInfo) : ResponseEntity<AnimalEntity>{
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/shelter/register").toUriString()
        )
        return ResponseEntity.created(uri).body(animalService.registerAnimal(animalDataInfo))
    }

    @PutMapping("/update")
    fun updateAnimal(@RequestBody animalUpdateDataInfo: AnimalUpdateDataInfo) : ResponseEntity<AnimalEntity>{
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/shelter/update").toUriString()
        )
        return ResponseEntity.created(uri).body(animalService.updateAnimal(animalUpdateDataInfo))
    }

    @DeleteMapping("/delete/{animal_id}")
    fun deleteAnimal(@PathVariable("animal_id") id: Long) : ResponseEntity<String>{
        animalService.deleteAnimal(id)
        return ResponseEntity.ok().body("Deleted")
    }
}