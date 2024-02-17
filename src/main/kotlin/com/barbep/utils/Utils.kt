package com.barbep.utils

import jakarta.ws.rs.InternalServerErrorException
import jakarta.ws.rs.core.SecurityContext
import org.eclipse.microprofile.jwt.JsonWebToken

class Utils {
    companion object {
        fun getResponseString(
            ctx: SecurityContext, jwt: JsonWebToken, hasJwt: () -> Boolean = { jwt.claimNames != null }
        ): String {
            val name: String
            if (ctx.userPrincipal == null) {
                name = "anonymous"
            } else if (ctx.userPrincipal.name != jwt.name) {
                throw InternalServerErrorException("Principal and JsonWebToken names do not match")
            } else {
                name = ctx.userPrincipal.name
            }
            return String.format(
                "hello %s, isHttps: %s, authScheme: %s, hasJWT: %s",
                name,
                ctx.isSecure,
                ctx.authenticationScheme,
                hasJwt()
            )
        }
    }

}