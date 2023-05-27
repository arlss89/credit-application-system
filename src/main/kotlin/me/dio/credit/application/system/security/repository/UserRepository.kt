package me.dio.credit.application.system.security.repository

import me.dio.credit.application.system.security.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface UserRepository : JpaRepository<User?, Int?> {

    @Query("SELECT e FROM User e JOIN FETCH e.roles WHERE e.username= (:username)")
    fun findByUsername(@Param("username") username: String?): User?
}