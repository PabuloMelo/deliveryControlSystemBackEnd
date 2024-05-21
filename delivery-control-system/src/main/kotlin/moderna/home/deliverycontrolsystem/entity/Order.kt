package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate

@Entity

@Table(name = "Pedido")
data class Order(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val orderId: Long? = null,
    @Column(nullable = false, unique = true) var orderNumber: String = "",
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "customerCode") val customer: Customer? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "loadID") val load: Load? = null,
    @Enumerated(EnumType.STRING) var orderType: OrderType,
    @Enumerated(EnumType.STRING) var status: Status = Status.Default,
    @Column(nullable = false) var purchaseDate: LocalDate,
    @Column(nullable = false) var invoicingDate: LocalDate,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "sellerId") var orderSeller: Sellers? = null,

    )
