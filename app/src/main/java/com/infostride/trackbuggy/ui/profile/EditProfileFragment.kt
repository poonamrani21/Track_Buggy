package com.infostride.trackbuggy.ui.profile

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.infostride.trackbuggy.R
import com.infostride.trackbuggy.databinding.FragmentEditProfileBinding
import com.infostride.trackbuggy.databinding.FragmentProfileBinding
import com.infostride.trackbuggy.ui.viewmodel.ProfileViewModel
import com.infostride.trackbuggy.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    @Inject
    lateinit var viewModel : ProfileViewModel

    private lateinit var binding : FragmentEditProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentEditProfileBinding.bind(view)
        binding.editProfileBack.setOnClickListener { findNavController().navigateUp() }
        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner){result ->
            when(result){
                is Resource.Loading -> {
                    Log.i("ProfileFragment","Loading...")
                }
                is Resource.Success ->{
                    val user = result.data
                  /*  binding.editProfileFirstName.text = "${result.data?.data?.first_name} ${result.data?.data?.last_name}"
                    binding.editProfileLastName.text = "${result.data?.data?.email}"
                    binding.editProfileEmailAddress.text = "${result.data?.data?.email}"
                    binding.editProfilePhoneNumber.text = "${result.data?.data?.email}"
                    binding.editProfileAddress.text = "${result.data?.data?.email}"*/
                }
                is Resource.Error -> {
                    Log.i("ProfileFragment","Error ${result.message}")
                }
            }
        }    }

}