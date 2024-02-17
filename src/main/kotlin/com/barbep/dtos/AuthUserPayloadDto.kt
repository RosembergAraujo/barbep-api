package com.barbep.dtos

data class AuthUserPayloadDto(val id: Long, val name: String, val roles: List<String> = emptyList()) {
}