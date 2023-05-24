package me.dio.credit.application.system.service.impl

import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.repository.CustomerRepository
import me.dio.credit.application.system.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val customerRepository: CustomerRepository
    ): ICustomerService {

    override fun save(customer: Customer) =
        this.customerRepository.save(customer)

    override fun findByID(id: Long) =
        this.customerRepository.findById(id).orElseThrow {
            throw BusinessException("Id $id not found")
        }

    override fun delete(id: Long) {
        val customer = this.findByID(id)

        this.customerRepository.delete(customer)
    }

    override fun existsById(id: Long): Boolean {
        return this.customerRepository.existsById(id)
    }
}