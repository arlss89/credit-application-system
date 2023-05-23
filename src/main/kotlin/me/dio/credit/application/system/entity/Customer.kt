package me.dio.credit.application.system.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import me.dio.credit.application.system.dto.response.CustomerView

@Entity
//@Table(name = "cliente")
data class Customer(
    @Column(nullable = false)
    var firstName: String = "",

    @Column(nullable = false)
    var lastName: String = "",

    @Column(nullable = false, unique = true)
    var cpf: String = "",

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var password: String = "",

    @Column(nullable = false)
    var income: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    @Embedded
    var address: Address = Address(),

    @Column(nullable = false)
    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.REMOVE, CascadeType.PERSIST],
        mappedBy = "customer"
    )
    var credits: List<Credit> = mutableListOf(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

) {
    override fun toString(): String {
        return "firstName='$firstName', lastName='$lastName', income=$income"
    }
}

