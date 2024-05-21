package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.State
import org.springframework.data.jpa.repository.JpaRepository

interface StateRepository: JpaRepository<State, Long >
