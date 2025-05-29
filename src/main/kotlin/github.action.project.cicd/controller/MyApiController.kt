package github.action.project.cicd.controller


import github.action.project.cicd.util.LogServiceWrapper
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1"], produces = ["application/json"])
class MyApiController(
private val myApiService: MyApiService
) : MyApi{

    companion object {
        private val logger = LogServiceWrapper(MyApiController::class)
    }

    override fun getUserDetails()
        : ResponseEntity<List<String>> {

        val response = myApiService.getUsersDetails()

        return ResponseEntity.ok(response)

    }


}
