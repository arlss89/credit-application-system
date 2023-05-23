package me.dio.credit.application.system.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Customer
import org.hibernate.validator.constraints.br.CPF

data class CustomerDto (
    @field:NotEmpty(message = "Invalid firstName") val firstName: String,
    @field:NotEmpty(message = "Invalid lastName") val lastName: String,
    @field:CPF(message = "Invalid cpf value") val cpf: String,
    @field:NotNull(message = "Invalide value for income")val income: BigDecimal,
    @field:Email(message = "Invalid email value") val email: String,
    @field:NotEmpty(message = "Invalid password") val password: String,
    @field:NotEmpty(message = "Invalid zipCode") val zipCode: String,
    @field:NotEmpty(message = "Invalid street name") val street: String
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
