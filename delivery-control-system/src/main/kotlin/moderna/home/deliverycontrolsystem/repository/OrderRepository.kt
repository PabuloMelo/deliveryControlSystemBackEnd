package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository: JpaRepository<Order, Long>
