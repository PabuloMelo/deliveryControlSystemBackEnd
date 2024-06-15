package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.CustomerType
import moderna.home.deliverycontrolsystem.enumerators.Register
import java.util.UUID

@Entity

@Table(name = "Cliente")

data class Customer(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null,
    @Column(nullable = false) var name: String = " ",
    @Column(nullable = true) var phone: Long? = null,
    @Column(nullable = false) var customerCode: Long?,
    @Enumerated(EnumType.STRING) var customerType: CustomerType = CustomerType.Normal,
    @Enumerated(EnumType.STRING) var customerRegistered: Register = Register.Default,
    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = arrayOf(CascadeType.ALL),
        mappedBy = "customer"
    ) var orders: List<Order> = mutableListOf(),
)


