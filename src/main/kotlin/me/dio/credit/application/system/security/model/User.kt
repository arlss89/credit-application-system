package me.dio.credit.application.system.security.model

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table


@Entity
@Table(name = "tab_user")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    var id: Int? = null

    @Column(length = 50, nullable = false)
    var name: String? = null

    @Column(length = 20, nullable = false)
    var username: String? = null

    @Column(length = 100, nullable = false)
    var password: String? = null

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tab_user_roles", joinColumns = [JoinColumn(name = "user_id")])
    @Column(name = "role_id")
    var roles: List<String> = ArrayList()

    constructor() {}

    constructor(username: String?) {
        this.username = username
    }

}