package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.EventDto
import com.example.dev_cal_kotlin_jpa.service.EventService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/event")
class EventController (
        val service: EventService
){

        @GetMapping
        fun findAll() : MutableList<EventDto> {
                return service.findAll()
        }

        @PostMapping
        fun create(@Valid @RequestBody eventDto: EventDto):ResponseEntity<Any> {
                return service.create(eventDto)
        }

        @GetMapping(path = ["/{title}"])
        fun findEventListContainsTitle(@PathVariable title : String): MutableList<EventDto> {
                return service.findListContainsTitle(title)
        }


}