package com.example.cit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.cit.databinding.MissionFragmentBinding
import com.example.cit.databinding.TableFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TableFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.table_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = TableAdapter(this)

        val viewPager: ViewPager2 = view.findViewById(R.id.vp2)
        viewPager.adapter = adapter

        val tabNames: Array<String> = arrayOf("Слои", "Подложки", "Миссии")

        val tabLayout: TabLayout = view.findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }
}