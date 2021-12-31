package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ErrorResponse
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.MethodArgumentNotValidException
import javax.transaction.Transactional

@Service
class UserService(
        val repo: UserRepository,
        val modelMapper: ModelMapper
) {

    fun create(userDto: UserDto): UserDto {
        val result = repo.save(modelMapper.map(userDto, User::class.java))
        return modelMapper.map(result, userDto::class.java)
    }

    fun findOne(email: String): UserDto {
        val result = repo.findByEmail(email)
        return modelMapper.map(result, UserDto::class.java)
    }

    fun findAll(): MutableList<UserDto> {
        return repo.findAll()
                .map{
                    modelMapper.map(it, UserDto::class.java)
                }.toMutableList()
    }

    @Transactional
    fun update(userDto: UserDto): ResponseEntity<Any> {
        val user = repo.findByEmail(userDto.email)
        return user?.let {
            it.mobileNumber = userDto.mobileNumber
            it.password = userDto.password
            ResponseEntity.status(HttpStatus.OK).build()
        }
         ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    fun delete(email: String): ResponseEntity<Any> {
        val user = repo.findByEmail(email)
        return user?.let {
            repo.deleteById(it.id)
            ResponseEntity.status(HttpStatus.OK).build()
        } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

}