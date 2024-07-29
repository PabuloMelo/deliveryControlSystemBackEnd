package moderna.home.deliverycontrolsystem.dto.load

import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.enumerators.Driver
import java.time.LocalDate

class LoadDTO(

    val loadNumber: Long?,
    val driver: Driver,
    val depurateDate: LocalDate,

    ){

fun toEntity(): Load = Load(

    loadNumber = this.loadNumber,
    driver = this.driver,
    departureDate = this.depurateDate,


    )



}

