package moderna.home.deliverycontrolsystem.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "Vendedores")

data class Sellers(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val sellerId: UUID = UUID.randomUUID(),
    @Column(nullable = false) var sellersName: String = " ",
    @Column(nullable = false) var sellersRCA: String = " ",

    )
