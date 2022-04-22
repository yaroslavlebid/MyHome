package yaroslavlebid.apps.myhome.ui.helpers

data class Event<T> (
    val status: T? = null,
    val customMessage: String = ""
)