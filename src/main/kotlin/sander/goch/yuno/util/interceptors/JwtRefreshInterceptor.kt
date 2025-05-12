package sander.goch.yuno.util.interceptors

import com.auth0.jwt.exceptions.JWTVerificationException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import sander.goch.yuno.jwt.JwtService

@Component
class JwtRefreshInterceptor(private val jwtService: JwtService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.requestURI.startsWith("/auth/login")) {
            return true
        }
        val jwtToken = request.getHeader("Authorization") ?: return false
        try {
            val userId = jwtService.validateJWTToken(jwtToken)
            response.setHeader("Authorization", jwtService.generateToken(userId))
            return true
        } catch (error: JWTVerificationException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return false
        } catch (error: Exception) {
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            return false
        }
    }
}