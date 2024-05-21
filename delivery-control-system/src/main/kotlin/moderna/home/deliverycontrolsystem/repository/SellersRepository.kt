package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Sellers
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface SellersRepository: JpaRepository<Sellers,UUID>
