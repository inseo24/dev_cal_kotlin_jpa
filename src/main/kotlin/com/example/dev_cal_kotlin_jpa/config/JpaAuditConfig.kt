package com.example.dev_cal_kotlin_jpa.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing


// JPA meta model error
// JpaAuditingConfig 파일 생성해서 @EnableJpaAuditing 를 옮김(원래 ~~Application.java 위에 붙였었음)
// @EntityListeners 는 BaseEntity 에 있음(변경 사항 없음)
@Configuration
@EnableJpaAuditing
class JpaAuditConfig {
}