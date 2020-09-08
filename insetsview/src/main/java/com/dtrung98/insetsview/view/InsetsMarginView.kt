package com.dtrung98.insetsview.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets

/**
 * Constraint other child views in the same ConstraintLayout parent to this InsetsMarginView view so that those views will not be covered by the System UI.
 * By using this pattern, you can handle almost the common ui layouts.
 *
 * ```
 *  <androidx.constraintlayout.widget.ConstraintLayout
 *      android:id="@+id/constraint_layout"
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent">
 *
 *  <com.dtrung98.insetsview.view.InsetsMarginView
 *      android:id="@+id/insetsMarginView"
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
 *      app:layout_constraintStart_toStartOf="@+id/insetsMarginView"
 *      app:layout_constraintEnd_toEndOf="@+id/insetsMarginView"
 *      app:layout_constraintTop_toTopOf="@+id/insetsMarginView"
 *      app:layout_constraintBot_toBotOf="@+id/insetsMarginView"/>
 *
 *  ...
 *
 *  </androidx.constraintlayout.widget.ConstraintLayout>
 * ```
 */
class InsetsMarginView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    init {
        visibility = INVISIBLE
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