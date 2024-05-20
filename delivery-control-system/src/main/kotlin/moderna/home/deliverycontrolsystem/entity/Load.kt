package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.Driver
import java.time.LocalDate
@Entity

@Table(name = "Carregamento")
data class Load(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val loadID: Long = 0,
    @Column(nullable = true) var loadNumber: String = " ",
    @OneToMany(mappedBy = "load", cascade = [CascadeType.ALL], fetch = FetchType.LAZY) var orders: List<Order> = mutableListOf(),
    @Enumerated var driver: Driver,
    @Column(nullable = false) var departureDate: LocalDate,

    )
