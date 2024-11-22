package moderna.home.deliverycontrolsystem.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import java.time.LocalDate
@Entity

@Table(name = "Carregamento")
data class Load(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val loadId: Long? = null,
    @Column(nullable = true, unique = true) var loadNumber: Long? = 0,
    @OneToMany(fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        mappedBy = "load")
    @JsonBackReference var  orders: List<Order> = mutableListOf(),
    @Column(nullable = false) var driver: String,
    @Column(nullable = false) var departureDate: LocalDate,

    )
