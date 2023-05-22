package me.dio.credit.application.system.service

import java.util.UUID
import me.dio.credit.application.system.entity.Credit

interface ICreditService {
    fun save(credit: Credit): Credit

    fun findAllByCustomer(customerId: Long): List<Credit>

    fun findByCreditCode(customerId: Long, creditCode: UUID): Credit?
}