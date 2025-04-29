package sander.goch.yuno.business.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("USERS")
data class AuthCredentials(val email: String, val password: String, @Id val id: String? = null)
