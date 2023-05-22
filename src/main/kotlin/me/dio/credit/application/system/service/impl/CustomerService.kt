package me.dio.credit.application.system.service.impl

import java.lang.RuntimeException
import me.dio.credit.application.system.entity.Customer
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
            throw RuntimeException("Id $id not found")
        }

    override fun delete(id: Long): Boolean {
        return this.customerRepository.existsById(id).also {
            this.customerRepository.deleteById(id)
        }
    }

    override fun existsById(id: Long): Boolean {
        return this.customerRepository.existsById(id)
    }
}