package com.shomazz.smartkiosk.presentation.auth

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.shomazz.smartkiosk.R
import com.shomazz.smartkiosk.util.BaseFragment
import kotlinx.android.synthetic.main.fragment_auth.*
import javax.inject.Inject


class AuthFragment : BaseFragment(), AuthView {

    @Inject
    override lateinit var presenter: AuthPresenter
    private lateinit var toast: Toast

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter.attach(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBtn.setOnClickListener(::onLoginClick)
    }

    override fun getLogin() = loginEditText.text.toString()

    override fun getPassword() = passwordEditText.text.toString()

    override fun onLoginClick(button: View) {
        presenter.onLoginClick()
    }

    override fun showPrinterIpDialog() {
        if (context != null) {
            val dialog = AlertDialog.Builder(context)
                .setView(R.layout.input_printer_ip_view)
                .create()
            with(dialog) {
                show()
                val button = findViewById<ImageButton>(R.id.inputPrinterIpBtn)
                val editText = findViewById<EditText>(R.id.inputPrinterIpEditText)
                button.setOnClickListener {
                    dismiss()
                    presenter.onInputPrinterIpClick(editText.text.toString())
                }
            }
        }
    }

    override fun onError(id: Int) {
        onError(getString(id))
    }

    override fun onError(msg: String) {
        if (::toast.isInitialized) {
            toast.setText(msg)
            toast.show()
        } else {
            toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
            toast.show()
        }
    }

    override fun showProgress(show: Boolean) {
        if (show) {
            activity?.window?.setFlags(FLAG_NOT_TOUCHABLE, FLAG_NOT_TOUCHABLE)
            authProgressBar.visibility = View.VISIBLE
        } else {
            authProgressBar.visibility = View.INVISIBLE
            activity?.window?.clearFlags(FLAG_NOT_TOUCHABLE)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AuthFragment()
    }
}
