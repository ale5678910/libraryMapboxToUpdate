package com.eopeter.fluttermapboxnavigation.utilities

import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.mapbox.navigation.base.formatter.DistanceFormatterOptions
import com.mapbox.navigation.core.MapboxNavigation
import com.mapbox.navigation.core.internal.extensions.flowRouteProgress
import com.mapbox.navigation.core.lifecycle.MapboxNavigationObserver
import com.mapbox.navigation.ui.base.lifecycle.UIBinder
import com.mapbox.navigation.ui.base.lifecycle.UIComponent
import com.mapbox.navigation.ui.tripprogress.api.MapboxTripProgressApi
import com.mapbox.navigation.ui.tripprogress.model.DistanceRemainingFormatter
import com.mapbox.navigation.ui.tripprogress.model.EstimatedTimeToArrivalFormatter
import com.mapbox.navigation.ui.tripprogress.model.TimeRemainingFormatter
import com.mapbox.navigation.ui.tripprogress.model.TripProgressUpdateFormatter

/*class CustomTripProgress(
    private val binding: MapboxTripProgressCustomLayoutBinding
) : UIComponent() {
    override fun onAttached(mapboxNavigation: MapboxNavigation) {
        super.onAttached(mapboxNavigation)
        val distanceFormatterOptions =
            DistanceFormatterOptions.Builder(binding.root.context).build()
        val tripProgressFormatter = TripProgressUpdateFormatter
            .Builder(binding.root.context)
            .distanceRemainingFormatter(
                DistanceRemainingFormatter(distanceFormatterOptions)
            )
            .timeRemainingFormatter(
                TimeRemainingFormatter(binding.root.context)
            )
            .estimatedTimeToArrivalFormatter(
                EstimatedTimeToArrivalFormatter(binding.root.context)
            )
            .build()
        val tripProgressApi = MapboxTripProgressApi(tripProgressFormatter)
        coroutineScope.launch {
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
        }
        coroutineScope.launch {
            mapboxNavigation.flowRouteProgress().collect {
            }
        }
    }
}

class MyTripProgressViewBinder : UIBinder {
    override fun bind(viewGroup: ViewGroup): MapboxNavigationObserver {
        val scene = Scene.getSceneForLayout(
            viewGroup,
            R.layout.mapbox_trip_progress_custom_layout,
            viewGroup.context
        )
        TransitionManager.go(scene, Fade())

        val binding = MapboxTripProgressCustomLayoutBinding.bind(viewGroup)
        return CustomTripProgress(binding)
    }
}*/