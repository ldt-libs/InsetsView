package com.dtrung98.insetsview.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import androidx.constraintlayout.widget.ConstraintLayout


/**
 * This view group gives you multiple attributes to consume the window insets
 *   + consumeWindowInsets
 *   + applyWindowInsetTop
 *   + applyWindowInsetLeft
 *   + applyWindowInsetRight
 *   + applyWindowInsetBottom
 */
class InsetsConstraintLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), InsetsView {
    override val insetsViewAttribute = InsetsViewAttribute()
    init {
        initialize(context, attrs)
    }

    override fun onApplyWindowInsets(insets: WindowInsets?) : WindowInsets = insetsViewApplyWindowInsets(insets)
        ?: super.onApplyWindowInsets(insets)


}