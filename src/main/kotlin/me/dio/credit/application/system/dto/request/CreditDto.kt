package me.dio.credit.application.system.dto.request

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal
import java.time.LocalDate
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer

data class CreditDto (
    @field:NotNull(message = "Verify creditValue") val creditValue: BigDecimal,
    @field:Future(message = "Verify the date") val dayFirstOfInstallment: LocalDate,
    @field:Positive @field:Max(value = 48) val numberOfInstallments: Int,
    @field:NotNull(message = "Invalid customerId")val customerId: Long
) {

    fun toEntity() = Credit(
        creditValue =  this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallment = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
