package com.example.cit

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TableAdapter(fragment: TableFragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                LayersFragment()
            }
            1 -> {
                SubstratesFragment()
            }
            else -> {
                MissionFragment()
            }
        }
    }
}