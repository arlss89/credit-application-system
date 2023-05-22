package me.dio.credit.application.system.controller

import me.dio.credit.application.system.dto.CustomerDto
import me.dio.credit.application.system.dto.CustomerUpdateDto
import me.dio.credit.application.system.dto.CustomerView
import me.dio.credit.application.system.service.impl.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/api/customers")
class CustomerResouce(
    private val customerService: CustomerService
) {

    @PostMapping
    fun saveCustomer(@RequestBody customerDto: CustomerDto): ResponseEntity<String> {
        val savedCustomer = this.customerService.save(customerDto.toEntity())

        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Customer ${savedCustomer.firstName} saved!")
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<CustomerView> {
        val customer = this.customerService.findByID(id)

        return ResponseEntity.status(HttpStatus.OK)
            .body(CustomerView(customer))
    }

    @DeleteMapping("/{id}")
    fun deleteCustomer(@PathVariable id: Long): ResponseEntity<HttpStatus> {
        val success = this.customerService.existsById(id).apply {
            this@CustomerResouce.customerService.delete(id)
        }
        val status = if (success) {
            HttpStatus.OK
        } else {
            HttpStatus.NOT_FOUND
        }

        return ResponseEntity.status(status).body(status)
    }

    @PatchMapping
    fun updateCustomer(@RequestParam(value = "customerId") id: Long,
                       customerUpdateDto: CustomerUpdateDto
    ): ResponseEntity<CustomerView> {
        val customer = this.customerService.findByID(id)
        val customerToUpdate = customerUpdateDto.toEntity(customer)
        val customerUpdated = this.customerService.save(customerToUpdate)

        return ResponseEntity.status(HttpStatus.OK)
            .body(CustomerView(customerUpdated))
    }
}