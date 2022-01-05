package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.persistence.EventRepository
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
    val eventRepository: EventRepository,
    val scrapRepository: ScrapRepository,
    val modelMapper: ModelMapper,
) {

    @Transactional
    fun deleteScrap(email: String, eventId: Long): ResponseEntity<Any> {
        val user = userRepo.findByEmail(email)
        val userId = user?.id
        userId?.let {
            eventRepository.findById(eventId).orElseThrow()
            scrapRepository.deleteScrapByEventIdAndUserId(eventId, userId)
            return ResponseEntity.ok().build()
        } ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

    }

    @Transactional
    fun scrap(email: String, eventId: Long): ResponseEntity<Any> {
        val user = userRepo.findByEmail(email)
        val userId = user?.id
        userId?.let {
            eventRepository.findById(eventId).orElseThrow()
            scrapRepository.scrap(eventId, userId)
            return ResponseEntity.ok().build()
        } ?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

    }

    fun findAll(): MutableList<ScrapDto> {
        return scrapRepository.findAll()
            .map {
                modelMapper.map(it, ScrapDto::class.java)
            }.toMutableList()
    }

    fun findUsersScrap(email: String): ResponseEntity<Any> {
        val user = userRepo.findByEmail(email)
        val userId = user?.id
        return userId?.let {
            val result = scrapRepository.findAllByUserId(userId)
                .map {
                    modelMapper.map(it, ScrapDto::class.java)
                }.toMutableList()

            val response = ResponseDto<UserDto>().apply {
                this.data = result
                this.status = "200 OK"
            }
            ResponseEntity.ok().body(response)

        } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }
}