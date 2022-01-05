package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.service.ScrapService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/scrap")
class ScrapController(
        val service: ScrapService,
) {


    @PostMapping(path = ["/{email}/{eventId}"])
    fun scrap(@PathVariable email: String, @PathVariable eventId: Long): ResponseEntity<Any> {
        return service.scrap(email, eventId)
    }

    @DeleteMapping(path = ["/{email}/{eventId}"])
    fun unscrap(@PathVariable email: String, @PathVariable eventId: Long): ResponseEntity<Any> {
        return service.deleteScrap(email, eventId)
    }

    @GetMapping
    fun findAll(): MutableList<ScrapDto> {
        return service.findAll()
    }

    @GetMapping(path = ["/{email}"])
    fun findUserScraps(@PathVariable email: String): ResponseEntity<Any> {
        return service.findUsersScrap(email)
    }

}