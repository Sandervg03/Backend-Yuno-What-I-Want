package sander.goch.yuno.data

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import sander.goch.yuno.business.models.AuthCredentials

interface AuthRepository: CrudRepository<AuthCredentials, String> {
    fun findByEmail(email: String): AuthCredentials?

    @Query("INSERT INTO users (email, password) VALUES (:email, :password)")
    @Modifying
    fun insert(
        @Param("email") email: String,
        @Param("password") password: String
    )
}