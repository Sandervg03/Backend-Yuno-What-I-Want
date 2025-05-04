package sander.goch.yuno.business.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("USERS")
data class AuthCredentials(
    @Id val id: String? = null,
    val email: String,
    val password: String,
)
