package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Load
import org.springframework.data.jpa.repository.JpaRepository

interface LoadRepository: JpaRepository<Load, Long>
