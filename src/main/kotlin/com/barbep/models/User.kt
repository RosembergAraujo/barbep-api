package com.barbep.models

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "user_tb")
data class User(

    @Column(name = "name")
    val name: String? = null,

    @Column(name = "email", unique = true)
    val email: String? = null,

    @Column(name = "password", unique = true)
    val password: String? = null,

    @Column(name = "phone", unique = true)
    val phone: String? = null

) : PanacheEntity()
