package com.barbep.auth

import com.barbep.dtos.AuthUserPayloadDto
import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import java.time.Duration


@ApplicationScoped
class Auth {
    companion object {
        @JvmStatic
        fun generateToken(authUserPayloadDto: AuthUserPayloadDto): String {
            with(authUserPayloadDto) {
                return Jwt
                    .issuer("barbep-mvp")
                    .upn(name)
                    .subject(id.toString())
                    .groups(roles.map { it }.toSet())
                    .expiresIn(Duration.ofHours(1))
                    .sign()
            }

        }

    }
}