package com.example.dev_cal_kotlin_jpa.persistence

import com.example.dev_cal_kotlin_jpa.domain.Event
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

// event id로 찾는 건 스킵
@Repository
interface EventRepository : JpaRepository<Event, Long>{

    fun findEventsByTitleContains(title : String) : MutableList<Event>

}