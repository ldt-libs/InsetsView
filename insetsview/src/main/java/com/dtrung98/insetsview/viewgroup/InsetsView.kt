package com.dtrung98.insetsview.viewgroup

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.dtrung98.insetsview.R
import java.lang.IllegalArgumentException
import kotlin.math.max

/**
 * This view group gives you multiple attributes to consume the window insets
 *   + consumeWindowInsets
 *   + applyWindowInsetTop
 *   + applyWindowInsetLeft
 *   + applyWindowInsetRight
 *   + applyWindowInsetBottom
 */
enum class DispatchSystemBarInsetsType {
    NOT_CONSUMED,
    CONSUMED,
    ADJUST_REDUCE,
    ADJUST_RELATIVE,
    ADJUST_FIXED;

    companion object {
        fun from(value: Int) = when (value) {
            1 -> CONSUMED
            2 -> ADJUST_REDUCE
            3 -> ADJUST_RELATIVE
            4 -> ADJUST_FIXED
            else -> NOT_CONSUMED
        }
    }
}

enum class ApplySystemBarsInsetsType {
    IGNORE,
    PADDING,
    MARGIN;

    companion object {
        fun from(value: Int) = when (value) {
            1 -> PADDING
            2 -> MARGIN
            else -> IGNORE
        }
    }
}

class InsetsViewAttribute {
    var applySystemBarsInsetsTop = ApplySystemBarsInsetsType.IGNORE
    var applySystemBarsInsetsStart = ApplySystemBarsInsetsType.IGNORE
    var applySystemBarsInsetsEnd = ApplySystemBarsInsetsType.IGNORE
    var applySystemBarsInsetsBottom = ApplySystemBarsInsetsType.IGNORE

    var dispatchSystemBarsInsetsTop = DispatchSystemBarInsetsType.NOT_CONSUMED
    var dispatchSystemBarsInsetsStart = DispatchSystemBarInsetsType.NOT_CONSUMED
    var dispatchSystemBarsInsetsEnd = DispatchSystemBarInsetsType.NOT_CONSUMED
    var dispatchSystemBarsInsetsBottom = DispatchSystemBarInsetsType.NOT_CONSUMED

    var dispatchSystemBarsInsetsAdjustStart = 0
    var dispatchSystemBarsInsetsAdjustTop = 0
    var dispatchSystemBarsInsetsAdjustEnd = 0
    var dispatchSystemBarsInsetsAdjustBottom = 0

    var applySystemBarsInsetsAdjustStart = 0
    var applySystemBarsInsetsAdjustTop = 0
    var applySystemBarsInsetsAdjustEnd = 0
    var applySystemBarsInsetsAdjustBottom = 0

    var isDispatchSystemBarsInsetsAdjustReduce = true
}

interface InsetsView {
    val insetsViewAttribute: InsetsViewAttribute

