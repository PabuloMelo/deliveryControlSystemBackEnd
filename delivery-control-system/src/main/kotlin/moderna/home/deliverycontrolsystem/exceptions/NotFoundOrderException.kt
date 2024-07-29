package moderna.home.deliverycontrolsystem.exceptions

data class NotFoundOrderException(override val message: String?): RuntimeException(message)
