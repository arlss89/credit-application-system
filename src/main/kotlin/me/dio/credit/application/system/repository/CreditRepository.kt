package me.dio.credit.application.system.repository

import java.util.*
import me.dio.credit.application.system.entity.Credit
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository //opcional
interface CreditRepository: JpaRepository<Credit, Long> {

    fun findByCreditCode(creditCode: UUID): Credit?

    @Query(value = "SELECT * FROM CREDIT WHERE CUSTOMER_ID = ?1", nativeQuery = true)
    fun findAllByCustomerId(customerId: Long): List<Credit>

}