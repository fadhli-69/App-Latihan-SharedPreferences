package com.example.mysharedpreferences

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mysharedpreferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mUserPreference: UserPreference
    private lateinit var binding: ActivityMainBinding

    private var isPreferenceEmpty =  false
    private lateinit var userModel: UserModel

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == FormUserPreferenceActivity.RESULT_CODE) {

            result.data?.let { data ->
                userModel = data.getUserModelFromResult() ?: UserModel()

                populateView(userModel)
                checkForm(userModel)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.btnSave.setOnClickListener(this)

        setContentView(binding.root)

        supportActionBar?.title = "My User Preferences"

        mUserPreference = UserPreference(this)

        showExistingPreferences()
    }

    private fun showExistingPreferences() {
        userModel = mUserPreference.getUser()
        populateView(userModel)
        checkForm(userModel)
    }
    private fun populateView(userModel: UserModel) {
        binding.tvName.text = userModel.name.orEmpty().ifEmpty { "Tidak ada" }
        binding.tvAge.text = if (userModel.age == 0) "Tidak Ada" else userModel.age.toString()
        binding.tvIsWibu.text = if (userModel.isLove) "Ya" else "Tidak"
        binding.tvEmail.text = userModel.email.orEmpty().ifEmpty { "Tidak Ada" }
        binding.tvPhone.text = userModel.phoneNumber.orEmpty().ifEmpty { "Tidak Ada" }
    }
    private fun checkForm(userModel: UserModel) {
        when {
            userModel.name.toString().isNotEmpty() -> {
                binding.btnSave.text = getString(R.string.change)
                isPreferenceEmpty = false
            }
            else -> {
                binding.btnSave.text = getString(R.string.save)
                isPreferenceEmpty = true
            }
        }
    }

    override fun onClick(view: View) {
        if (view.id ==R.id.btn_save) {
            val intent  = Intent(this, FormUserPreferenceActivity::class.java)
            when {
                isPreferenceEmpty -> {
                    intent.putExtra(
                    FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                    FormUserPreferenceActivity.TYPE_ADD
                    )
                intent.putExtra("User", userModel)
            }
            else -> {
                intent.putExtra(
                    FormUserPreferenceActivity.EXTRA_TYPE_FORM,
                    FormUserPreferenceActivity.TYPE_EDIT
                )
                intent.putExtra("USER", userModel)
            }
        }
        resultLauncher.launch(intent)
        }
    }
}