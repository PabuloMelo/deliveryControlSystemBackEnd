package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.FutureDelivery
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FuturyDeliveryRepository: JpaRepository<FutureDelivery,UUID>
