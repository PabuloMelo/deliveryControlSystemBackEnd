package moderna.home.deliverycontrolsystem.service.Imp

import moderna.home.deliverycontrolsystem.entity.Load
import moderna.home.deliverycontrolsystem.entity.Order
import moderna.home.deliverycontrolsystem.repository.LoadRepository
import moderna.home.deliverycontrolsystem.service.ILoadService
import org.springframework.stereotype.Service

@Service
class LoadService(

    private val loadRepository: LoadRepository
) : ILoadService {
    override fun save(load: Load): Load =
        this.loadRepository.save(load)


    override fun findByloadNumber(loadCode: Long): Load {
        val load: Load = (
                this.loadRepository.findByloadNumber(loadCode) ?:
                throw RuntimeException("Carregamento $loadCode não encontrado"))
        return load
    }

    override fun findAllbyLoad(load: Load): List<Order> =
        this.loadRepository.findAllbyLoad(load)


}