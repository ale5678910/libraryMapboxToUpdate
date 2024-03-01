package com.eopeter.fluttermapboxnavigation.utilities

import android.app.Activity
import android.view.ViewGroup
import android.widget.TextView
import com.mapbox.navigation.base.formatter.DistanceFormatterOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.internal.extensions.flowRouteProgress
import com.mapbox.navigation.core.lifecycle.MapboxNavigationObserver
import com.mapbox.navigation.dropin.R
import com.mapbox.navigation.dropin.internal.extensions.updateMargins
import com.mapbox.navigation.ui.base.lifecycle.UIBinder
import com.mapbox.navigation.ui.base.lifecycle.UIComponent
import com.mapbox.navigation.ui.tripprogress.api.MapboxTripProgressApi
import com.mapbox.navigation.ui.tripprogress.model.DistanceRemainingFormatter
import com.mapbox.navigation.ui.tripprogress.model.EstimatedTimeToArrivalFormatter
import com.mapbox.navigation.ui.tripprogress.model.TimeRemainingFormatter
import com.mapbox.navigation.ui.tripprogress.model.TripProgressUpdateFormatter
import com.mapbox.navigation.ui.tripprogress.view.MapboxTripProgressView

class CustomInfoPanelTripProgressBinder(val activity: Activity) : UIBinder {
    override fun bind(viewGroup: ViewGroup): MapboxNavigationObserver {

        val trip = MapboxTripProgressView(
            viewGroup.context,
            null,
            R.style.MapboxStyleTripProgressView
        )

        val distanceFormatterOptions =
            DistanceFormatterOptions.Builder(activity).build()

        val tripProgressFormatter = TripProgressUpdateFormatter
            .Builder(activity)
            .distanceRemainingFormatter(
                DistanceRemainingFormatter(distanceFormatterOptions)
            )
            .timeRemainingFormatter(
                TimeRemainingFormatter(activity)
            )
            .estimatedTimeToArrivalFormatter(
                EstimatedTimeToArrivalFormatter(activity)
            )
            .build()
        val tripProgressApi = MapboxTripProgressApi(tripProgressFormatter)

        /*coroutineScope.launch {
            mapboxNavigation.flowRouteProgress().collect {
                val value = tripProgressApi.getTripProgress(it)
                binding.distanceRemaining.setText(
                    value.formatter.getDistanceRemaining(value.distanceRemaining),
                    TextView.BufferType.SPANNABLE
                )

                binding.estimatedTimeToArrive.setText(
                    value.formatter.getEstimatedTimeToArrival(value.estimatedTimeToArrival),
                    TextView.BufferType.SPANNABLE
                )

                binding.timeRemaining.setText(
                    value.formatter.getTimeRemaining(value.currentLegTimeRemaining),
                    TextView.BufferType.SPANNABLE
                )
                binding.tripProgress.progress = (value.percentRouteTraveled * 100).toInt()
            }
        }*/

        //viewGroup.removeAllViews()
        viewGroup.addView(trip)
        trip.updateMargins(left = 400)
        return object : UIComponent() {
                override fun onAttached(mapboxNavigation: MapboxNavigation) {
                super.onAttached(mapboxNavigation)

            }
        }
    }

}