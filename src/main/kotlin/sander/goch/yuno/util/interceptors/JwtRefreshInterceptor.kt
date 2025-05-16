package sander.goch.yuno.util.interceptors

import com.auth0.jwt.exceptions.JWTVerificationException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import sander.goch.yuno.jwt.JwtService

@Component
class JwtRefreshInterceptor(private val jwtService: JwtService) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (request.requestURI.startsWith("/auth/login")) {
            return true
        }
        val jwtToken = request.getHeader("Authorization")
        if (jwtToken.isNullOrBlank()) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return false
        }
        try {
            response.setHeader("Authorization", jwtService.refreshToken(jwtToken))
            return true
        } catch (error: JWTVerificationException) {
            response.status = HttpStatus.UNAUTHORIZED.value()
            return false
        } catch (error: Exception) {
            response.status = HttpStatus.OK.value()
            return false
        }
    }
}