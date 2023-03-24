package com.infostride.trackbuggy.ui.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.infostride.trackbuggy.R
import com.infostride.trackbuggy.databinding.FragmentLoginBinding
import com.infostride.trackbuggy.ui.HolderActivity
import com.infostride.trackbuggy.ui.viewmodel.LoginViewModel
import com.infostride.trackbuggy.utils.SharedPreference
import com.infostride.trackbuggy.utils.Utils.validateLoginRequest
import com.infostride.trackbuggy.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {
    @Inject
    lateinit var loginViewModel: LoginViewModel
    private lateinit var  binding : FragmentLoginBinding
    @Inject
    lateinit var prefManager: SharedPreference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)
        binding.loginUsername.setText("sushil@infostride.com")
        binding.loginPassword.setText("123456")

        binding.loginButton.setOnClickListener {

            val userEmail = binding.loginUsername.editableText.toString()
            val password = binding.loginPassword.editableText.toString()
            val result = validateLoginRequest(userEmail, password)

            if (result.successful){
                binding.loginProgress.visibility = View.VISIBLE
                binding.loginButton.isEnabled = false
                loginViewModel.loginUser(email = userEmail,password= password, latitude = HolderActivity.userLatitude, longitude = HolderActivity.userLongitude, device_token = HolderActivity.device_token)
                loginViewModel.successful.observe(viewLifecycleOwner){successful ->
                    if (successful?.status == true){
                        //Data store in shared Pref
                        prefManager.saveUserData(successful.data)
                        binding.loginProgress.visibility = View.INVISIBLE
                        binding.loginButton.isEnabled = true
                        findNavController().navigate(R.id.action_loginFragment_to_dashboardFragment)
                        loginViewModel.navigated()
                    }else if(successful?.status==false){
                        binding.loginProgress.visibility = View.INVISIBLE
                        binding.loginButton.isEnabled = true
                        binding.root.showSnackbar(view = binding.root, "${loginViewModel.error.value}", Snackbar.LENGTH_SHORT, getString(R.string.ok)) {}
                        loginViewModel.navigated()
                    }
                }
            }
            else{
                binding.root.showSnackbar(view = binding.root, "${result.error}", Snackbar.LENGTH_SHORT, getString(R.string.ok)) {}
            }


        }
        binding.tvForgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

}