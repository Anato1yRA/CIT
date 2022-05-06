package com.example.cit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import com.example.cit.databinding.TowerFragmentBinding
import com.google.android.gms.maps.model.LatLng

class TowerFragment : Fragment() {

    private lateinit var binding: TowerFragmentBinding

    private var coordinate: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TowerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance() = TowerFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivBack2.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.ivCancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.tvCancel.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        binding.clRadius.setOnClickListener {
            openFragment(
                NumberPickerFragment.newInstance(
                    binding.fcNumberPicker,
                    binding.tvRadius
                ),
                R.id.clRadius
            )
        }
        binding.clHeight.setOnClickListener {
            openFragment(
                NumberPickerFragment.newInstance(
                    binding.fcNumberPicker,
                    binding.tvHeight
                ),
                R.id.clHeight
            )
        }
        binding.clPoints.setOnClickListener {
            openFragment(
                NumberPickerFragment.newInstance(
                    binding.fcNumberPicker,
                    binding.tvPoints
                ),
                R.id.clPoints
            )
        }
        binding.clTiltAngle.setOnClickListener {
            openFragment(
                NumberPickerFragment.newInstance(
                    binding.fcNumberPicker,
                    binding.tvTiltAngle
                ), R.id.clTiltAngle
            )
        }
        binding.clSpeed.setOnClickListener {
            openFragment(
                NumberPickerFragment.newInstance(
                    binding.fcNumberPicker,
                    binding.tvSpeed
                ),
                R.id.clSpeed
            )
        }
        binding.clRounding.setOnClickListener {
            openFragment(
                NumberPickerFragment.newInstance(
                    binding.fcNumberPicker,
                    binding.tvRounding
                ), R.id.clRounding
            )
        }

        binding.ivSave.setOnClickListener {
            if (coordinate == null) {
                toast("Укажите центр миссии")
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        }

        binding.tvSave.setOnClickListener {
            if (coordinate == null) {
                toast("Укажите центр миссии")
            } else {
                activity?.supportFragmentManager?.popBackStack()
            }
        }
    }

    private fun openFragment(fragment: Fragment, idFrom: Int) {
        binding.fcNumberPicker.visibility = View.GONE

        val cl = binding.clTowerFragment
        val set = ConstraintSet()
        set.clone(cl)
        set.connect(R.id.fcNumberPicker, ConstraintSet.TOP, idFrom, ConstraintSet.TOP)
        set.connect(R.id.fcNumberPicker, ConstraintSet.BOTTOM, idFrom, ConstraintSet.BOTTOM)
        set.connect(R.id.fcNumberPicker, ConstraintSet.START, idFrom, ConstraintSet.START)
        set.applyTo(cl)

        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fcNumberPicker, fragment)
            ?.commit()

        binding.fcNumberPicker.visibility = View.VISIBLE
    }

    private fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun setMarker(marker: LatLng){
        coordinate = marker

        toast("Центр миссии задан")
    }
}