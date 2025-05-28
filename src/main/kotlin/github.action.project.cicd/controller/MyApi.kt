package github.action.project.cicd.controller

import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping

@Validated
interface MyApi {

    companion object{
        const val USERS = "/users"
    }

    @GetMapping(USERS)
    fun getUserDetails() : ResponseEntity<List<String>>

}