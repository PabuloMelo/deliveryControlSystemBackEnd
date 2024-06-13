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
    @Column(nullable = false, unique = true) var orderCode: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customerCode") var customer: Customer? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "loadNumber") val load: Load? = null,
    @Column(nullable = false) @JoinColumn(name = "name") var customerName: String = " ",
    @Enumerated(EnumType.STRING) var orderType: OrderType,
    @Enumerated(EnumType.STRING) var status: Status = Status.Default,
    @Column(nullable = false) var purchaseDate: LocalDate,
    @Column(nullable = false) var invoicingDate: LocalDate,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "sellerId") var orderSeller: Sellers? = null,
    @Column(nullable = false) var daysUntilDelivery: Int = 0,
    @Enumerated(EnumType.STRING) var orderFutureDelState: FutureDlState = FutureDlState.DentroDoPrazo,


    )




















