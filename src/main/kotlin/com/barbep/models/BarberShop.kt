package com.barbep.models

import com.fasterxml.jackson.annotation.JsonIgnore
import io.quarkus.hibernate.orm.panache.kotlin.PanacheCompanion
import io.quarkus.hibernate.orm.panache.kotlin.PanacheEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table

@Entity
@Table(name = "barber_shop_tb")
class BarberShop : PanacheEntity() {

    @Column var name: String? = null
    // @OneToOne
    // val owner: User? = null
    @field:JsonIgnore
    @ManyToMany(mappedBy = "barbershops")
    var users: MutableList<User>? = mutableListOf<User>()

    companion object : PanacheCompanion<BarberShop>
}
