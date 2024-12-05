package moderna.home.deliverycontrolsystem.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import moderna.home.deliverycontrolsystem.enumerators.*
import java.time.LocalDate

@Entity
@Table(name = "Situacao")
data class State(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val stateId: Long? = null,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonManagedReference
    var order: Order? = null,


    @Enumerated(EnumType.STRING)
    var state: StateInit,

    @Enumerated(EnumType.STRING)
    var firstLevel: FirstLevel,

    @Enumerated(EnumType.STRING)
    var secondLevel: SecondLevel,

    @Column(nullable = true)
    var description: String = " ",

    @Column(nullable = true)
    var solveDate: LocalDate?,

    @Column(nullable = true)
    var solveDriver: String,

    @Column(nullable = true)
    var daysUntilSolve: Int?,

    @Enumerated(EnumType.STRING)
    var resolve: Resolve

)
