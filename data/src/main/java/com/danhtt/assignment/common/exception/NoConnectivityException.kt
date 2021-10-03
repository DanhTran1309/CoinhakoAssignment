package com.danhtt.assignment.common.exception

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message = "Please check internet connection and try again."
}
