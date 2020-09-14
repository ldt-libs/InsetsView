package com.dtrung98.insetsview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

/**
 * Constraint other child views in the ConstraintLayout parent to this SafeAreaView so that those views will not be covered by the System Bar (Status bar, Bottom Navigation Bar).
 * By using this view, you can handle almost the common ui layouts.
 *
 * ```
 *  <androidx.constraintlayout.widget.ConstraintLayout
 *      android:id="@+id/constraint_layout"
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent">
 *
 *  <com.dtrung98.insetsview.SafeAreaView
 *      android:id="@+id/safeArea"
 *      android:layout_width="0dp"
 *      android:layout_height="0dp"
 *      app:layout_constraintStart_toStartOf="parent"
 *      app:layout_constraintEnd_toEndOf="parent"
 *      app:layout_constraintBottom_toBottomOf="parent"
 *      app:layout_constraintTop_toTopOf="parent"
 *      android:visibility="invisible"/>
 *  <ImageView
 *      android:id="@+id/wallImageView"
 *      android:layout_width="0dp"
 *      android:layout_height="350dp"
 *      app:layout_constraintStart_toStartOf="parent"
 *      app:layout_constraintEnd_toEndOf="parent"
 *      app:layout_constraintTop_toTopOf="parent"/>
 *  <TextView
 *      android:id="@+id/content"
 *      android:layout_width="0dp"
 *      android:layout_height="0dp"
 *      app:layout_constraintStart_toStartOf="@+id/safeArea"
 *      app:layout_constraintEnd_toEndOf="@+id/safeArea"
 *      app:layout_constraintTop_toTopOf="@+id/safeArea"
 *      app:layout_constraintBot_toBotOf="@+id/safeArea"/>
 *
 *  ...
 *
 *  </androidx.constraintlayout.widget.ConstraintLayout>
 * ```
 */
class SafeAreaView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    init {
        super.setVisibility(INVISIBLE)
    }

    override fun setVisibility(visibility: Int) {}

    @SuppressLint("MissingSuperCall")
    override fun draw(canvas: Canvas?) {}
    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        params?.apply {
            when (this) {
                is ConstraintLayout.LayoutParams -> {
                    width = 0
                    height = 0
                    startToStart = ConstraintSet.PARENT_ID
                    topToTop = ConstraintSet.PARENT_ID
                    endToEnd = ConstraintSet.PARENT_ID
                    bottomToBottom = ConstraintSet.PARENT_ID
                }
                else -> {
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = ViewGroup.LayoutParams.MATCH_PARENT
                }
            }
        }
        super.setLayoutParams(params)
    }

    override fun onApplyWindowInsets(insets: WindowInsets?): WindowInsets {
        val lp = layoutParams as ViewGroup.MarginLayoutParams?

        if (lp != null)
            insets?.apply {
                lp.topMargin = systemWindowInsetTop
                lp.leftMargin = systemWindowInsetLeft
                lp.rightMargin = systemWindowInsetRight
                lp.bottomMargin = systemWindowInsetBottom
            }
        return super.onApplyWindowInsets(insets)
    }
}