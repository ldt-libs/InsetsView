package com.dtrung98.insetsview

import android.content.Context
import android.util.AttributeSet
import android.util.SparseArray
import androidx.constraintlayout.solver.widgets.ConstraintWidget
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer
import androidx.constraintlayout.solver.widgets.Helper
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout

private class InsetsViewHelper @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintHelper(context, attrs, defStyleAttr) {
    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
    }

    override fun updatePostMeasure(container: ConstraintLayout?) {
        super.updatePostMeasure(container)
    }

    override fun updatePreLayout(
        container: ConstraintWidgetContainer?,
        helper: Helper?,
        map: SparseArray<ConstraintWidget>?
    ) {
        super.updatePreLayout(container, helper, map)
    }
}