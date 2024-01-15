package com.demo.app.utils

import java.io.IOException

class NoInternetException(message: String) : IOException(message)
class APIException(message: String) : IOException(message)