package com.example.exam.controllers

import com.example.exam.dataclass.AuthorityToUserInfo
import com.example.exam.dataclass.NewUserInfo
import com.example.exam.entity.AuthorityEntity
import com.example.exam.entity.UserEntity
import com.example.exam.service.AuthorityService
import com.example.exam.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.security.Principal


@RestController
@RequestMapping("/api/user")
class UserController(@Autowired private val userService: UserService, @Autowired private val authorityService: AuthorityService) {

    //PERMIT ALL
    @PostMapping("/register")
    fun registerNewUser(@RequestBody newUserInfo: NewUserInfo) : ResponseEntity<UserEntity>{
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/register").toUriString()
        )
        return ResponseEntity.created(uri).body(userService.registerUser(newUserInfo))
    }

    //USER AND ADMIN
    @GetMapping( "/username")
    fun currentUserName(principal: Principal) : ResponseEntity<String>{
        return ResponseEntity.ok().body("Your username: " + principal.name)
    }

    @GetMapping("/{username}")
    fun getCurrentUser(@PathVariable("username") username : String, principal: Principal) : ResponseEntity<UserEntity>{
        if(username == principal.name){
            return ResponseEntity.ok().body(userService.getUserByUsername(username))
        } else {
            throw Exception("ikke i dag")
        }
    }

    //ADMIN
    @GetMapping("/all")
    fun getAllUsers() : ResponseEntity<List<UserEntity>>{
        return ResponseEntity.ok().body(userService.getAllUsers())
    }

    @PostMapping("/authority/register")
    fun registerAuthority(@RequestBody authorityEntity: AuthorityEntity) : ResponseEntity<AuthorityEntity>{
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/authority/register").toUriString()
        )
        return ResponseEntity.created(uri).body(authorityService.saveAuthority(authorityEntity))
    }

    @PostMapping("/authority/add")
    fun addRoleToUser(@RequestBody authorityToUserInfo: AuthorityToUserInfo) : ResponseEntity<Unit>{
        userService.addAuthorityToUser(authorityToUserInfo)
        return ResponseEntity.ok().build()
    }
}