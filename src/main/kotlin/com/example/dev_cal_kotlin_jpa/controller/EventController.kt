package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.service.EventService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/event")
class EventController(
        val eventService: EventService,
) {

    @GetMapping
    fun findAll(): MutableList<EventDto> {
        return eventService.findAll()
    }

    @PostMapping
    fun create(@Valid @RequestBody eventDto: EventDto): ResponseEntity<Any> {
        return eventService.create(eventDto)
    }

    @GetMapping("/{title}")
    fun findEventListContainsTitle(@PathVariable title: String): MutableList<EventDto> {
        return eventService.findListContainsTitle(title)
    }


}