package com.dtrung98.insetsview.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import android.widget.FrameLayout

class InsetsFrameLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), InsetsView {
    override val insetsViewAttribute = InsetsViewAttribute()
    init {
        initialize(context, attrs)
    }

    override fun onApplyWindowInsets(insets: WindowInsets?) : WindowInsets = insetsViewApplyWindowInsets(insets)
        ?: super.onApplyWindowInsets(insets)

}