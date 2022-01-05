package com.example.dev_cal_kotlin_jpa.service

import com.example.dev_cal_kotlin_jpa.domain.Event
import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.persistence.EventRepository
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import org.modelmapper.ModelMapper
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class EventService(
        val repo: EventRepository,
        val modelMapper: ModelMapper,
) {

    fun findListContainsTitle(title: String): MutableList<EventDto> {
        return repo.findEventsByTitleContains(title).map {
            modelMapper.map(it, EventDto::class.java)
        }.toMutableList()
    }

    fun findAll(): MutableList<EventDto> {
        return repo.findAll()
            .map {
                modelMapper.map(it, EventDto::class.java)
            }.toMutableList()
    }

    fun create(eventDto: EventDto): ResponseEntity<Any> {
        val entity = repo.save(modelMapper.map(eventDto, Event::class.java))
        val response = ResponseDto<EventDto>().apply {
            this.data = modelMapper.map(entity, EventDto::class.java)
            this.status = "200 OK"
        }
        return ResponseEntity.ok().body(response)
    }

}