package com.barbep.models

import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.*

@Entity
@Table(name = "user_tb")
class User : PanacheEntity() {

    @Column
    var name: String? = null

    @Column(unique = true)
    var email: String? = null

    @Column var password: String? = null

    @Column var phone: String? = null

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST, CascadeType.MERGE])
    @JoinTable(
            name = "user_barbershop_relation",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "barber_shop_id")]
    )
    var barbershops: MutableList<BarberShop>? = mutableListOf()

    companion object : PanacheCompanion<User>
}
