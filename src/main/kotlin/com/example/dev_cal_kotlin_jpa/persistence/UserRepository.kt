package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.User
import com.example.dev_cal_kotlin_jpa.dto.UserDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>{
    // ?를 없애고, 서비스 쪽 email validation 을 따로 분리하고 싶은데...
    // ?를 쓴 이유는 혹시 나오지 않을 때 에러 처리를 하기 위해서 했음(service) -> 잘 모르겠음
    fun findByEmail(email: String) : User?
}