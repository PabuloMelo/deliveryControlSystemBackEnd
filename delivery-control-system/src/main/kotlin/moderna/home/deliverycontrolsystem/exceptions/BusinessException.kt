package moderna.home.deliverycontrolsystem.exceptions

data class BusinessException(override val message: String?): RuntimeException(message)
