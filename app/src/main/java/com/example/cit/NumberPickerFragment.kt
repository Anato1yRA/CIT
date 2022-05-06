package com.example.cit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import com.example.cit.databinding.NumberPickerFragmentBinding

class NumberPickerFragment(private val fc: FragmentContainerView, private val item: TextView) : Fragment() {

    private lateinit var binding: NumberPickerFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = NumberPickerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(fc: FragmentContainerView, item: TextView) = NumberPickerFragment(fc, item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val itemVal = item.text.toString().toInt()

        binding.etValue.setText("$itemVal")

        val np = binding.numberPicker

        np.minValue = 1
        np.maxValue = itemVal + 1000

        np.value = itemVal

        np.setOnValueChangedListener { _, _, _ ->
            binding.etValue.setText("${np.value}")
        }

        binding.ivSetCancel.setOnClickListener {
            fc.visibility = View.GONE
        }
        binding.ivSetOk.setOnClickListener {
            item.text = "${np.value}"
            fc.visibility = View.GONE
        }
    }
}