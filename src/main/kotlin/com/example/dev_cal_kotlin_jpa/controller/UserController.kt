package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(val service: UserService) {

    @PostMapping(path = [""])
    fun create(@Valid @RequestBody userDto: UserDto): UserDto {
        return service.create(userDto)
    }

    @GetMapping(path = ["/email/{email}"])
    fun findOne(@PathVariable email: String) : UserDto {
        return service.findOne(email)
    }

    @GetMapping
    fun findAll() : MutableList<UserDto> {
        return service.findAll()
    }

    @PutMapping(path = ["/update"])
    fun update(@Valid @RequestBody userDto: UserDto) : ResponseEntity<Any> {
        return service.update(userDto)
    }

    @DeleteMapping(path = ["/delete/{email}"])
    fun delete(@Valid @PathVariable email: String) : ResponseEntity<Any> {
        return service.delete(email)
    }

}