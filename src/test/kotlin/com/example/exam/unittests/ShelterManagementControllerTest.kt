package com.example.exam.unittests

import com.example.exam.controllers.ShelterManagementController
import com.example.exam.entity.AnimalEntity
import com.example.exam.service.AnimalService
import com.example.exam.service.UserService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@WebMvcTest(ShelterManagementController::class)
@AutoConfigureMockMvc(addFilters = false)
class ShelterManagementControllerTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun animalService() = mockk<AnimalService>()
        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired
    private lateinit var animalService: AnimalService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun testShouldGetAllAnimalsTestWithOneAnimalReturningJSON(){
        val animalOne = AnimalEntity(name = "heldiggrisen babe", type="pig", breed = "luckybreed", health = "greatness")

        every { animalService.getAllAnimals() } answers {
            mutableListOf(animalOne)
        }
        mockMvc.get("/api/shelter/all"){

        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("[{\"id\":null,\"name\":\"heldiggrisen babe\",\"type\":\"pig\",\"breed\":\"luckybreed\",\"age\":null,\"health\":\"greatness\"}]") }
        }
    }

    @Test
    fun testShouldReturnAnimalByIdInBody(){
        val animalOne = AnimalEntity(id = 1, name = "heldiggrisen babe", type="pig", breed = "luckybreed", health = "greatness")

        every { animalService.getAnimalById(1) } answers {
            animalOne
        }

        mockMvc.get("/api/shelter/1") {

        }.andExpect {
            status { isOk() }
            content { contentType(MediaType.APPLICATION_JSON) }
            content { json("{\"id\":1,\"name\":\"heldiggrisen babe\",\"type\":\"pig\",\"breed\":\"luckybreed\",\"age\":null,\"health\":\"greatness\"}}") }
        }
    }

}