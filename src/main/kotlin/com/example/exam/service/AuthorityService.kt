package com.example.exam.service

import com.example.exam.entity.AuthorityEntity
import com.example.exam.entity.UserEntity
import com.example.exam.repo.AuthorityRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthorityService(@Autowired private val authorityRepo: AuthorityRepo) {

    fun getAuthorities() : List<AuthorityEntity> {
        return authorityRepo.findAll();
    }

    fun getAuthorityByName(name : String) : AuthorityEntity {
        return authorityRepo.getByAuthorityName(name);
    }

    fun saveAuthority(authorityEntity: AuthorityEntity) : AuthorityEntity{
        return authorityRepo.save(authorityEntity)
    }
}