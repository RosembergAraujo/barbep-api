package com.barbep.resources

import com.barbep.dtos.CreateUserInputDto
import com.barbep.models.User
import com.barbep.services.UserService
import jakarta.inject.Inject
import jakarta.persistence.EntityExistsException
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody

@Path("/user")
class UserResource {
    //TODO (BERG) classe de error message para retorno da exception

    @Inject
    lateinit var userService: UserService

    @POST
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
        return try {
            Response.status(Response.Status.CREATED).entity(userService.createUser(dto)).build()
        } catch (e: EntityExistsException) {
            Response.status(Response.Status.CONFLICT).entity("Usuário já existente!").build()
        }
    }

    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    fun getUsers(): Response {
        return Response.ok(userService.getUsers()).build()
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserById(@PathParam("id") id: Long): Response {
        return try {
            Response.status(Response.Status.NO_CONTENT).entity(userService.getUserById(id)).build()
        } catch (e: EntityNotFoundException) {
            Response.status(Response.Status.NOT_FOUND).entity("Usuário com id '${id}' não encontrado").build()
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestBody(
        content =
        [
            Content(
                mediaType = "application/json",
                schema = Schema(implementation = User::class),
                examples =
                [
                    ExampleObject(
                        name = "example1",
                        value =
                        """
                        {
                            "id": 123
                            "name": "John",
                            "email": "john@example.com",
                            "password": "123",
                            "phone": "1234567890"
                        }
                        """
                    )
                ]
            )]
    )
    fun updateUser(@Valid user: User): Response {
        return try {
            Response.ok(userService.updateUser(user)).build()
        } catch (e: EntityNotFoundException) {
            Response.status(Response.Status.NOT_FOUND).entity("Usuário com id '${user.id}' não encontrado")
                .build()
        }
    }

    @DELETE
    @Path("/{id}")
    fun deleteUser(@PathParam("id") id: Long): Response {
        try {
            userService.deleteUserById(id)
            return Response.ok().build()
        } catch (e: EntityNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND).entity("Usuário com id '${id}' não encontrado").build()
        }
    }
}
