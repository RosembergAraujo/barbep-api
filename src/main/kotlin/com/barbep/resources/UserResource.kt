package com.barbep.resources

import com.barbep.auth.Auth
import com.barbep.dtos.AuthUserPayloadDto
import com.barbep.dtos.CreateUserInputDto
import com.barbep.models.BarberShop
import com.barbep.models.User
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.media.*
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody

@Path("/user")
class UserResource {

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @RequestBody(
        content =
        [
            Content(
                mediaType = "application/json",
                schema = Schema(implementation = CreateUserInputDto::class),
                examples =
                [
                    ExampleObject(
                        name = "example1",
                        value =
                        """
                        {
                            "name": "John",
                            "email": "john@example.com",
                            "password": "123",
                            "phone": "1234567890"
                        }
                        """
                    ),
                    ExampleObject(
                        name = "example2",
                        value =
                        """
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
        newUser.password = dto.password
        newUser.phone = dto.phone
        newUser.barbershops?.add(exBarberShop)
        newUser.persistAndFlush()

        return Response.status(Response.Status.CREATED).build()
    }

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    fun getAllUsers(): Response {
        val token = Auth.generateToken(AuthUserPayloadDto(1, "John"))
        val users = User.listAll()
        return Response.ok(object {
            val token = token
            val users = users
        }).build()
    }
}
