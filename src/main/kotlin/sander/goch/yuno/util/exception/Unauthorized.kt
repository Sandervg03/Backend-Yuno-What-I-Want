package sander.goch.yuno.util.exception

import org.springframework.http.HttpStatus

class Unauthorized(
    message: String = "You are not authorized to perform this action.",
    statusCode: HttpStatus = HttpStatus.UNAUTHORIZED
) : HttpException(message, statusCode)