package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate

@Entity

@Table(name = "Pedido")
data class Order(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false, unique = true) var orderCode: Long?,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customerCode") var customer: Customer? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "loadNumber") val load: Load? = null,
    @Column(nullable = false) @JoinColumn(name = "name") val customerName: String = " ",
    @Enumerated(EnumType.STRING) val orderType: OrderType,
    @Enumerated(EnumType.STRING) val status: Status = Status.Default,
    @Column(nullable = false) val purchaseDate: LocalDate,
    @Column(nullable = false) val invoicingDate: LocalDate,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "sellerId") var orderSeller: Sellers? = null,
    @Column(nullable = false) var daysUntilDelivery: Int = 0,
    @Enumerated(EnumType.STRING) var orderFutureDelState: FutureDlState = FutureDlState.DentroDoPrazo,


    )




















