package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import java.time.LocalDate
@Entity

@Table(name = "Carregamento")
data class Load(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val loadId: Long? = null,
    @Column(nullable = true) var loadNumber: Long? = null,
    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        mappedBy = "load")
    var orders: List<Order> = mutableListOf(),
    @Column(nullable = false) var driver: String,
    @Column(nullable = false) var departureDate: LocalDate,

    )
