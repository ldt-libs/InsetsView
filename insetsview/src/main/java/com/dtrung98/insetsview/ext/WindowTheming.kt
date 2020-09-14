package com.dtrung98.insetsview.ext

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.Window
import androidx.core.content.ContextCompat
import com.dtrung98.insetsview.R
import java.util.function.BinaryOperator

/**This method applies transparent system bars to the windows (activity, dialog, dialog fragment):
 * - Dark Screen (ex: MeetingFragment): Dark screen use white system bar icons which available in every android version

 * - Light Screen (ex: HomeFragment): Light screen requires dark system bar icons which only available in newer android version
 *      + Android API 27 and above: Transparent System bars & Light system bar icon
 *
 *      + Android API 23 and above: Alpha-black navigation bar color
 *
 *      + Android API 21 and above: Alpha-black system bar color
 *
 *      + Android below 21
 */
fun Window.setUpSystemUIVisibility(light: Boolean) {
    if (light) {
        setUpLightSystemUIVisibility()
    } else {
        setUpDarkSystemUIVisibility()
    }
}

fun Window.setUpLightSystemUIVisibility() {

    decorView.systemUiVisibility = when {
        /* fully supported */
        Build.VERSION.SDK_INT >= 26 -> {
            statusBarColor = 0
            navigationBarColor = 0
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        /* use black navigation bar instead */
        Build.VERSION.SDK_INT >= 23 -> {
            statusBarColor = 0
            navigationBarColor = Color.BLACK

            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

        /* use black navigation bar and blue status bar */
        Build.VERSION.SDK_INT >= 23 -> {
            val colorAccent = ContextCompat.getColor(context, R.color.colorAccent)
            statusBarColor = colorAccent
            navigationBarColor = Color.BLACK
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
        else -> {
            val colorAccent = ContextCompat.getColor(context, R.color.colorAccent)
            (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }
    }
}

fun Window.setUpDarkSystemUIVisibility() {

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        statusBarColor = 0
        navigationBarColor = 0
    }

    decorView.systemUiVisibility =
        (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
}