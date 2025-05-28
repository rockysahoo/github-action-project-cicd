package github.action.project.cicd.controller

import com.springboot.postgres.docker.kotlin.prometheus.grafana.app.util.LogServiceWrapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1"], produces = ["application/json"])
class MyApiController() : MyApi{

    companion object {
        private val logger = LogServiceWrapper(MyApiController::class)
    }

    override fun getUserDetails()
        : ResponseEntity<List<String>> {

        val users = listOf("Rocky", "Pratik", "Sangram")

        return ResponseEntity.ok(users)

    }


}
