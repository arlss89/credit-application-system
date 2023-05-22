package me.dio.credit.application.system.dto

import java.math.BigDecimal
import java.util.UUID
import me.dio.credit.application.system.entity.Credit

data class CreditViewList (
    val creditCode: UUID,
    val creditValue: BigDecimal,
    val numberOfInstallments: Int
) {

    constructor(credit: Credit): this (
        creditCode = credit.creditCode,
        creditValue = credit.creditValue,
        numberOfInstallments = credit.numberOfInstallment
    )

}
