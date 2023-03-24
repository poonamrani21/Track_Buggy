package com.infostride.trackbuggy.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.infostride.trackbuggy.R
import com.infostride.trackbuggy.data.model.login.LoginResponse
import com.infostride.trackbuggy.databinding.FragmentProfileBinding
import com.infostride.trackbuggy.ui.viewmodel.ProfileViewModel
import com.infostride.trackbuggy.utils.Resource
import com.infostride.trackbuggy.utils.SharedPreference
import com.infostride.trackbuggy.utils.logoutPopUpView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    @Inject
    lateinit var viewModel : ProfileViewModel
    @Inject
    lateinit var prefManager: SharedPreference
    lateinit var userLoginData: LoginResponse.Data
    private lateinit var binding : FragmentProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        userLoginData = prefManager.getUserData()!!
        binding.profileName.text = "${userLoginData.first_name} ${userLoginData.last_name}"
        binding.profileEmail.text = "${userLoginData.email}"
        //  viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner){result ->
            when(result){
                is Resource.Loading -> {
                    Log.i("ProfileFragment","Loading...")
                }
                is Resource.Success ->{
                   val user = result.data
                    binding.profileName.text = "${result.data?.data?.first_name} ${result.data?.data?.last_name}"
                    binding.profileEmail.text = "${result.data?.data?.email}"
                }
                is Resource.Error -> {
                    Log.i("ProfileFragment","Error ${result.message}")
                }
            }
        }


        binding.tvLogout.setOnClickListener {
            logoutPopUpView(requireActivity()) {
                viewModel.logoutUser()
                it.dismiss()
                findNavController().navigate(R.id.action_profileFragment_to_splashFragment)
            }
        }
        binding.tvProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.tvDashboard.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_dashboardFragment)
        }
        binding.profileBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvTrackDevice.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_trackDeviceFragment)
        }
    }
}