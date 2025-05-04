package sander.goch.yuno.util.exception

import org.springframework.http.HttpStatus

class Database(
    message: String = "Something went wrong while performing a database action.",
    statusCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
) : HttpException(message, statusCode)