package sander.goch.yuno.business.service

import org.springframework.stereotype.Service
import sander.goch.yuno.business.models.AuthCredentials
import sander.goch.yuno.data.AuthRepository

@Service
class AuthService(private val db: AuthRepository) {

    fun login(authCredentials: AuthCredentials): String {
        val user: AuthCredentials? = db.findByEmail(authCredentials.email);
        if (user !is AuthCredentials) {
            throw Exception("Incorrect credentials")
        }
        if (user.password == authCredentials.password) {
            return "someString"
        } else {
            throw Exception("Incorrect credentials")
        }
    }
}