package com.example.dev_cal_kotlin_jpa.domain

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0

        @CreatedDate
        @Column(name = "created_time", updatable = false)
        var createdTime: LocalDateTime? = null

        @LastModifiedDate
        @Column(name = "updated_time")
        var updatedTime: LocalDateTime? = null

}