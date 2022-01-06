package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.ScrapDto
import com.example.dev_cal_kotlin_jpa.responseDto.ResponseDto
import com.example.dev_cal_kotlin_jpa.service.ScrapService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/scrap")
class ScrapController(
    val scrapService: ScrapService,
) {

    @PostMapping(path = ["/{email}/{eventId}"])
    fun scrap(@PathVariable email: String, @PathVariable eventId: Long): ResponseEntity<HttpStatus> {
        return scrapService.scrap(email, eventId)
    }

    @DeleteMapping(path = ["/{email}/{eventId}"])
    fun unscrap(@PathVariable email: String, @PathVariable eventId: Long): ResponseEntity<HttpStatus> {
        return scrapService.deleteScrap(email, eventId)
    }

    @GetMapping
    fun findAll(): ResponseEntity<ResponseDto<ScrapDto>> {
        return scrapService.findAll()
    }

    @GetMapping("/{email}")
    fun findUserScraps(@PathVariable email: String): ResponseEntity<ResponseDto<ScrapDto>> {
        return scrapService.findUsersScrap(email)
    }

}