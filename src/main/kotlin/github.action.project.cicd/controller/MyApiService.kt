package github.action.project.cicd.controller

import org.springframework.stereotype.Service

@Service
class MyApiService{

fun getUsersDetails() : List<String>{

    val users = listOf("Rocky", "Pratik", "Sangram")

    return users
}


}