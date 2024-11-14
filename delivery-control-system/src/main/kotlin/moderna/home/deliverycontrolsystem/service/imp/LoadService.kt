package moderna.home.deliverycontrolsystem.service.imp

import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.exceptions.BusinessException
import moderna.home.deliverycontrolsystem.repository.LoadRepository
import moderna.home.deliverycontrolsystem.service.ILoadService
import org.springframework.stereotype.Service

@Service
class LoadService(

    private val loadRepository: LoadRepository
) : ILoadService {
    override fun save(load: Load): Load =
        this.loadRepository.save(load)


    override fun findByloadNumber(loadNumber: Long): Load {
        val load: Load = (
                this.loadRepository.findByloadNumber(loadNumber)
                    ?: throw BusinessException("Carregamento $loadNumber não encontrado"))
        return load
    }

    override fun findAllbyLoad(loadNumber: Long): List<Order> =
        this.loadRepository.findAllByLoad(loadNumber)


}