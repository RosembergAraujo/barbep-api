package com.barbep.service

import com.barbep.auth.Auth
import com.barbep.dtos.AuthUserPayloadDto
import com.barbep.dtos.UserLoginInputDto
import com.barbep.models.User
import com.barbep.utils.constants.Roles
import io.quarkus.security.AuthenticationFailedException
import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.jboss.logging.Logger
import org.mindrot.jbcrypt.BCrypt


@ApplicationScoped
class UserService {
    @Inject
    private lateinit var log: Logger

    @Transactional
    fun validateUser(userLoginInputDto: UserLoginInputDto): String {
        val user = User.findByEmail(userLoginInputDto.email)
            ?.takeIf { BCrypt.checkpw(userLoginInputDto.password, it.password) }
            ?.also { log.info("User ${it.email} successfully authenticated") }
            ?: throw AuthenticationFailedException("Invalid email or password")

        val validatedUser = validateUserData(user)

        return Auth.generateToken(
            AuthUserPayloadDto(
                id = validatedUser.id, name = validatedUser.name, roles = validatedUser.roles
            )
        )
    }

    private fun validateUserData(user: User): ValidatedUser {
        val name = user.name ?: throw MissingNameException("User has no name")
        val id = user.id ?: throw MissingIdException("User ID cannot be null")

        return ValidatedUser(id, name, listOf(Roles.USER, Roles.ADMIN, Roles.BARBER))
        //TODO add roles system
    }

    data class MissingNameException(override val message: String) : RuntimeException(message)
    data class MissingIdException(override val message: String) : RuntimeException(message)
    data class ValidatedUser(val id: Long, val name: String, val roles: List<String>)


}