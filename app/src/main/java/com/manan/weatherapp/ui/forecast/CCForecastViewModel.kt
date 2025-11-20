import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manan.weatherapp.domain.usercases.CCGetFiveDayForecastUseCase
import com.manan.weatherapp.ui.forecast.CCForecastUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CCForecastViewModel(
    private val m_getForecastUseCase: CCGetFiveDayForecastUseCase
) : ViewModel() {

    private val _m_state = MutableStateFlow<CCForecastUiState>(CCForecastUiState.Loading)
    val m_state: StateFlow<CCForecastUiState> = _m_state

    fun loadForecast(lat: Double, lon: Double) {
        _m_state.value = CCForecastUiState.Loading

        viewModelScope.launch {
            try {
                val items = m_getForecastUseCase(lat, lon)
                _m_state.value = CCForecastUiState.Success(items)
            } catch (ex: Exception) {
                _m_state.value = CCForecastUiState.Error(
                    ex.message ?: "Something went wrong"
                )
            }
        }
    }

    fun setNoLocationPermission() {
        _m_state.value = CCForecastUiState.NoLocationPermission
    }
}
