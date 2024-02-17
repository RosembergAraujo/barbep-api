package com.barbep.resources

import com.barbep.auth.Auth
import com.barbep.dtos.AuthUserPayloadDto
import com.barbep.dtos.CreateUserInputDto
import com.barbep.models.BarberShop
import com.barbep.models.User
import com.barbep.utils.constants.Roles
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.jwt.JsonWebToken
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.mindrot.jbcrypt.BCrypt


@Path("/user")
class UserResource {

    @Inject
    lateinit var jwt: JsonWebToken

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @RequestBody(
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = CreateUserInputDto::class),
            examples = [ExampleObject(
                name = "example1", value = """
                        {
                            "name": "John",
                            "email": "john@example.com",
                            "password": "123",
                            "phone": "1234567890"
                        }
                        """
            ), ExampleObject(
                name = "example2", value = """
                        {
                            "name": "John",
                            "email": "john@example.com",
                            "phone": "123-456-7890"
                        }
                        """
            )]
        )]
    )
    fun createUser(@Valid dto: CreateUserInputDto): Response {
        val newUser = User()
        val exBarberShop = BarberShop()
        exBarberShop.name = "exBarberShop"

        newUser.name = dto.name
        newUser.email = dto.email
        newUser.password = BCrypt.hashpw(dto.password, BCrypt.gensalt())
        newUser.phone = dto.phone
        newUser.barbershops?.add(exBarberShop)
        newUser.persistAndFlush()

        return Response.status(Response.Status.CREATED).build()
    }

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllUsers(): Response {
        val token = Auth.generateToken(
            AuthUserPayloadDto(
                id = 1, name = "John", roles = listOf(Roles.USER, Roles.ADMIN, Roles.BARBER)
            )
        )
        val users = User.listAll()
        return Response.ok(object {
            val token = token
            val users = users
        }).build()
    }
}
