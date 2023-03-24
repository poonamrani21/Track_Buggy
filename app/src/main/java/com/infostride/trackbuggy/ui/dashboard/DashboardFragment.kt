package com.infostride.trackbuggy.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.infostride.trackbuggy.R
import com.infostride.trackbuggy.databinding.FragmentDashboardBinding
import com.infostride.trackbuggy.databinding.FragmentLoginBinding
import com.infostride.trackbuggy.domain.service.LocationService
import com.infostride.trackbuggy.utils.Helper
import com.infostride.trackbuggy.utils.logoutPopUpView
import com.infostride.trackbuggy.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    var mLocationService: LocationService = LocationService()
    lateinit var mServiceIntent: Intent
    private lateinit var  binding : FragmentDashboardBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDashboardBinding.bind(view)
        startService()
        binding.ivAvatar.setOnClickListener { findNavController().navigate(R.id.action_dashboardFragment_to_profileFragment) }
        binding.deactivateCode.setOnClickListener { logoutPopUpView(requireActivity()) {
            it.dismiss()
        }
        }
    }

    private fun startService() {
        mLocationService = LocationService()
        mServiceIntent = Intent(requireActivity(), mLocationService.javaClass)
        if (!Helper.isMyServiceRunning(mLocationService.javaClass, requireActivity())) {
            requireActivity().startService(mServiceIntent)
            requireActivity().showToast(getString(R.string.service_start_successfully))
        } else {
            requireActivity().showToast(getString(R.string.service_already_running))
        }
    }


}



