package com.eopeter.fluttermapboxnavigation.utilities

import android.app.Activity
import android.view.ViewGroup
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.lifecycle.MapboxNavigationObserver
import com.mapbox.navigation.dropin.R
import com.mapbox.navigation.dropin.internal.extensions.updateMargins
import com.mapbox.navigation.ui.base.lifecycle.UIBinder
import com.mapbox.navigation.ui.base.lifecycle.UIComponent
import com.mapbox.navigation.ui.tripprogress.view.MapboxTripProgressView

class Testt(val activity: Activity) : UIBinder {
    override fun bind(viewGroup: ViewGroup): MapboxNavigationObserver {

        val trip = MapboxTripProgressView(
            viewGroup.context,
            null,
            R.style.MapboxStyleTripProgressView
        )
        viewGroup.removeAllViews()
        viewGroup.addView(trip)
        trip.updateMargins(left = 40)
        return object : UIComponent() {
            override fun onAttached(mapboxNavigation: MapboxNavigation) {
                super.onAttached(mapboxNavigation)
            }
        }
    }

}