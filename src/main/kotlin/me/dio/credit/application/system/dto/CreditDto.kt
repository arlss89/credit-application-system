package me.dio.credit.application.system.dto

import java.math.BigDecimal
import java.time.LocalDate
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer

data class CreditDto (
    val creditValue: BigDecimal,
    val dayFirstOfInstallment: LocalDate,
    val numberOfInstallments: Int,
    val customerId: Long
) {

    fun toEntity() = Credit(
        creditValue =  this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallment = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
