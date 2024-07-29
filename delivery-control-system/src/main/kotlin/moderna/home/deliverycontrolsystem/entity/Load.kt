package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.Driver
import java.time.LocalDate
@Entity

@Table(name = "Carregamento")
data class Load(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val loadId: Long? = null,
    @Column(nullable = true) var loadNumber: Long? = null,
    @OneToMany(fetch = FetchType.LAZY,
        cascade = arrayOf(CascadeType.ALL),
        mappedBy = "load")
    var orders: List<Order> = mutableListOf(),
    @Enumerated(EnumType.STRING) var driver: Driver = Driver.Default,
    @Column(nullable = false) var departureDate: LocalDate,

    )
