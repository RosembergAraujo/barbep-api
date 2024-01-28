package com.barbep.models

import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

@Entity
@Table(name = "barber_shop_user_relation_tb")
class BarberShopUserRelation : PanacheEntity(){
    @OneToOne
    val barberShop: BarberShop? = null
    @OneToOne
    val user: User? = null
}