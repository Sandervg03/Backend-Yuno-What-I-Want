package sander.goch.yuno.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sander.goch.yuno.business.models.AuthCredentials
import sander.goch.yuno.business.service.AuthService

@RestController
@RequestMapping("/auth")
class AuthController(private val service: AuthService) {

    @PostMapping("/login")
    fun login(@RequestBody credentials: AuthCredentials): ResponseEntity<String> {
        try {
            val token: String = service.login(credentials)
            return ResponseEntity
                .status(200)
                .header("Authorization", token)
                .body("Logged in.")
        } catch (error: Exception) {
            return ResponseEntity
                .status(401)
                .body("Incorrect credentials.")
        }
    }

}