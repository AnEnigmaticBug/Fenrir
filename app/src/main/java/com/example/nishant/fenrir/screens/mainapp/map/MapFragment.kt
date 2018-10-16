package com.example.nishant.fenrir.screens.mainapp.map

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nishant.fenrir.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var rootPOV: View
    private lateinit var map: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootPOV = inflater.inflate(R.layout.fra_map, container, false)

        (childFragmentManager.findFragmentById(R.id.mapFRA) as SupportMapFragment).getMapAsync(this)

        return rootPOV
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if(googleMap != null) {
            map = googleMap
            googleMap.clear()

            when(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                PackageManager.PERMISSION_GRANTED -> googleMap.isMyLocationEnabled = true
                PackageManager.PERMISSION_DENIED  -> requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 69)
            }

            val locationMarkers = mapOf(
                    "Rotunda" to LatLng(28.3633546, 75.5871163)
            )

            locationMarkers.forEach {
                googleMap.addMarker(MarkerOptions().position(it.value).title(it.key)).showInfoWindow()
            }

            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationMarkers["Rotunda"], 17.0f))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == 69) {
            if(permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    map.isMyLocationEnabled = true
                }
                catch(e: SecurityException) {
                    throw IllegalStateException("What the actual fu" +
                            "ck Android?")
                }
            }
        }
    }
}