package yaroslavlebid.apps.myhome.utils

import android.content.Context
import yaroslavlebid.apps.myhome.R

object AdvantageMapper {
    fun mapAdvantageNameToResId(context: Context, title: String): Int? = when (title) {
        context.getString(R.string.free_wifi) -> R.drawable.wifi
        context.getString(R.string.free_parking) -> R.drawable.local_parking
        context.getString(R.string.breakfest) -> R.drawable.local_cafe
        context.getString(R.string.bar) -> R.drawable.local_bar
        context.getString(R.string.restaurant) -> R.drawable.restaurant
        context.getString(R.string.transfer) -> R.drawable.emoji_transportation
        context.getString(R.string.service) -> R.drawable.cleaning_services
        context.getString(R.string.non_smoking_rooms) -> R.drawable.smoke_free
        context.getString(R.string.pets_allowed) -> R.drawable.pets
        context.getString(R.string.facilities_for_disabled) -> R.drawable.accessible
        else -> null
    }
}

/*
<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/free_wifi"
app:chipIcon="@drawable/wifi" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/free_parking"
app:chipIcon="@drawable/local_parking" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/breakfest"
app:chipIcon="@drawable/local_cafe" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/bar"
app:chipIcon="@drawable/local_bar" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/restaurant"
app:chipIcon="@drawable/restaurant" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/transfer"
app:chipIcon="@drawable/emoji_transportation" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/service"
app:chipIcon="@drawable/cleaning_services" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/non_smoking_rooms"
app:chipIcon="@drawable/smoke_free" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/pets_allowed"
app:chipIcon="@drawable/pets" />

<com.google.android.material.chip.Chip
style="@style/Theme.MyHome.AdvantageChip"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
android:text="@string/facilities_for_disabled"
app:chipIcon="@drawable/accessible" />*/
