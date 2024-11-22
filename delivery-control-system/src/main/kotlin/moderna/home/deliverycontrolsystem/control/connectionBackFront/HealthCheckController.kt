package moderna.home.deliverycontrolsystem.control.connectionBackFront

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.sql.DataSource

@RestController

@RequestMapping("/api/")
class HealthCheckController(val dataSource: DataSource) {

@GetMapping("/database-status")

fun  testDataBaseStatus(): ResponseEntity<String>{

    return try {

        dataSource.connection.use {

            connection ->

            if (connection.isValid(1000)){

                ResponseEntity.ok("Banco de dados Ativo")

            }else{

                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Banco de dados Inativo")

            }

        }


    }catch (ex: Exception){

        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao tentar verificar a conexão com o banco de dados ${ex.message}")

    }



}

}

