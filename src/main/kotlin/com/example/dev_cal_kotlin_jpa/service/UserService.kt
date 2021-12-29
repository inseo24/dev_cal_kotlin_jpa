package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class UserService(
        val repo: UserRepository,
        val modelMapper: ModelMapper
) {

    fun create(userDto: UserDto): UserDto {
        val result = repo.save(modelMapper.map(userDto, User::class.java))
        return modelMapper.map(result, userDto::class.java)
    }


}