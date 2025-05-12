package sander.goch.yuno.jwt

import Config
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Service
import java.util.*

@Service
object JwtService {

    /**
     * @return the secret key from the hmac-512.key file
     */
    private fun loadSecretKey(): ByteArray {
        return Config.jwtSecret.toByteArray()
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
            .withExpiresAt(Date(System.currentTimeMillis() + 3600000))
            .sign(algorithm)
    }

}