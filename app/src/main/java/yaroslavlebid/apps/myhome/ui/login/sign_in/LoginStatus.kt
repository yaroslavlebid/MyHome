package yaroslavlebid.apps.myhome.ui.login.sign_in

object LoginStatusMap {
    fun getErrorMessage(status: LoginStatus) = when (status) {
        LoginStatus.SUCCESS -> ""
        LoginStatus.ERROR_EMAIL_EMPTY -> "Enter your e-mail"
        LoginStatus.ERROR_PASSWORD_EMPTY -> "Enter your password"
        LoginStatus.ERROR_PASSWORD_TOO_SHORT -> "Password length must be more than 6 symbols"
        LoginStatus.ERROR_DEFAULT -> "Registration failed."
        LoginStatus.INCORRECT_EMAIL -> "Incorrect email. Try again please."
        LoginStatus.INCORRECT_PASSWORD -> "Incorrect password. Try again please."
        else -> "Authentication failed"
    }
}

enum class LoginStatus {
    SUCCESS,
    ERROR_PASSWORD_TOO_SHORT,
    ERROR_PASSWORD_EMPTY,
    ERROR_EMAIL_EMPTY,
    ERROR_DEFAULT,
    CUSTOM_ERROR,
    INCORRECT_EMAIL,
    INCORRECT_PASSWORD
}