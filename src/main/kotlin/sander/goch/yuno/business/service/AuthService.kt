package sander.goch.yuno.business.service

import org.mindrot.jbcrypt.BCrypt
import org.springframework.stereotype.Service
import sander.goch.yuno.business.models.AuthCredentials
import sander.goch.yuno.data.AuthRepository
import sander.goch.yuno.jwt.JwtService
import sander.goch.yuno.util.exception.Database
import sander.goch.yuno.util.exception.Unauthorized

@Service
class AuthService(private val db: AuthRepository) {

    fun login(authCredentials: AuthCredentials): String {
        val user: AuthCredentials? = db.findByEmail(authCredentials.email)
        if (user !is AuthCredentials) {
            throw Unauthorized("Incorrect credentials")
        }
        if (BCrypt.checkpw(authCredentials.password, user.password)) {
            return JwtService.generateToken(user.id!!.toString())
        } else {
            throw Unauthorized("Incorrect credentials")
        }
    }

    fun register(credentials: AuthCredentials) {
        db.insert(
            credentials.email,
            BCrypt.hashpw(credentials.password, BCrypt.gensalt())
        )

        if (db.findByEmail(credentials.email) == null) {
            throw Database("Something went wrong while registering user")
        }
    }
}