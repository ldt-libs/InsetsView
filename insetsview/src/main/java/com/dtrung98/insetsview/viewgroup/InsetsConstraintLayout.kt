package com.dtrung98.insetsview.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.WindowInsetsCompat
import com.dtrung98.insetsview.InsetApplyingType
import com.dtrung98.insetsview.R
import com.dtrung98.insetsview.applyInsets

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
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var _insetLeftType = InsetApplyingType.PADDING
    private var _insetTopType = InsetApplyingType.PADDING
    private var _insetRightType = InsetApplyingType.PADDING
    private var _insetBottomType = InsetApplyingType.PADDING

    private var _consumeInsets = true

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.InsetsConstraintLayout
            )
            _insetLeftType =
                InsetApplyingType.from(
                    a.getInt(
                        R.styleable.InsetsConstraintLayout_applyWindowInsetLeft,
                        0
                    )
                )
            _insetTopType =
                InsetApplyingType.from(
                    a.getInt(
                        R.styleable.InsetsConstraintLayout_applyWindowInsetTop,
                        0
                    )
                )
            _insetRightType =
                InsetApplyingType.from(
                    a.getInt(
                        R.styleable.InsetsConstraintLayout_applyWindowInsetRight,
                        0
                    )
                )
            _insetBottomType =
                InsetApplyingType.from(
                    a.getInt(
                        R.styleable.InsetsConstraintLayout_applyWindowInsetBottom,
                        0
                    )
                )

            _consumeInsets =
                a.getBoolean(R.styleable.InsetsConstraintLayout_consumeWindowInsets, true)

            a.recycle()
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        insets?.run {

            val insetInfo =
                applyInsets(this, _insetLeftType, _insetTopType, _insetRightType, _insetBottomType)

            if (_consumeInsets) {

                return WindowInsetsCompat.toWindowInsetsCompat(this).inset(
                    insetInfo.insetLeft,
                    insetInfo.insetTop,
                    insetInfo.insetRight,
                    insetInfo.insetBottom
                ).toWindowInsets() ?: this.consumeSystemWindowInsets()
            }
            requestLayout()
        }
        return super.onApplyWindowInsets(insets)
    }

}