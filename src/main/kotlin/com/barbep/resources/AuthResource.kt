package com.barbep.resources

import com.barbep.dtos.UserLoginInputDto
import com.barbep.service.UserService
import jakarta.annotation.security.PermitAll
import jakarta.inject.Inject
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.jwt.JsonWebToken

@Path("secured")
class AuthResource {
    @Inject
    lateinit var jwt: JsonWebToken

    @Inject
    lateinit var userService: UserService

    @POST
    @Path("login")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    fun validateUser(userLoginInputDto: UserLoginInputDto): Response {
        return Response.ok(object {
            val token = userService.validateUser(userLoginInputDto)
        }).build()
    }

}