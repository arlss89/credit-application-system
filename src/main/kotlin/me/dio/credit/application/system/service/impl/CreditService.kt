package me.dio.credit.application.system.service.impl

import java.lang.RuntimeException
import java.util.*
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.service.ICreditService
import org.springframework.stereotype.Service

@Service
class CreditService (
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
    ): ICreditService {

    override fun save(credit: Credit): Credit {

        credit.apply {
            customer = customerService.findByID(credit.customer?.id!!)
        }

        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> {
        return this.creditRepository.findAllByCustomerId(customerId)
    }

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit? {
        val credit = this.creditRepository.findByCreditCode(creditCode) ?:
        throw RuntimeException("CreditCode $creditCode not found")

        return if (credit.customer?.id == customerId) {
            credit
        } else {
            throw RuntimeException ("Contact admin")
        }
    }
}