    fun initialize(context: Context, attrs: AttributeSet? = null) {
        attrs?.let {
            val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.InsetsView
            )
            insetsViewAttribute.apply {
                applySystemBarsInsetsStart = ApplySystemBarsInsetsType.from(
                    a.getInt(
                        R.styleable.InsetsView_applySystemBarsInsets_Start,
                        0
                    )
                )
                applySystemBarsInsetsTop = ApplySystemBarsInsetsType.from(
                    a.getInt(
                        R.styleable.InsetsView_applySystemBarsInsets_Top,
                        0
                    )
                )
                applySystemBarsInsetsEnd = ApplySystemBarsInsetsType.from(
                    a.getInt(
                        R.styleable.InsetsView_applySystemBarsInsets_End,
                        0
                    )
                )
                applySystemBarsInsetsBottom = ApplySystemBarsInsetsType.from(
                    a.getInt(
                        R.styleable.InsetsView_applySystemBarsInsets_Bottom,
                        0
                    )
                )

                applySystemBarsInsetsAdjustStart =
                    a.getDimension(R.styleable.InsetsView_applySystemBarsInsetsAdjust_Start, 0f)
                        .toInt()
                applySystemBarsInsetsAdjustTop =
                    a.getDimension(R.styleable.InsetsView_applySystemBarsInsetsAdjust_Top, 0f)
                        .toInt()
                applySystemBarsInsetsAdjustEnd =
                    a.getDimension(R.styleable.InsetsView_applySystemBarsInsetsAdjust_End, 0f)
                        .toInt()
                applySystemBarsInsetsAdjustBottom =
                    a.getDimension(R.styleable.InsetsView_applySystemBarsInsetsAdjust_Bottom, 0f)
                        .toInt()

                dispatchSystemBarsInsetsStart = DispatchSystemBarInsetsType.from(
                    a.getInt(R.styleable.InsetsView_dispatchSystemBarsInsets_Start, 0)
                )
                dispatchSystemBarsInsetsTop = DispatchSystemBarInsetsType.from(
                    a.getInt(R.styleable.InsetsView_dispatchSystemBarsInsets_Top, 0)
                )
                dispatchSystemBarsInsetsEnd = DispatchSystemBarInsetsType.from(
                    a.getInt(R.styleable.InsetsView_dispatchSystemBarsInsets_End, 0)
                )
                dispatchSystemBarsInsetsBottom = DispatchSystemBarInsetsType.from(
                    a.getInt(R.styleable.InsetsView_dispatchSystemBarsInsets_Bottom, 0)
                )

                dispatchSystemBarsInsetsAdjustStart =
                    a.getDimension(R.styleable.InsetsView_dispatchSystemBarsInsetsAdjust_Start, 0f)
                        .toInt()

                dispatchSystemBarsInsetsAdjustTop =
                    a.getDimension(R.styleable.InsetsView_dispatchSystemBarsInsetsAdjust_Top, 0f)
                        .toInt()

                dispatchSystemBarsInsetsAdjustEnd =
                    a.getDimension(R.styleable.InsetsView_dispatchSystemBarsInsetsAdjust_End, 0f)
                        .toInt()

                dispatchSystemBarsInsetsAdjustBottom =
                    a.getDimension(R.styleable.InsetsView_dispatchSystemBarsInsetsAdjust_Bottom, 0f)
                        .toInt()

                val adjustTypes = arrayOf(
                    dispatchSystemBarsInsetsStart,
                    dispatchSystemBarsInsetsTop,
                    dispatchSystemBarsInsetsEnd,
                    dispatchSystemBarsInsetsBottom
                )

                if (adjustTypes.contains(DispatchSystemBarInsetsType.ADJUST_REDUCE)) {
                    for (type in adjustTypes) {
                        if (type == DispatchSystemBarInsetsType.ADJUST_RELATIVE || type == DispatchSystemBarInsetsType.ADJUST_FIXED) {
                            throw IllegalArgumentException("the dispatching edge insets attribute value \"adjust_reduce\" could not be used together with \"adjust_relative\" or \"adjust_fixed\" in other edge attributes")
                        }
                    }
                    isDispatchSystemBarsInsetsAdjustReduce = true
                } else {
                    isDispatchSystemBarsInsetsAdjustReduce = false
                }

            }
            a.recycle()
        }
    }

    private fun retrieveDispatchingSystemBarInsetsDirectionImpl20(
        dispatchType: DispatchSystemBarInsetsType,
        directionIndex: Int,
        currentInsets: IntArray,
        adjustValue: Int,
        newInsets: IntArray
    ) {
        when (dispatchType) {
            DispatchSystemBarInsetsType.NOT_CONSUMED -> {
                // do nothing
            }

            DispatchSystemBarInsetsType.CONSUMED -> {
                // be zero
                newInsets[directionIndex] = 0
            }

            DispatchSystemBarInsetsType.ADJUST_REDUCE -> {
                // reduced value
                newInsets[directionIndex] = currentInsets[directionIndex] - adjustValue
            }

            DispatchSystemBarInsetsType.ADJUST_RELATIVE -> {
                newInsets[directionIndex] = currentInsets[directionIndex] + adjustValue
            }

            DispatchSystemBarInsetsType.ADJUST_FIXED -> {
                newInsets[directionIndex] = adjustValue
            }
        }
    }

    private fun retrieveApplyingSystemBarInsetsDirectionImpl20(
        applyType: ApplySystemBarsInsetsType,
        directionIndex: Int,
        currentInsets: IntArray,
        adjustValue: Int,
        newPadding: IntArray,
        newMargin: IntArray
    ) {
        when (applyType) {
            ApplySystemBarsInsetsType.IGNORE -> {
                newMargin[directionIndex] = -1
                newPadding[directionIndex] = -1
            }

            ApplySystemBarsInsetsType.MARGIN -> {
                newMargin[directionIndex] = currentInsets[directionIndex] + adjustValue
                newPadding[directionIndex] = -1
            }

            ApplySystemBarsInsetsType.PADDING -> {
                newMargin[directionIndex] = -1
                newPadding[directionIndex] = currentInsets[directionIndex] + adjustValue
            }
        }
    }

    fun insetsViewApplyWindowInsets(insets: WindowInsets?): WindowInsets? {
        if (this !is View) throw ClassCastException("The InsetsView required inherited class is a View or ViewGroup")
        if (insets == null || Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH) {
            return null
        } else {
            return insetsViewApplyWindowInsetsImpl20(insets)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT_WATCH)
    private fun insetsViewApplyWindowInsetsImpl20(insets: WindowInsets): WindowInsets {
        if (this !is View) throw ClassCastException("The InsetsView required inherited class is a View or ViewGroup")
        val isLTR =
            ViewCompat.getLayoutDirection(this@InsetsView) == ViewCompat.LAYOUT_DIRECTION_LTR

        val currentInsets = intArrayOf(
            insets.systemWindowInsetLeft,
            insets.systemWindowInsetTop,
            insets.systemWindowInsetRight,
            insets.systemWindowInsetBottom
        )


        // apply the insets to current view
        insetsViewAttribute.run {
            // -1 means nothing changes
            val newPadding = intArrayOf(-1, -1, -1, -1)
            val newMargin = intArrayOf(-1, -1, -1, -1)

            retrieveApplyingSystemBarInsetsDirectionImpl20(
                applySystemBarsInsetsStart,
                if (isLTR) 0 else 2,
                currentInsets,
                applySystemBarsInsetsAdjustStart,
                newPadding,
                newMargin
            )

            retrieveApplyingSystemBarInsetsDirectionImpl20(
                applySystemBarsInsetsTop,
                1,
                currentInsets,
                applySystemBarsInsetsAdjustTop,
                newPadding,
                newMargin
            )

            retrieveApplyingSystemBarInsetsDirectionImpl20(
                applySystemBarsInsetsEnd,
                if (isLTR) 2 else 0,
                currentInsets,
                applySystemBarsInsetsAdjustEnd,
                newPadding,
                newMargin
            )

            retrieveApplyingSystemBarInsetsDirectionImpl20(
                applySystemBarsInsetsBottom,
                3,
                currentInsets,
                applySystemBarsInsetsAdjustBottom,
                newPadding,
                newMargin
            )

            var doesApplyPadding = false
            var doesApplyMargin = false

            for (item in newPadding) {
                if (item != -1) {
                    doesApplyPadding = true
                    break
                }
            }

            for (item in newMargin) {
                if (item != -1) {
                    doesApplyMargin = true
                    break
                }
            }

            if (doesApplyPadding) {
                updatePadding(newPadding[0], newPadding[1], newPadding[2], newPadding[3])
            }

            if (doesApplyMargin) {
                val layoutParams = layoutParams
                if (layoutParams is ViewGroup.MarginLayoutParams) {
                    layoutParams.leftMargin = newMargin[0]
                    layoutParams.topMargin = newMargin[1]
                    layoutParams.rightMargin = newMargin[2]
                    layoutParams.bottomMargin = newMargin[3]
                }
            }
        }

        // adjust the dispatched insets
        // -1: not consumed
        // 0: consumed
        val newInsets = intArrayOf(-1, -1, -1, -1)
        insetsViewAttribute.run {

            retrieveDispatchingSystemBarInsetsDirectionImpl20(
                dispatchSystemBarsInsetsStart,
                if (isLTR) 0 else 2,
                currentInsets,
                dispatchSystemBarsInsetsAdjustStart,
                newInsets
            )
            retrieveDispatchingSystemBarInsetsDirectionImpl20(
                dispatchSystemBarsInsetsTop,
                1,
                currentInsets,
                dispatchSystemBarsInsetsAdjustTop,
                newInsets
            )
            retrieveDispatchingSystemBarInsetsDirectionImpl20(
                dispatchSystemBarsInsetsEnd,
                if (isLTR) 2 else 0,
                currentInsets,
                dispatchSystemBarsInsetsAdjustEnd,
                newInsets
            )
            retrieveDispatchingSystemBarInsetsDirectionImpl20(
                dispatchSystemBarsInsetsBottom,
                3,
                currentInsets,
                dispatchSystemBarsInsetsAdjustBottom,
                newInsets
            )
        }

        var doesInsetsNotConsumed = true
        var doesInsetsFullyConsumed = true

        for (i in 0 until 4) {
            // at least one direction not equal to -1
            // the dispatching insets is not consumed (no thing consumed)
            if (newInsets[i] != -1) {
                doesInsetsNotConsumed = false
            }

            // at least one direction not equal to 0
            // the dispatching insets is not consumed (not fully consumed)
            if (newInsets[i] != 0) {
                doesInsetsFullyConsumed = false
            }
        }

        if (doesInsetsNotConsumed) {
            return insets
        }
        if (doesInsetsFullyConsumed) {
            return insets.consumeSystemWindowInsets()
        }

        for (i in 0 until 4) {
            if (newInsets[i] == -1) {
                newInsets[i] = currentInsets[i]
            }
        }

        val insetsCompat = WindowInsetsCompat.toWindowInsetsCompat(insets)
        val newInsetsCompat = if (insetsViewAttribute.isDispatchSystemBarsInsetsAdjustReduce) {
            insetsCompat.inset(
                max(0, currentInsets[0] - newInsets[0]),
                max(0, currentInsets[1] - newInsets[1]),
                max(0, currentInsets[2] - newInsets[2]),
                max(0, currentInsets[3] - newInsets[3])
            )
        } else {
            WindowInsetsCompat.Builder().setDisplayCutout(insetsCompat.displayCutout)
                .setMandatorySystemGestureInsets(insetsCompat.mandatorySystemGestureInsets)
                .setStableInsets(insetsCompat.stableInsets)
                .setSystemGestureInsets(insetsCompat.systemGestureInsets)
                .setTappableElementInsets(insetsCompat.tappableElementInsets)
                .setSystemWindowInsets(
                    Insets.of(
                        max(0, newInsets[0]),
                        max(0, newInsets[1]),
                        max(0, newInsets[2]),
                        max(0, newInsets[3])
                    )
                )
                .build()
        }
        val result = newInsetsCompat.toWindowInsets()!!

        return result
    }

}