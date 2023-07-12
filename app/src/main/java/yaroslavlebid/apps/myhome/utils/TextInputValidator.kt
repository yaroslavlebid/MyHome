package yaroslavlebid.apps.myhome.utils

object TextInputValidator {

    private const val EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private const val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\\\S+\$).{4,}\$"

    fun isEmailValid(email: String): Boolean {
        if (email.matches(EMAIL_PATTERN.toRegex()) && email.length > 0) {
            return true
        } else {
            return false
        }
    }

    fun isPasswordValid(password: String): Boolean {
        if(password.length > 6) {
            return true
        } else {
            return false
        }
    }
}