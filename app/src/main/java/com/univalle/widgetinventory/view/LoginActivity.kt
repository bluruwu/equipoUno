package com.univalle.widgetinventory.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.univalle.widgetinventory.R
import com.univalle.widgetinventory.databinding.ActivityLoginBinding
import com.univalle.widgetinventory.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)
        initializeUI()
    }
    private fun initializeUI() {
        sesion()
        colorFields()
        setListeners()
        setupButtons()
    }
    private fun colorFields() {
        val whiteTint = ColorStateList.valueOf(Color.WHITE)
        with(binding) {
            tilEmail.setBoxStrokeColor(Color.WHITE)
            tilEmail.defaultHintTextColor = whiteTint
            tilPass.setBoxStrokeColor(Color.WHITE)
            tilPass.defaultHintTextColor = whiteTint
            tilPass.setEndIconTintList(whiteTint)
        }
    }

    private fun setListeners() {
        with(binding) {
            etPass.addTextChangedListener(createTextWatcher { handlePasswordTextChanged(it) })
            etEmail.addTextChangedListener(createTextWatcher { updateLoginButtonState() })
        }
    }
    private fun createTextWatcher(onTextChanged: (CharSequence?) -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged(s)
            }

            override fun afterTextChanged(s: Editable?) {}
        }
    }
    private fun handlePasswordTextChanged(s: CharSequence?) {
        val password = s.toString()
        with(binding.tilPass) {
            if ((password.length < 6) || (password.length > 10)) {
                error = "Mínimo 6 dígitos"
                setErrorStyle()
            } else {
                error = null
                boxStrokeColor = resources.getColor(R.color.white)
                updateLoginButtonState()
            }
        }
    }
    private fun setErrorStyle() {
        with(binding.tilPass) {
            val redColorStateList = ColorStateList.valueOf(Color.RED)
            boxStrokeErrorColor = redColorStateList
            setErrorTextColor(ColorStateList.valueOf(ContextCompat.getColor(this@LoginActivity, R.color.Red)))
            setErrorIconTintList(redColorStateList)
            updateLoginButtonState()
            buttontextcolor()
        }
    }
    private fun updateLoginButtonState() {
        with(binding) {
            val email = etEmail.text.toString().trim()
            val password = etPass.text.toString().trim()
            btnLogin.isEnabled = email.isNotEmpty() && password.isNotEmpty()
            tvRegister.isEnabled = email.isNotEmpty() && password.isNotEmpty()
            buttontextcolor()
        }
    }

    private fun buttontextcolor() {
        with(binding) {
            if (btnLogin.isEnabled) {
                btnLogin.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                tvRegister.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
            } else {
                btnLogin.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.grayH2C11))
                tvRegister.setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.grayH2C11))
            }
        }
    }
    private fun setupButtons() {
        with(binding) {
            tvRegister.setOnClickListener { registerUser() }
            btnLogin.setOnClickListener { loginUser() }
        }
    }
    private fun registerUser() {
        with(binding) {
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            loginViewModel.registerUser(email, pass) { isRegister ->
                if (isRegister) {
                    goToHome(email)
                } else {
                    showToast(R.string.registration_error)
                }
            }
        }
    }
    private fun loginUser() {
        with(binding) {
            val email = etEmail.text.toString()
            val pass = etPass.text.toString()
            loginViewModel.loginUser(email, pass) { isLogin ->
                if (isLogin) {
                    goToHome(email)
                } else {
                    showToast(R.string.login_error)
                }
            }
        }
    }
    private fun showToast(messageResId: Int) {
        Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
    }
    private fun sesion() {
        val email = sharedPreferences.getString("email", null)
        loginViewModel.sesion(email) { isEnableView ->
            if (isEnableView) {
                binding.clContenedor.visibility = View.INVISIBLE
                goToHome(email)
            }
        }
    }
    override fun onStart() {
        super.onStart()
        binding.clContenedor.visibility = View.VISIBLE
    }

    private fun goToHome(email: String?) {
        val intent = Intent (this, MainActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(intent)
        finish()
    }
}
