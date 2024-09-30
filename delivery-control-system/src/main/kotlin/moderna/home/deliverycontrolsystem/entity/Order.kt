package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate

@Entity

@Table(name = "Pedido")
data class Order
(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null,
    @Column(nullable = false, unique = true) var orderCode: Long?,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customer_id") var customer: Customer? = null,
    @Column(name = "customer_Code", nullable = false) var customerCode: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "load_id") var load: Load? = null,
    @Column(name = "load_Number",nullable = false) var loadNumber: Long?,
    @Column(nullable = false) @JoinColumn(name = "customer_name") var customerName: String = customer!!.name,
    @Enumerated(EnumType.STRING) var orderType: OrderType,
    @Enumerated(EnumType.STRING) var status: Status = Status.Default,
    @Column(nullable = false) var purchaseDate: LocalDate,
    @Column(nullable = true) var invoicingDate: LocalDate?,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "sellerId", nullable = true) var orderSeller: Sellers? = Sellers.defaultSeller,
    @Column(nullable = false,name = "sellers_Rca") var sellerRCA: Long? = null,
    @Column(nullable = false, name = "Seller_Name") var sellerName: String = orderSeller!!.sellersName,
    @Column(nullable = true) var daysUntilDelivery: Int = 0,
    @Enumerated(EnumType.STRING) var orderFutureDelState: FutureDlState = FutureDlState.DentroDoPrazo,

    )




















