package com.infostride.trackbuggy.ui.track_device

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.infostride.trackbuggy.R
import com.infostride.trackbuggy.databinding.FragmentTrackBinding
import com.infostride.trackbuggy.utils.launchActivity
import com.infostride.trackbuggy.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrackFragment : Fragment(R.layout.fragment_track) {

private lateinit var binding:FragmentTrackBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentTrackBinding.bind(view)
        binding.editProfileBackBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.tvTrackDevice.setOnClickListener {
            requireActivity().showToast("Coming soon")
         //  requireActivity().launchActivity<MapActivity>()
        }
    }


}