# InsetsView
**Constraint view elements to not be covered by the android system bars (status bar, navigation bar, etc) with XML ONLY, no ANY CODE needed**

## Screen shot

<div align="center">
  <table align="center" border="0" >
  <tr>
    <td> <img width="360"
src="https://raw.githubusercontent.com/dtrung98/Source/master/9ff8de686f469018c957.jpg"/></td>
    <td> <img width="360"
src="https://raw.githubusercontent.com/dtrung98/Source/master/54c689ea37c4c89a91d5.jpg"/></td>
  </tr>
</table>
  </div>
</br>

## Usage
There're several custom view groups provided in the library:

 #### SafeAreaView
 
Constraint other child views in the same ConstraintLayout parent to this SafeAreaView view so those views will not be covered by the System UI. By using this view, you can handle almost the common ui layout types.

```
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/constraint_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

<com.dtrung98.insetsview.view.SafeAreaView
    android:id="@+id/safeArea"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
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
    app:layout_constraintStart_toStartOf="@+id/safeArea"
    app:layout_constraintEnd_toEndOf="@+id/safeArea"
    app:layout_constraintTop_toTopOf="@+id/safeArea"
    app:layout_constraintBot_toBotOf="@+id/safeArea"/>

...

</androidx.constraintlayout.widget.ConstraintLayout
```

## Adding to projects
```
implementation 'com.github.ldt-libs:InsetsView:1.0'
```
