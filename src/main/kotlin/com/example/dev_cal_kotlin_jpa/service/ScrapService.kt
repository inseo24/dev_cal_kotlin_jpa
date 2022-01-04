package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.persistence.EventRepository
import com.example.dev_cal_kotlin_jpa.persistence.ScrapRepository
import com.example.dev_cal_kotlin_jpa.persistence.UserRepository
import org.modelmapper.ModelMapper
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
class ScrapService(
        val userRepo: UserRepository,
        val eventRepository: EventRepository,
        val repo: ScrapRepository,
        val modelMapper: ModelMapper
) {

    @Transactional
    fun unscrap(email : String , eventId : Long ): ResponseEntity<Any>{
        val user = userRepo.findByEmail(email)
        val userId = user?.id
        userId?.let {
            eventRepository.findById(eventId).orElseThrow()

            repo.unscrap(eventId, userId)
            return ResponseEntity.ok().build()
        }?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

    }

    @Transactional
    fun scrap(email: String, eventId: Long): ResponseEntity<Any>{
        val user = userRepo.findByEmail(email)
        val userId = user?.id
        userId?.let {
            eventRepository.findById(eventId).orElseThrow()
            repo.scrap(eventId, userId)
            return ResponseEntity.ok().build()
        }?: return ResponseEntity.status(HttpStatus.BAD_REQUEST).build()

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