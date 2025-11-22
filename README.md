# Simplified Weather App (Android · Kotlin · Jetpack Compose)

This project is my implementation of the **DVT Simplified Weather App Assessment**.  
It displays a **5-day weather forecast** based on the user's current location, using clean architecture, modern Android development practices, and custom UI.

---

## 1. Overview

The app includes:

- Kotlin + Jetpack Compose UI  
- MVVM + StateFlow  
- Clean Architecture (Data → Domain → UI)  
- Retrofit + Moshi + OkHttp  
- Location Services API  
- Fully custom UI components (no third-party UI libraries)  
- Weather-based backgrounds & icons  
- Permission handling with fallback & settings dialog  

---

## 2. Architecture

The project follows a clean and modular structure:

### **Data Layer**
- Retrofit API service (`CCWeatherApi`)
- DTO models representing API responses
- Repository implementation (`CCWeatherRepositoryImpl`)
- Weather condition mapping
- Grouping & averaging 3-hour forecast data

### **Domain Layer**
- Pure Kotlin models (`CCWeatherDayForecast`, `WeatherCondition`)
- Repository interface (`CCWeatherRepository`)
- Business logic in a use case (`CCGetFiveDayForecastUseCase`)

### **UI Layer**
- Jetpack Compose screens & components
- `CCForecastViewModel` exposing `StateFlow<CCForecastUiState>`
- Screens reacting to:
  - **Loading**
  - **Success**
  - **Error**
  - **NoLocationPermission**

---

## 3. Package Structure
com.manan.weatherapp
│
├── data
│ ├── remote/
│ ├── repository/
│
├── domain
│ ├── model/
│ ├── repository/
│ └── usercases/
│
├── ui
│ ├── forecast/
│ └── theme/
│
└── MainActivity.kt


---

## 4. UI Features

### **Dynamic Backgrounds Per Weather Condition**
Background changes automatically depending on:

| Condition | Background |
|----------|------------|
| Sunny | `bg_sunny.png` |
| Cloudy | `bg_cloudy.png` |
| Rainy | `bg_rainy.png` |
| Forest / Default | `bg_forest.png` |

### **Forecast Day Card**
Each card shows:

- Day name  
- Weather icon  
- Temperature  
- Custom spacing, consistent with design  
- Fully Compose-based  

---

## 5. Weather Mapping

OpenWeather API → Domain → UI icon/background

clear → SUNNY
rain/drizzle/thunderstorm → RAINY
else → CLOUDY


Forecast is processed by:

- Grouping by LocalDate  
- Averaging temps of each 3-hour entry  
- Picking first available weather condition  

---

## 6. Permission Handling

If the user denies location:

- The app emits `NoLocationPermission` state
- UI shows an **AlertDialog**
  - **Open Settings** button
  - **Retry** button  
- If no location is available, fallback location = Dubai

---

## 7. Dependencies (All Allowed by Assessment)

| Dependency | Purpose |
|-----------|---------|
| Retrofit | HTTP calls |
| Moshi | JSON parsing |
| OkHttp Logging | Network logs |
| Coroutines | Async + ViewModel |
| Coroutines Test | Unit testing |
| Play Services Location | Location API |
| AndroidX + Compose | UI |
| Desugar JDK libs | java.time support |

No UI libraries or forbidden dependencies are used.

---

## 8. Build Instructions

### **Requirements**
- Android Studio Ladybug or newer
- JDK 17
- Internet connection
- OpenWeather API key

### **Add your API key**

In `local.properties`:


### **Run the app**

1. Open project in Android Studio  
2. Sync Gradle  
3. Run on device or emulator  

---

## 9. Running Unit Tests

Unit tests are located in:


### They test:

✔ Successful forecast loading  
✔ Error handling  
✔ No-location-permission state  

### Run tests using:

Android Studio:

