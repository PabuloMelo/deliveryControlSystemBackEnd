package moderna.home.deliverycontrolsystem.dto.order


import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.entity.Sellers
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import moderna.home.deliverycontrolsystem.repository.CustomerRepository
import moderna.home.deliverycontrolsystem.repository.SellersRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import kotlin.random.Random

//Recebe os Imputs do Usuario
data class OrderDTO(


    val orderCode: Long?,
    val customerCode: Long?,
    var loadNumber: Long?,
    val status: Status,
    val orderType: OrderType,
    val purchaseDate: LocalDate,
    val invoicingDate: LocalDate?,
    val sellerRCA: Long,
    val daysUntilDelivery: Int,


    ) {


    // Verifica o tipo de pedido e apartir disso define seu carregamento
    fun defineLoad(orderType: OrderType): Long? {

        return when {
            orderType == OrderType.Entrega -> {

                this.loadNumber


            }

            orderType == OrderType.EntregaFutura -> {

                loadNumber = 1
                loadNumber
            }

            orderType == OrderType.Carteira || orderType == OrderType.CartEntrega || orderType == OrderType.CartRetiraPosterior -> {

                loadNumber = 2
                loadNumber

            }

            else -> {
                loadNumber = 3
                loadNumber
            }
        }


    }

    // Define o codigo RCA do vendedor do pedido com base nos  2 primeiros digitos do pedido
    fun defineRCA(orderCode: Long?): Long {
        val orderCodeString = orderCode.toString()
        val firstTwoNumber = orderCodeString.take(2)

        return firstTwoNumber.toLong()
    }

    // Realiza a contagem dos dias entre a data de compra e a data de entrega dos pedidos do tipo de entrega futura
    fun contDays(
        orderType: OrderType,
        status: Status,
        purchaseDate: LocalDate,
        actualDay: LocalDate,
        invoicingDate: LocalDate?
    ): Int {

        return when {
            orderType == OrderType.EntregaFutura && status == Status.Pendente || orderType == OrderType.EntregaFutura && status == Status.Agendada ->

                ChronoUnit.DAYS.between(purchaseDate, actualDay).toInt()


            orderType == OrderType.EntregaFutura && status == Status.Entregue && invoicingDate != null ->

                ChronoUnit.DAYS.between(purchaseDate, invoicingDate).toInt()


            else -> 0


        }
    }

    // Define uma data padrão para o campo de faturamento em caso de pedidos do tipo entrega futura afim de evitar campos nullos
    fun invoiceDateDefault(orderType: OrderType, status: Status, invoicingDate: LocalDate?): LocalDate? {

        return if (orderType == OrderType.EntregaFutura && status == Status.Pendente) {

            LocalDate.of(1000, 1, 1)

        } else invoicingDate

    }


    //Define o status do Pedido com base na contagem de dias entre a data da compra e o dia atual
    fun defineOderFutureStatus(orderType: OrderType, status: Status, daysUntilDelivery: Int): FutureDlState {

        return when {
            orderType == OrderType.EntregaFutura && status == Status.Pendente && daysUntilDelivery <= 30 ->

                FutureDlState.DentroDoPrazo

            orderType == OrderType.EntregaFutura && status == Status.Pendente && daysUntilDelivery > 30 ->

                FutureDlState.AcimaDoPrazo

            orderType == OrderType.EntregaFutura && status == Status.Entregue ->
                FutureDlState.Entregue


            else -> FutureDlState.Default
        }


    }


    //Transforma os Imputs fornecidos e preenche os campos da entidade
    fun toEntityOrder(customerRepository: CustomerRepository, sellersRepository: SellersRepository): Order {

        //transforma as funções em variaveis para serem aplicadas no na entidade
        val actualDayofInvoicing = invoiceDateDefault(orderType, status, this.invoicingDate)
        val actualContDays = contDays(orderType, status, purchaseDate, LocalDate.now(), actualDayofInvoicing)
        val actualStatus = defineOderFutureStatus(
            orderType,
            status,
            contDays(orderType, status, purchaseDate, LocalDate.now(), invoicingDate)
        )
        //Busca no banco de dados o cliente com base no codigo fornecido no DTTO e preenche os campos pertinentes ao cliente
        val customer = customerRepository.findByCustomerCode(this.customerCode!!)
        val customerName = customer?.name ?: throw IllegalArgumentException("Cliente ${customer?.name} não encontrado")
        val seller = sellersRepository.findBySellerRca(defineRCA(orderCode))
        val sellerName = seller?.sellersName ?: "Vendedor Não cadastrado"




        return Order(

            orderCode = this.orderCode,
            customerCode = this.customerCode,
            customerName = customerName,
            loadNumber = this.loadNumber,
            status = this.status,
            orderType = this.orderType,
            purchaseDate = this.purchaseDate,
            invoicingDate = actualDayofInvoicing,
            sellerRCA = defineRCA(orderCode),
            sellerName = sellerName,
            daysUntilDelivery = actualContDays,
            orderFutureDelState = actualStatus

        )
    }
}
