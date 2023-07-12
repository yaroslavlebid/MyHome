package yaroslavlebid.apps.myhome.utils

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.getText() = this.editText?.text.toString()