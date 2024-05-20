package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.Driver
import moderna.home.deliverycontrolsystem.enumerators.FirstLevel
import moderna.home.deliverycontrolsystem.enumerators.SecondLevel
import moderna.home.deliverycontrolsystem.enumerators.StateInit
import java.time.LocalDate

@Entity
@Table(name = "Situacao")
data class State (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val stateId: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "orderId", referencedColumnName = "orderId") var order: Order? = null,
    @Column(nullable = false) var orderNumber: String = "",
    @Enumerated(EnumType.STRING) var state: StateInit = StateInit.Default,
    @Enumerated(EnumType.STRING) var firstLevel: FirstLevel = FirstLevel.SemPendencia,
    @Enumerated(EnumType.STRING) var secondLevel: SecondLevel = SecondLevel.SemPendencia,
    @Column(nullable = true) var description: String = " ",
    @Column(nullable = true) var solveDate: LocalDate,
    @Enumerated(EnumType.STRING) var solveDriver: Driver = Driver.Default,
    @Column(nullable = true) var daysUntilSolve: Long,

    )
