package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.ScrapRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class ScrapService(
        val userRepo: UserRepository,
        val repo: ScrapRepository,
        val modelMapper: ModelMapper
) {

    @Transactional
    fun unscrap(eventId : Long, email : String): ResponseEntity<Any>{
        val result = userRepo.findByEmail(email)
        return try {
            repo.unscrap(eventId, email)
            val response = ResponseDto<ScrapDto>().apply {
                this.status = "200 OK"
                this.data = modelMapper.map(result, UserDto::class.java)
            }
            ResponseEntity.ok().body(response)
        } catch (e : Exception) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    @Transactional
    fun scrap(eventId: Long, email: String): ResponseEntity<Any>{
        val result = userRepo.findByEmail(email)
        return try {
            repo.scrap(eventId, email)
            val response = ResponseDto<ScrapDto>().apply {
                this.status = "200 OK"
                this.data = modelMapper.map(result, UserDto::class.java)
            }
            ResponseEntity.ok().body(response)
        } catch (e : Exception) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

    fun findAll(): MutableList<ScrapDto> {
        return repo.findAll()
            .map {
                modelMapper.map(it, ScrapDto::class.java)
            }.toMutableList()
    }

    fun findUsersScrap(email : String) : MutableList<ScrapDto> {
        return repo.userScraps(email)
            .map {
                modelMapper.map(it, ScrapDto::class.java)
            }.toMutableList()
    }

}