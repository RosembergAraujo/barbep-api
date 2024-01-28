package com.barbep

import com.barbep.dtos.CreateUserInputDto
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.media.Content
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody


@Path("/hello")
class GreetingResource {

    @POST
//    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RequestBody(
        content = [Content(
            mediaType = "application/json",
            schema = Schema(implementation = CreateUserInputDto::class),
            examples = [
                ExampleObject(
                    name = "example1",
                    value = "{\"name\": \"John\"," +
                            " \"email\": \"john@example.com\"," +
                            " \"password\": \"123\"," +
                            " \"phone\": \"1234567890\"}"
                ),
                ExampleObject(
                    name = "example2",
                    value = "{\"name\": \"John\"," +
                            " \"email\": \"john@example.com\"," +
                            " \"phone\": \"123-456-7890\"}"
                )
            ]
        )]
    )
    fun createUser(@Valid userInput: CreateUserInputDto): CreateUserInputDto {
        return userInput
    }

}