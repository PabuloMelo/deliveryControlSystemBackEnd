package moderna.home.deliverycontrolsystem.repository

import moderna.home.deliverycontrolsystem.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository

interface CustomerRepository: JpaRepository<Customer, String >
