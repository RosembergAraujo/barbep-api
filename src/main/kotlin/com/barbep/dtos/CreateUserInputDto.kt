package com.barbep.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateUserInputDto(

    @field:NotBlank(message = "Name cannot be empty")
    val name: String,

    @field:Email(message = "Email is not valid")
    val email: String,

    @field:NotBlank(message = "Password cannot be empty")
    val password: String,

    @field:Size(min = 9, max = 14, message = "Phone length is not valid")
    @field:NotBlank(message = "Phone is not valid")
    val phone: String
)
