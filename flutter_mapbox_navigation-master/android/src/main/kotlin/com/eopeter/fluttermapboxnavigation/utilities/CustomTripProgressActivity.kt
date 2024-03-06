package com.eopeter.fluttermapboxnavigation.utilities

import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.TransitionManager
import com.eopeter.fluttermapboxnavigation.R
import com.eopeter.fluttermapboxnavigation.databinding.MapboxActivityCustomizeTripProgressBinding
import com.eopeter.fluttermapboxnavigation.databinding.MapboxTripProgressCustomLayoutBinding
import com.eopeter.fluttermapboxnavigation.models.MapBoxEvents
import com.mapbox.navigation.base.ExperimentalPreviewMapboxNavigationAPI
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
import kotlinx.coroutines.launch

class CustomTripProgressActivity : AppCompatActivity() {

    private lateinit var binding: MapboxActivityCustomizeTripProgressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MapboxActivityCustomizeTripProgressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navigationView.api.routeReplayEnabled(true)

        binding.navigationView.customizeViewBinders {
            infoPanelTripProgressBinder = MyTripProgressViewBinder()
        }
    }
}

class MyTripProgressComponent(
    private val binding: MapboxTripProgressCustomLayoutBinding,
    private var testText: String = "1"
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
        binding.endNavButton.setOnClickListener {
            mapboxNavigation.stopTripSession()
            PluginUtilities.sendEvent(MapBoxEvents.NAVIGATION_CANCELLED)
        }
        binding.deliverButton.setOnClickListener {
            PluginUtilities.sendEvent(MapBoxEvents.DELIVER_BUTTON_TAP)
        }
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
            }
        }
        coroutineScope.launch {
            mapboxNavigation.flowRouteProgress().collect {
            }
        }
    }
}

class MyTripProgressViewBinder(
    private var testText: String = "2"
) : UIBinder {
    override fun bind(viewGroup: ViewGroup): MapboxNavigationObserver {
        val scene = Scene.getSceneForLayout(
            viewGroup,
            R.layout.mapbox_trip_progress_custom_layout,
            viewGroup.context
        )
        TransitionManager.go(scene, Fade())

        val binding = MapboxTripProgressCustomLayoutBinding.bind(viewGroup)
        return MyTripProgressComponent(binding,testText)
    }
}