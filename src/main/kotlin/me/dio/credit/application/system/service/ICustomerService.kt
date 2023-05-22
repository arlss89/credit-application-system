package me.dio.credit.application.system.service

import me.dio.credit.application.system.entity.Customer

//@Service
interface ICustomerService {
    fun save(customer: Customer): Customer

    fun findByID(id: Long): Customer

    fun delete(id: Long): Boolean

    fun existsById(id: Long): Boolean
}