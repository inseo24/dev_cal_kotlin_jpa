package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Scrap
import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
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
    val userRepository: UserRepository,
    val eventRepository: EventRepository,
    val scrapRepository: ScrapRepository,
    val modelMapper: ModelMapper,
) {

    @Transactional
    fun deleteScrap(email: String, eventId: Long): ResponseEntity<HttpStatus> {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException()
        eventRepository.findById(eventId).orElseThrow()
        scrapRepository.deleteScrapByEventIdAndUserId(eventId, user.id)
        return ResponseEntity.ok().build()
    }

    @Transactional
    fun scrap(email: String, eventId: Long): ResponseEntity<HttpStatus> {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException()
        val event = eventRepository.findById(eventId).orElseThrow()
        val entity = Scrap(event, user)
        scrapRepository.save(entity)
        return ResponseEntity.ok().build()
    }

    fun findAll(): ResponseEntity<ResponseDto<ScrapDto>> {
        val result = scrapRepository.findAll().map { modelMapper.map(it, ScrapDto::class.java) }
        val response = ResponseDto<ScrapDto>().apply {
            data = result
            status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

    fun findUsersScrap(email: String): ResponseEntity<ResponseDto<ScrapDto>> {
        val user = userRepository.findByEmail(email) ?: throw RuntimeException()
        val result = scrapRepository.findAllByUserId(user.id).map { modelMapper.map(it, ScrapDto::class.java) }
        val response = ResponseDto<ScrapDto>().apply {
            this.data = result
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }
}