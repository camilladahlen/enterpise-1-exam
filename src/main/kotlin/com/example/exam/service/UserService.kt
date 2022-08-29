package com.example.exam.service

import com.example.exam.dataclass.AuthorityToUserInfo
import com.example.exam.dataclass.NewUserInfo
import com.example.exam.entity.UserEntity
import com.example.exam.passwordEncoder
import com.example.exam.repo.AuthorityRepo
import com.example.exam.repo.UserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(@Autowired private val userRepo: UserRepo, @Autowired private val authorityRepo: AuthorityRepo) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val user = userRepo.findByEmail(it)
            return User(user.email, user.password, user.authorities.map { SimpleGrantedAuthority(it.authorityName) })
        }
        throw Exception("error")
    }

    fun registerUser(newUserInfo: NewUserInfo) : UserEntity{
        val newUser =
            UserEntity(email = newUserInfo.email, password = passwordEncoder().encode(newUserInfo.password))
        newUser.authorities.add(authorityRepo.getByAuthorityName("USER"))
        return userRepo.save(newUser);
    }

    fun addAuthorityToUser(authorityToUserInfo: AuthorityToUserInfo) : UserEntity {
        val user = userRepo.findByEmail(authorityToUserInfo.email)
        user.authorities.add(authorityRepo.getByAuthorityName(authorityToUserInfo.authorityName))
        return userRepo.save(user)
    }

    fun getUserByUsername(username : String) : UserEntity{
        return userRepo.findByEmail(username)
    }

    fun getById(id : Long) : UserEntity{
        return userRepo.getById(id)
    }

    fun getAllUsers() : List<UserEntity>{
        return userRepo.findAll()
    }


}