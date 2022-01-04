package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.service.ScrapService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/scrap")
class ScrapController (
        val service: ScrapService
){


        @PostMapping(path = ["/{email}/{eventId}"])
        fun scrap(@PathVariable eventId : Long, @PathVariable email : String) :ResponseEntity<Any> {
                return service.scrap(eventId, email)
        }

        @DeleteMapping(path = ["/{email}/{eventId}"])
        fun unscrap(@PathVariable eventId: Long, @PathVariable email: String) : ResponseEntity<Any> {
                return service.unscrap(eventId, email)
        }

        @GetMapping
        fun findAll() : MutableList<ScrapDto> {
                return service.findAll()
        }

}