# InsetsView
By using this library, your app will have truely transparent system bar (status bar, navigation bar, etc) with **XML ONLY**, no **ANY CODE** needed

## Usages

### InsetsMarginView
Constraint other child views in the same ConstraintLayout parent to this InsetsMarginView view so that those views will not be covered by the System UI. By using this view, you can handle almost the common ui layout types.

```
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/constraint_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

<com.dtrung98.insetsview.view.InsetsMarginView
    android:id="@+id/insetsMarginView"
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    android:visibility="invisible"/>
<ImageView
    android:id="@+id/wallImageView"
    android:layout_width="0dp"
    android:layout_height="350dp"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintTop_toTopOf="parent"/>
<TextView
    android:id="@+id/content"
    android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintStart_toStartOf="@+id/insetsMarginView"
    app:layout_constraintEnd_toEndOf="@+id/insetsMarginView"
    app:layout_constraintTop_toTopOf="@+id/insetsMarginView"
    app:layout_constraintBot_toBotOf="@+id/insetsMarginView"/>

...

</androidx.constraintlayout.widget.ConstraintLayout
```
