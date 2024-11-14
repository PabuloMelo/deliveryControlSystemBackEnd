package moderna.home.deliverycontrolsystem.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import java.time.LocalDate

@Entity

@Table(name = "Pedido")
data class Order
    (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)

    var id: Long? = null,

    @Column(nullable = false, unique = true)

    var orderCode: Long?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonManagedReference
    var customer: Customer? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "load_id")
    @JsonManagedReference
    var load: Load? = null,

    @Enumerated(EnumType.STRING)
    var orderType: OrderType,

    @Enumerated(EnumType.STRING)
    var status: Status = Status.DEFAULT,

    @Column(nullable = false)
    var purchaseDate: LocalDate,

    @Column(nullable = true)
    var invoicingDate: LocalDate?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "sellerId",
        nullable = true
    )
    @JsonManagedReference
    var orderSeller: Sellers? = Sellers.defaultSeller,

    @Column(nullable = false, name = "order_RCA")
    var orderRCA: Long? = 0,



    @Column(nullable = true)
    var daysUntilDelivery: Int = 0,

    @Enumerated(EnumType.STRING)
    var orderFutureDelState: FutureDlState = FutureDlState.DentroDoPrazo,

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonBackReference
    var state: State? = null,


    @Column(nullable = false, name = "order_address")
    var orderAddress: String

    )




















