package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.transaction.Transactional

// 고민 : findOne 이랑 겹치는 코드가 많음 - update, delete 모두 먼저 email validation 하고 넘어감
@Service
class UserService(
        val repo: UserRepository,
        val modelMapper: ModelMapper
) {


    fun create(userDto: UserDto): ResponseEntity<Any> {
        return try {
            val result = repo.save(modelMapper.map(userDto, User::class.java))
            val response = ResponseDto<UserDto>().apply {
                this.data = modelMapper.map(result, UserDto::class.java)
                this.status = "200 OK"
            }
            ResponseEntity.ok().body(response)
        } catch ( e : Exception ) {
            println(e)
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    fun findOne(email: String): ResponseEntity<Any> {
        val result = repo.findByEmail(email)
        return (result?.let {
            val response = ResponseDto<UserDto>().apply {
                this.data = modelMapper.map(result, UserDto::class.java)
                this.status = "200 OK"
            }
            ResponseEntity.ok().body(response)
        } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build())
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
        } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

    fun delete(email: String): ResponseEntity<Any> {
        val user = repo.findByEmail(email)
        return user?.let {
            repo.deleteById(it.id)
            ResponseEntity.status(HttpStatus.OK).build()
        } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

}