package github.action.project.cicd

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GithubActionApplication

fun main(args: Array<String>) {
	runApplication<GithubActionApplication>(*args)
}
