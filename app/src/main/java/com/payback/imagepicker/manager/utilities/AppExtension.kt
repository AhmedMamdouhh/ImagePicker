package com.payback.imagepicker.manager.utilities

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.payback.imagepicker.R
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

fun convertFromStringToList(tagsString: String) = tagsString.split(",").toList()

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