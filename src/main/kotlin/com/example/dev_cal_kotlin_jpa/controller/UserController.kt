package com.example.dev_cal_kotlin_jpa.controller

import com.example.dev_cal_kotlin_jpa.dto.UserDto
import com.example.dev_cal_kotlin_jpa.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

// 기존 프로젝트에는 cru + login 까지 있고 d는 없음
// login 을 일단 제외하고 테스트 코드 작성 연습을 위해 일단 기본 crud 로 만들어 봄
@RestController
@RequestMapping("/user")
class UserController(
        val service: UserService
) {

    @PostMapping(path = [""])
    fun create(@Valid @RequestBody userDto: UserDto): ResponseEntity<Any> {
        return service.create(userDto)
    }

    @GetMapping(path = ["/email/{email}"])
    fun findOne(@PathVariable email: String) : ResponseEntity<Any> {
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