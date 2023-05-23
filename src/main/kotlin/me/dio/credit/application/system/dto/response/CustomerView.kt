package me.dio.credit.application.system.dto.response

import java.math.BigDecimal
import me.dio.credit.application.system.entity.Customer

class CustomerView (
    val firstName: String,
    val lastName: String,
    val cpf: String,
    val income: BigDecimal,
    val email: String,
    val zipCode: String,
    val street: String
) {
    constructor(customer: Customer): this (
        firstName = customer.firstName,
        lastName = customer.lastName,
        cpf = customer.cpf,
        income = customer.income,
        email = customer.email,
        zipCode = customer.address.zipCode,
        street = customer.address.street
    )

    override fun toString(): String {
        return "firstName='$firstName', lastName='$lastName', cpf='$cpf', income=$income, email='$email', zipCode='$zipCode', street='$street'"
    }


}
