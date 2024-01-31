package com.barbep.services

import com.barbep.dtos.CreateUserInputDto
import com.barbep.models.BarberShop
import com.barbep.models.User
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional

@ApplicationScoped
class UserService {

    @Transactional
    @Throws(EntityExistsException::class)
    fun createUser(dto: CreateUserInputDto) {
        val newUser = User().apply {
            name = dto.name
            email = dto.email
            password = dto.password
            phone = dto.phone
            barbershops = barbershops ?: mutableListOf()
            barbershops?.add(BarberShop().apply { name = "exBarberShop" })
        }
        newUser.persistAndFlush()

    }

    @Transactional
    @Throws(EntityExistsException::class)
    fun updateUser(user: User): User {
        user.persistAndFlush()
        return user
    }

    @Transactional
    fun getUsers(): List<User> {
        return User.listAll()
    }

    @Transactional
    @Throws(EntityNotFoundException::class)
    fun getUserById(id: Long): User? {
        return User.findById(id)
    }

    @Transactional
    @Throws(EntityNotFoundException::class)
    fun deleteUserById(id: Long) {
        if (!User.deleteById(id)) {
            throw EntityNotFoundException()
        }
    }
}
