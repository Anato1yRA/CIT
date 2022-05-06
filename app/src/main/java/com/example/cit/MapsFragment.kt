package com.example.cit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.maps_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Центр Москвы
        var marker = LatLng(55.7540229, 37.6192309)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 16.0f))

        map.setOnMapClickListener {
            marker = LatLng(it.latitude, it.longitude)

//            map.addMarker(MarkerOptions().position(marker).title("Новый маркер"))
//            map.moveCamera(CameraUpdateFactory.newLatLng(marker))

            val frag = parentFragmentManager.findFragmentByTag("TowerFragment")
            if (frag != null) {
                (frag as TowerFragment).setMarker(marker)
            }
        }
    }
}