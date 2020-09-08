package com.dtrung98.insetsview.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import android.widget.LinearLayout
import androidx.core.view.WindowInsetsCompat
import com.dtrung98.insetsview.InsetApplyingType
import com.dtrung98.insetsview.R
import com.dtrung98.insetsview.applyInsets

class InsetsLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var _insetLeftType = InsetApplyingType.PADDING
    private var _insetTopType = InsetApplyingType.PADDING
    private var _insetRightType = InsetApplyingType.PADDING
    private var _insetBottomType = InsetApplyingType.PADDING

    private var _consumeInsets = true

    init {
        attrs?.let {
            val a = context.obtainStyledAttributes(attrs,
                R.styleable.InsetsLinearLayout
            )
            _insetLeftType =
                InsetApplyingType.from(
                    a.getInt(
                        R.styleable.InsetsLinearLayout_applyWindowInsetLeft,
                        0
                    )
                )
            _insetTopType =
                InsetApplyingType.from(
                    a.getInt(
                        R.styleable.InsetsLinearLayout_applyWindowInsetTop,
                        0
                    )
                )
            _insetRightType =
                InsetApplyingType.from(
                    a.getInt(
                        R.styleable.InsetsLinearLayout_applyWindowInsetRight,
                        0
                    )
                )
            _insetBottomType =
                InsetApplyingType.from(
                    a.getInt(
                        R.styleable.InsetsLinearLayout_applyWindowInsetBottom,
                        0
                    )
                )

            _consumeInsets = a.getBoolean(R.styleable.InsetsLinearLayout_consumeWindowInsets, true)

            a.recycle()
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        insets?.run {

            val insetInfo = applyInsets(this, _insetLeftType, _insetTopType,  _insetRightType , _insetBottomType)

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