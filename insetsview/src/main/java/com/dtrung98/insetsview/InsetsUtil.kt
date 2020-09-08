package com.dtrung98.insetsview

import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.updatePadding

internal enum class InsetApplyingType {
    NONE,
    PADDING,
    MARGIN,
    IGNORE;

    companion object {
        fun from(value: Int) = when (value) {
            1 -> PADDING
            2 -> MARGIN
            3 -> IGNORE
            else -> NONE
        }
    }

}

internal data class InsetInfo(
    val insetLeft: Int,
    val insetTop: Int,
    val insetRight: Int,
    val insetBottom: Int
)

internal fun View.applyInsets(
    insets: WindowInsets,
    insetLeftType: InsetApplyingType = InsetApplyingType.NONE,
    insetTopType: InsetApplyingType = InsetApplyingType.NONE,
    insetRightType: InsetApplyingType = InsetApplyingType.NONE,
    insetBottomType: InsetApplyingType = InsetApplyingType.NONE
): InsetInfo {

    var insetLeft = insets.systemWindowInsetLeft
    var insetTop = insets.systemWindowInsetTop
    var insetRight = insets.systemWindowInsetRight
    var insetBottom = insets.systemWindowInsetBottom

    val marginLayoutParams = layoutParams as ViewGroup.MarginLayoutParams

    when (insetLeftType) {
        InsetApplyingType.PADDING -> updatePadding(left = insetLeft)
        InsetApplyingType.MARGIN -> marginLayoutParams.leftMargin = insetLeft
        InsetApplyingType.IGNORE -> {
        }
        else -> {
            insetLeft = 0
        }
    }

    when (insetTopType) {
        InsetApplyingType.PADDING -> updatePadding(top = insetTop)
        InsetApplyingType.MARGIN -> marginLayoutParams.topMargin = insetTop
        InsetApplyingType.IGNORE -> {
        }
        else -> {
            insetTop = 0
        }
    }

    when (insetRightType) {
        InsetApplyingType.PADDING -> updatePadding(right = insetRight)
        InsetApplyingType.MARGIN -> marginLayoutParams.rightMargin = insetRight
        InsetApplyingType.IGNORE -> {
        }
        else -> {
            insetRight = 0
        }
    }

    when (insetBottomType) {
        InsetApplyingType.PADDING -> updatePadding(bottom = insetBottom)
        InsetApplyingType.MARGIN -> marginLayoutParams.bottomMargin = insetBottom
        InsetApplyingType.IGNORE -> {
        }
        else -> {
            insetBottom = 0
        }
    }

    requestLayout()

    return InsetInfo(insetLeft, insetTop, insetRight, insetBottom)
}