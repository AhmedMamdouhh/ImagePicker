package com.payback.imagepicker.manager.base

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.payback.imagepicker.R
import com.payback.imagepicker.manager.utilities.Constants
import com.payback.imagepicker.manager.utilities.EventObserver
import com.payback.imagepicker.presentation.response.error.ErrorSheet
import com.payback.imagepicker.presentation.response.no_connection.NoConnectionSheet
import com.payback.imagepicker.presentation.response.success.SuccessDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private var loadingBar: Dialog? = null
    private val baseActivityViewModel: BaseActivityViewModel by viewModels()

    @Inject
    lateinit var responseManager: ResponseManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializeProgress()
        observeResponse()
        observeLoading()
        observeSuccess()
        observeFailed()
        observeNoConnection()
        observeHideLoading()

    }

    private fun observeHideLoading() {
        baseActivityViewModel.observeHideLoading.observe(this, EventObserver {
            hideProgress()
        })
    }

    private fun observeNoConnection() {
        baseActivityViewModel.observeNoConnection.observe(this, EventObserver {
            hideProgress()
            noConnection()
        })
    }

    private fun observeFailed() {
        baseActivityViewModel.observeFailed.observe(this, EventObserver { errorMessage ->
            hideProgress()
            failedMessage(errorMessage)
        })
    }

    private fun observeSuccess() {
        baseActivityViewModel.observeSuccess.observe(this, EventObserver { successMessage ->
            hideProgress()
            successMessage(successMessage)
        })
    }

    private fun observeLoading() {
        baseActivityViewModel.observeLoading.observe(this, EventObserver {
            Log.e("324324324", "observeLoading: ")
            showProgress()
        })
    }


    open fun observeResponse() {
        responseManager.observeResponseManager.observe(this, EventObserver
        { responseResource ->
            try {
                baseActivityViewModel.getResponseState(responseResource)
            } catch (ex: NullPointerException) {
            }
        })
    }

    //Snack bar :
    private fun successMessage(message: String?) {
        val successSheet = SuccessDialog()
        val bundle = Bundle()
        bundle.putString(Constants.MESSAGE, message)
        successSheet.arguments = bundle

        successSheet.show(supportFragmentManager, Constants.SUCCESS_SHEET)
    }

    private fun failedMessage(message: String?) {
        val errorSheet = ErrorSheet()
        val bundle = Bundle()
        bundle.putString(Constants.MESSAGE, message)
        errorSheet.arguments = bundle

        errorSheet.show(supportFragmentManager, Constants.ERROR_SHEET)
    }

    private fun noConnection() {
        NoConnectionSheet().show(supportFragmentManager, Constants.NO_CONNECTION_SHEET)
    }

    @SuppressLint("InflateParams")
    private fun initializeProgress() {
        loadingBar = Dialog(this, R.style.Theme_AppCompat_DayNight)
        loadingBar!!.setCancelable(false)
        loadingBar!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val inflater = LayoutInflater.from(this)
        val loadingView = inflater.inflate(R.layout.layout_loader, null)
        loadingBar!!.setContentView(loadingView)
        loadingBar!!.window!!.setBackgroundDrawable(
            ColorDrawable(Color.parseColor("#0effffff"))
        )
    }


    private fun showProgress() {
        if (loadingBar != null && !this.isFinishing) loadingBar!!.show()
    }

    private fun hideProgress() {
        if (loadingBar != null && loadingBar!!.isShowing && !this.isFinishing) loadingBar!!.dismiss()
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }



}