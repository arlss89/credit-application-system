package me.dio.credit.application.system.dto

import java.math.BigDecimal
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Customer

data class CustomerDto (
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val income: BigDecimal,
    val email: String,
    val password: String,
    val zipCode: String,
    val street: String
) {
    fun toEntity() = Customer (
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        income = this.income,
        email = this.email,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}
