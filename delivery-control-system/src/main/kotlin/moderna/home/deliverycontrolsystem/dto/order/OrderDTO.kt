package moderna.home.deliverycontrolsystem.dto.order


import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.enumerators.FutureDlState
import moderna.home.deliverycontrolsystem.enumerators.OrderType
import moderna.home.deliverycontrolsystem.enumerators.Status
import moderna.home.deliverycontrolsystem.repository.CustomerRepository
import moderna.home.deliverycontrolsystem.repository.LoadRepository
import moderna.home.deliverycontrolsystem.repository.SellersRepository
import java.time.LocalDate
import java.time.temporal.ChronoUnit

//Recebe os Inputs do Usuario
data class OrderDTO(


    val orderCode: Long?,
    val customerCode: Long?,
    var loadNumber: Long?,
    val status: Status,
    val orderType: OrderType,
    val purchaseDate: LocalDate,
    val invoicingDate: LocalDate?,
    val daysUntilDelivery: Int,
    val orderAddress: String


    ) {



    // Define o codigo RCA do vendedor do pedido com base nos  2 primeiros digitos do pedido
    private fun defineRCA(orderCode: Long?): Long {
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
            orderType == OrderType.ENTREGA_FUTURA && status == Status.PENDENTE || orderType == OrderType.ENTREGA_FUTURA && status == Status.AGENDADA ->

                ChronoUnit.DAYS.between(purchaseDate, actualDay).toInt()


            orderType == OrderType.ENTREGA_FUTURA && status == Status.ENTREGUE && invoicingDate != null ->

                ChronoUnit.DAYS.between(purchaseDate, invoicingDate).toInt()


            else -> 0


        }
    }

    // Define uma data padrão para o campo de faturamento em caso de pedidos do tipo entrega futura afim de evitar campos nullos
    fun invoiceDateDefault(orderType: OrderType, status: Status, invoicingDate: LocalDate?): LocalDate? {

        return if (orderType == OrderType.ENTREGA_FUTURA && status == Status.PENDENTE) {

            LocalDate.of(1000, 1, 1)

        } else invoicingDate

    }


    //Define o status do Pedido com base na contagem de dias entre a data da compra e o dia atual
    private fun defineOderFutureStatus(orderType: OrderType, status: Status, daysUntilDelivery: Int): FutureDlState {

        return when {
            orderType == OrderType.ENTREGA_FUTURA && status == Status.PENDENTE && daysUntilDelivery <= 30 ->

                FutureDlState.DentroDoPrazo

            orderType == OrderType.ENTREGA_FUTURA && status == Status.PENDENTE && daysUntilDelivery > 30 ->

                FutureDlState.AcimaDoPrazo

            orderType == OrderType.ENTREGA_FUTURA && status == Status.ENTREGUE ->
                FutureDlState.Entregue


            else -> FutureDlState.Default
        }


    }


    //Transforma os Inputs fornecidos e preenche os campos da entidade
    fun toEntityOrder(customerRepository: CustomerRepository, sellersRepository: SellersRepository, loadRepository: LoadRepository): Order {

        //transforma as funções em variaveis para serem aplicadas no na entidade
        val actualDayOfInvoicing = invoiceDateDefault(orderType, status, this.invoicingDate)
        val actualContDays = contDays(orderType, status, purchaseDate, LocalDate.now(), actualDayOfInvoicing)
        val actualStatus = defineOderFutureStatus(
            orderType,
            status,
            contDays(orderType, status, purchaseDate, LocalDate.now(), invoicingDate)
        )
        //Busca no banco de dados o cliente com base no codigo fornecido no DTTO e preenche os campos pertinentes ao cliente
        val customer = customerRepository.findByCustomerCode(this.customerCode!!)
        val seller = sellersRepository.findBySellerRca(defineRCA(orderCode))
        val load = loadRepository.findByloadNumber(this.loadNumber!!)




        return Order(

            orderCode = this.orderCode,
            load = load,
            customer = customer,
            status = this.status,
            orderType = this.orderType,
            purchaseDate = this.purchaseDate,
            invoicingDate = actualDayOfInvoicing,
            orderSeller = seller,
            orderRCA = defineRCA(orderCode),
            daysUntilDelivery = actualContDays,
            orderFutureDelState = actualStatus,
            orderAddress = this.orderAddress

        )
    }
}
