package com.manan.weatherapp.ui.forecast

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.manan.weatherapp.R
import com.manan.weatherapp.domain.model.CCWeatherDayForecast
import com.manan.weatherapp.domain.model.WeatherCondition
import java.time.format.DateTimeFormatter

@Composable
fun CCForecastScreen(
    m_state: CCForecastUiState,
    onRetry: () -> Unit,
    onOpenSettings: () -> Unit
) {
    when (m_state) {
        is CCForecastUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is CCForecastUiState.Success -> {
            CCForecastContent(m_items = m_state.m_items)
        }

        is CCForecastUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = m_state.m_message)
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onRetry) {
                        Text(text = "Retry")
                    }
                }
            }
        }

        is CCForecastUiState.NoLocationPermission -> {
            AlertDialog(
                onDismissRequest = {},
                title = {
                    Text(
                        text = "Location Permission Needed",
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "To show accurate forecast data, please enable location access in your settings."
                    )
                },
                confirmButton = {
                    TextButton(onClick = onOpenSettings) {
                        Text(text = "Open Settings")
                    }
                },
                dismissButton = {
                    TextButton(onClick = onRetry) {
                        Text(text = "Retry")
                    }
                }
            )
        }
    }
}

@Composable
private fun CCForecastContent(
    m_items: List<CCWeatherDayForecast>
) {
    val current = m_items.firstOrNull()?.m_condition ?: WeatherCondition.SUNNY

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = backgroundForCondition(current)),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            // Title: 18, Bold, line height 28
            Text(
                text = "5 Day Forecast",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 28.sp,
                letterSpacing = 0.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Divider(
                thickness = 1.dp,
                color = Color.White.copy(alpha = 0.12f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 22.dp)
            ) {
                items(m_items) { item ->
                    CCForecastDayCard(m_item = item)
                }
            }
        }
    }
}

@Composable
private fun CCForecastDayCard(
    m_item: CCWeatherDayForecast
) {
    val dayFormatter = DateTimeFormatter.ofPattern("EEEE")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                // Card title: 16, SemiBold, line height 24
                Text(
                    text = m_item.m_date.format(dayFormatter),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 24.sp,
                    letterSpacing = 0.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Image(
                    painter = painterResource(id = iconForCondition(m_item.m_condition)),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp)
                )
            }

            // Temp: 36, Bold, line height 44
            Text(
                text = "${m_item.m_temperatureCelsius}°",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 44.sp,
                letterSpacing = 0.sp
            )
        }
    }
}

@DrawableRes
private fun backgroundForCondition(condition: WeatherCondition): Int {
    return when (condition) {
        WeatherCondition.SUNNY -> R.drawable.bg_sunny
        WeatherCondition.CLOUDY -> R.drawable.bg_cloudy
        WeatherCondition.RAINY -> R.drawable.bg_rainy
        WeatherCondition.FOREST -> R.drawable.bg_forest
    }
}

@DrawableRes
private fun iconForCondition(condition: WeatherCondition): Int {
    return when (condition) {
        WeatherCondition.SUNNY -> R.drawable.ic_sun_light
        WeatherCondition.CLOUDY -> R.drawable.ic_mostly_cloudy_light
        WeatherCondition.RAINY -> R.drawable.ic_rainyday_light
        WeatherCondition.FOREST -> R.drawable.ic_partial_cloudy_light
    }
}
