package moderna.home.deliverycontrolsystem.dto.load

import moderna.home.deliverycontrolsystem.entity.Load
import java.time.LocalDate

class LoadDTO(

    val loadNumber: Long?,
    val driver: String,
    val depurateDate: LocalDate,

    ){

fun toEntity(): Load = Load(

    loadNumber = this.loadNumber,
    driver = this.driver,
    departureDate = this.depurateDate,


    )



}

