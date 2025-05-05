package sander.goch.yuno.controller

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import sander.goch.yuno.business.models.AuthCredentials
import sander.goch.yuno.business.service.AuthService
import sander.goch.yuno.jwt.JwtService
import sander.goch.yuno.util.exception.HttpException

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
                .body("Logged in")
        } catch (error: HttpException) {
            return ResponseEntity
                .status(error.statusCode)
                .body(error.message)
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody credentials: AuthCredentials): ResponseEntity<String> {
        try {
            service.register(credentials)
            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User registered")
        } catch (error: HttpException) {
            return ResponseEntity
                .status(error.statusCode)
                .body(error.message)
        } catch (error: DataIntegrityViolationException) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("A user with this email already exists")
        }
    }

    @GetMapping("/isLoggedIn")
    fun isLoggedIn(@RequestHeader("Authorization") token: String): ResponseEntity<Boolean> {
        try {
            val id: String = JwtService.validateJWTToken(token)
            return ResponseEntity
                .status(HttpStatus.OK)
                .header("Authorization", JwtService.generateToken(id))
                .body(true)
        } catch (error: Exception) {
            println(error)
            return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(false)
        }
    }

}