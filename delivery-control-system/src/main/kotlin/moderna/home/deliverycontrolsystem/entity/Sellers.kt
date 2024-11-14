package moderna.home.deliverycontrolsystem.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
@Table(name = "Vendedores")

data class Sellers(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var sellerId: Long? = null,
    @Column(nullable = false) var sellersName: String = " ",
    @Column(nullable = false, unique = true) var sellersRca: Long? = null,
    @OneToMany(
        fetch = FetchType.LAZY,
        cascade = [CascadeType.ALL],
        mappedBy = "orderSeller"
    ) @JsonBackReference var orders: List<Order> = mutableListOf(),
) {
    companion object {
        val defaultSeller = Sellers(
            sellerId = 1,
            sellersName = "Vendedor Não cadastrado"


        )


    }


}
