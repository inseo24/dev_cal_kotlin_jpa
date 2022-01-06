package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(
    val userRepository: UserRepository,
    val modelMapper: ModelMapper,
) {


    fun create(userDto: UserDto): ResponseEntity<ResponseDto<UserDto>> {
        val result = userRepository.save(modelMapper.map(userDto, User::class.java))
        val response = ResponseDto<UserDto>().apply {
            this.data = modelMapper.map(result, UserDto::class.java)
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun findOne(email: String): ResponseEntity<ResponseDto<UserDto>> {
        val result = userRepository.findByEmail(email) ?: throw RuntimeException()
        val response = ResponseDto<UserDto>().apply {
            this.data = modelMapper.map(result, UserDto::class.java)
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun findAll(): ResponseEntity<ResponseDto<UserDto>> {
        val result = userRepository.findAll().map { modelMapper.map(it, UserDto::class.java) }
        val response = ResponseDto<UserDto>().apply {
            this.data = result
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    @Transactional
    fun update(userDto: UserDto): ResponseEntity<ResponseDto<UserDto>> {
        val user = userRepository.findByEmail(userDto.email) ?: throw RuntimeException()
        user.mobileNumber = userDto.mobileNumber
        user.password = userDto.password
        val response = ResponseDto<UserDto>().apply {
            this.data = modelMapper.map(user, UserDto::class.java)
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun delete(email: String): ResponseEntity<HttpStatus> {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException()
        userRepository.deleteById(user.id)
        return ResponseEntity.ok().build()
    }

}