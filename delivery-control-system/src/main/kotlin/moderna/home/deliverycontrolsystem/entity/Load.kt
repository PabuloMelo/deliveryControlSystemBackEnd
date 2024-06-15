package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.Driver
import java.time.LocalDate
@Entity

@Table(name = "Carregamento")
data class Load(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val loadID: Long? = null,
    @Column(nullable = true) var loadNumber: Long?,
    @OneToMany(mappedBy = "load", cascade = [CascadeType.ALL], fetch = FetchType.LAZY) var orders: List<Order> = mutableListOf(),
    @Enumerated var driver: Driver = Driver.Default,
    @Column(nullable = false) var departureDate: String = " ",

    )
