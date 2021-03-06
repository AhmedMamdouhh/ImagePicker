package com.payback.imagepicker.manager.utilities

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.payback.imagepicker.R
import com.payback.imagepicker.domain.model.image.Image
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


fun SearchView.getQueryTextChangeObservable(): Observable<String> {

    val subject = PublishSubject.create<String>()

    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            subject.onComplete()
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            subject.onNext(newText)
            return true
        }
    })

    return subject

}

fun Fragment.showConfirmDialog(action: NavDirections) {

    val dialogBuilder = AlertDialog.Builder(requireContext())
    val inflater = this.layoutInflater

    @SuppressLint("InflateParams")
    val dialogView = inflater.inflate(R.layout.layout_confirm_dialog, null)
    dialogBuilder.setView(dialogView)

    val alertDialog = dialogBuilder.create()

    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.show()

    val yes = dialogView.findViewById<Button>(R.id.btn_confirm_yes)
    val no = dialogView.findViewById<Button>(R.id.btn_confirm_no)

    yes.setOnClickListener {
        findNavController().navigate(action)
        alertDialog.dismiss()
    }
    no.setOnClickListener {
        alertDialog.dismiss()
    }
}

fun recyclerAnimationExtension(recyclerView: RecyclerView) {
    val resId: Int = R.anim.layout_animation
    val animation: LayoutAnimationController =
        AnimationUtils.loadLayoutAnimation(recyclerView.context, resId)
    recyclerView.layoutAnimation = animation
}



fun connectionSwitcher(
    topView: ImageView,
    bottomView: ImageView,
    cvImagePickerOfflineDialog: MaterialCardView,
    searchContainer: SearchView,
    isOnline: Boolean
) {
    if (isOnline) {
        topView.setImageResource(R.drawable.ic_wave_top_online)
        bottomView.setImageResource(R.drawable.ic_wave_bottom_online)
        cvImagePickerOfflineDialog.visibility = GONE
    } else {
        topView.setImageResource(R.drawable.ic_wave_top_offline)
        bottomView.setImageResource(R.drawable.ic_wave_bottom_offline)
        searchContainer.setQuery("", false)
        searchContainer.clearFocus()
        cvImagePickerOfflineDialog.visibility = VISIBLE
    }
}
