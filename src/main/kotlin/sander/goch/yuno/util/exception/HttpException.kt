package sander.goch.yuno.util.exception

import org.springframework.http.HttpStatus

open class HttpException(override val message: String, val statusCode: HttpStatus) : Throwable()