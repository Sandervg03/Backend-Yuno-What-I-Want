package sander.goch.yuno.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtService {

    /**
     * @return the secret key from the hmac-512.key file
     */
    private fun loadSecretKey(): ByteArray {
        val stream = JwtService::class.java
            .classLoader
            .getResourceAsStream("hmac-512.key")
            ?: throw IllegalStateException("hmac-512.key not found on classpath")
        val keyBase64 = stream.readAllBytes().toString(Charsets.UTF_8).trim()
        return Base64.getDecoder().decode(keyBase64)
    }

    private val algorithm = Algorithm.HMAC512(loadSecretKey())
    private val verifier = JWT
        .require(algorithm)
        .withIssuer("Sander.Goch.Yuno")
        .build()

    /**
     * Validates a JWT token and returns the id claim
     * @param token given by the user
     * @return the user id claim stored in the JWT token
     */
    fun validateJWTToken(token: String): String {
        val jwt = verifier.verify(token)
        return jwt.getClaim("id").asString()
    }

    /**
     * @param id of the user
     * @return the generated JWT token
     */
    fun generateToken(id: String): String {
        return JWT.create()
            .withIssuer("Sander.Goch.Yuno")
            .withClaim("id", id)
            .withIssuedAt(Date())
            .withExpiresAt(Date(System.currentTimeMillis() + 3600000))
            .sign(algorithm)
    }

}