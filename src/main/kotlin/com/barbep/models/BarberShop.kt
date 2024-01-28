package com.barbep.models

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "barber_shop_tb")
class BarberShop : PanacheEntity() {
    val name: String? = null
    @OneToOne
    val owner: User? = null
}