package com.example.cit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cit.databinding.MissionAddFragmentBinding

class MissionAddFragment : Fragment() {

    private lateinit var binding: MissionAddFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MissionAddFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = MissionAddFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivBack.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.bTower.setOnClickListener {
            openFragment(TowerFragment.newInstance(), "TowerFragment")
        }

        binding.bFlight.setOnClickListener {
            openFragment(FlightFragment.newInstance(), "FlightFragment")
        }

        binding.bShooting.setOnClickListener {
            openFragment(ShootingFragment.newInstance(), "ShootingFragment")
        }
    }

    private fun openFragment(fragment: Fragment, tag: String) {
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragmentContainerViewMain, fragment, tag)
            ?.addToBackStack(null)
            ?.commit()
    }
}