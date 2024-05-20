package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import java.util.*

@Entity
@Table(name = "Entrega Futura")
data class FutureDelivery(

    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: UUID = UUID.randomUUID(),
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_id") var customer: Customer? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "order_id") var order: Order? = null,
    @Enumerated(EnumType.STRING) var orderState: FutureDlState = FutureDlState.DentroDoPrazo,
    @Column(nullable = false) var daysUntilDelivery: Int = 0

)
