package com.example.exam.unittests

import com.example.exam.dataclass.AuthorityToUserInfo
import com.example.exam.dataclass.NewUserInfo
import com.example.exam.entity.AuthorityEntity
import com.example.exam.entity.UserEntity
import com.example.exam.repo.AuthorityRepo
import com.example.exam.repo.UserRepo
import com.example.exam.service.UserService
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class UserServiceUnitTest {

    private val userRepo = mockk<UserRepo>()
    private val authorityRepo = mockk<AuthorityRepo>()
    private val userService = UserService(userRepo, authorityRepo)

    @Test
    fun testShouldCreateUserWithEncryptedPassword(){
        val password = "pirate"
        val authority = AuthorityEntity(id = 1,"USER")

        every { userRepo.save(any()) } answers {
            firstArg()
        }

        every { authorityRepo.getByAuthorityName("USER") } answers {
            authority
        }

        val user = userService.registerUser(NewUserInfo("test", password))

        assert(user.password != "pirate")
        assert(user.password != password)

    }

    @Test
    fun testShouldAddAuthorityToUser(){
        val user = UserEntity(id = 1, email = "test", password = "test")
        val authority = AuthorityEntity(id = 1,"ADMIN")

        println(user.toString())

        every { userRepo.findByEmail(user.email) } answers {
            user
        }

        every { authorityRepo.getByAuthorityName("ADMIN") } answers {
            authority
        }

        every { userRepo.save(any()) } answers {
            firstArg()
        }

        userService.addAuthorityToUser(AuthorityToUserInfo(user.email, "ADMIN"))

        assert(user.authorities.contains(authority))
        assert(!user.authorities.contains(AuthorityEntity(id = 2, "USER")))

    }

    @Test
    fun shouldAddAuthorityWhenCreatingUserAndAddingNewAuthority(){
        every { userRepo.save(any()) } answers {
            firstArg()
        }

        every { authorityRepo.getByAuthorityName("USER")  } answers {
            AuthorityEntity(id = 1, "USER")
        }

        every { authorityRepo.getByAuthorityName("ADMIN")  } answers {
            AuthorityEntity(id = 2, "ADMIN")
        }

        val user = userService.registerUser(NewUserInfo("test", "testt"))
        assert(user.enabled == true)
        assert(user.authorities.size == 1)

        every { userRepo.findByEmail(user.email) } answers {
            user
        }

        userService.addAuthorityToUser(AuthorityToUserInfo(user.email, "ADMIN"))

        assert(user.authorities.size == 2)
    }

}