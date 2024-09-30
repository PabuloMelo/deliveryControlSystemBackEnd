package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.*
import java.time.LocalDate

@Entity
@Table(name = "Situacao")
data class State (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val stateId: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "Id", referencedColumnName = "Id") var order: Order? = null,
    @Column(nullable = false) var orderNumber: Long,
    @Column(nullable = false) @JoinColumn(name = "customerName") var customerName: String  = " ",
    @Column(nullable = false) @JoinColumn(name = "customerCode") var customerCode: Long,
    @Enumerated(EnumType.STRING) var state: StateInit = StateInit.Default,
    @Enumerated(EnumType.STRING) var firstLevel: FirstLevel = FirstLevel.SemPendencia,
    @Enumerated(EnumType.STRING) var secondLevel: SecondLevel = SecondLevel.SemPendencia,
    @Column(nullable = true) var description: String = " ",
    @Column(nullable = false) @JoinColumn(name = "invoicing_date", referencedColumnName = "invoicing_date") var invoicingDate: LocalDate?,
    @Column(nullable = false) @JoinColumn(name = "purchase_date", referencedColumnName = "purchase_date") var purchaseDate: LocalDate?,
    @Column(nullable = true) var solveDate: LocalDate?,
    @Column(nullable = true) var solveDriver: String ,
    @Column(nullable = true) var daysUntilSolve: Int?,
    @Enumerated(EnumType.STRING) var resolve: Resolve

    )
