package com.shomazz.smartkiosk.presentation.menu

import android.view.View
import androidx.annotation.StringRes
import com.shomazz.smartkiosk.util.BaseView

interface MenuView : BaseView {

    fun onQrClick(v: View)

    fun onInputClick(v: View)

    fun onChangeLangClick(v: View)

    fun showLanguageDialog()

    fun showProgress(show: Boolean)

    fun onResult(code: String?)

    fun showError(message: String)

    fun showError(@StringRes resId: Int)

}