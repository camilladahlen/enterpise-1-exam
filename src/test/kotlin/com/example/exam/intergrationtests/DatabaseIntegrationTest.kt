package com.example.exam.intergrationtests

import com.example.exam.dataclass.AnimalRegisterDataInfo
import com.example.exam.dataclass.AuthorityToUserInfo
import com.example.exam.entity.AuthorityEntity
import com.example.exam.service.AnimalService
import com.example.exam.service.AuthorityService
import com.example.exam.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Import(UserService::class, AuthorityService::class, AnimalService::class)
class DatabaseIntegrationTest(@Autowired private val userService: UserService, @Autowired private val authorityService: AuthorityService, @Autowired private val animalService: AnimalService) {

    @Test
    fun testShouldGetUsers(){
        val result = userService.getAllUsers()
        assert(result.size == 2)
    }

    @Test
    fun testShouldAddAuthorityToDatabase(){
        val createdAuthority = authorityService.saveAuthority(AuthorityEntity(authorityName = "USER_READ"))
        authorityService.getAuthorityByName(name = "USER_READ")
        val allAuths = authorityService.getAuthorities()
        assert(allAuths.size == 3)
        val info = AuthorityToUserInfo("user@user.com", authorityName = createdAuthority.authorityName)
        val userWithAuth = userService.addAuthorityToUser(info)
        assert(userWithAuth.authorities.contains(authorityService.getAuthorityByName("USER_READ")))
    }

    @Test
    fun testAddAnimalToDatabaseThenGetById(){
        val info = AnimalRegisterDataInfo(name = "test", type = "testing", age = 45)
        val animal = animalService.registerAnimal(info)
        animal.id?.let { animalService.getAnimalById(it).equals(animal) }?.let { assert(it) }
    }



}