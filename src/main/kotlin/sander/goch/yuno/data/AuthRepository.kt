package sander.goch.yuno.data

import org.springframework.data.repository.CrudRepository
import sander.goch.yuno.business.models.AuthCredentials

interface AuthRepository: CrudRepository<AuthCredentials, String> {
    fun findByEmail(email: String): AuthCredentials?
}