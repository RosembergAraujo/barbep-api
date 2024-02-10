package com.barbep.auth
import com.barbep.dtos.AuthUserPayloadDto
import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped
import java.time.Duration
import java.util.*

@ApplicationScoped
class Auth {
    companion object {
        @JvmStatic
        fun generateToken(authUserPayloadDto: AuthUserPayloadDto): String {
            return Jwt.issuer("https://example.com/issuer")
                .upn(authUserPayloadDto.name)
                .subject(authUserPayloadDto.id.toString())
                .groups(setOf("User", "Admin", "Barber_Manager"))
                .expiresIn(Duration.ofHours(1))
                .sign()
        }

        @JvmStatic
        fun generateToken(): String {
            return Jwt.issuer("https://example.com/issuer")
                .upn("anonymous")
                .groups(setOf("User", "Admin"))
                .sign()
        }
    }
}