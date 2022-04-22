package yaroslavlebid.apps.myhome.ui.login.sign_up

object RegistrationStatusMap {
    fun getErrorMessage(status: RegistrationStatus) = when (status) {
        RegistrationStatus.SUCCESS -> ""
        RegistrationStatus.ERROR_EMAIL_EMPTY -> "Enter your e-mail"
        RegistrationStatus.ERROR_PASSWORD_EMPTY -> "Enter your password"
        RegistrationStatus.ERROR_PASSWORD_TOO_SHORT -> "Password length must be more than 6 symbols"
        RegistrationStatus.ERROR_DEFAULT -> "Registration failed."
        else -> "Authentication failed"
    }
}

enum class RegistrationStatus {
    SUCCESS,
    ERROR_PASSWORD_TOO_SHORT,
    ERROR_PASSWORD_EMPTY,
    ERROR_EMAIL_EMPTY,
    ERROR_DEFAULT,
    CUSTOM_ERROR
}