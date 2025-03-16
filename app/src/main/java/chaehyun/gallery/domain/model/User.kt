package chaehyun.gallery.domain.model

data class User(
    val id: Long = 1L,
    val name: String = "당근조아",
    val temperature: Double = 99.9
) {
    init {
        require(temperature in 0.0..100.0) { INVALID_TEMPERATURE }
    }

    fun getTemperatureString(): String = "$temperature℃"

    companion object {
        const val INVALID_TEMPERATURE = "온도는 0.0 와 100.0℃ 사이입니다."
    }
}
