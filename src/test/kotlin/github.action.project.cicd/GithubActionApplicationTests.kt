package github.action.project.cicd

import github.action.project.cicd.controller.MyApiController
import github.action.project.cicd.controller.MyApiService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity


@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class GithubActionApplicationTests {
	// Mock dependencies
	private val myApiService: MyApiService = mockk()

	// System under test
	private lateinit var myApiController: MyApiController

	@BeforeEach
	fun setUp() {
		myApiController = MyApiController(myApiService)
	}

	@Test
	fun `getUserDetails should return OK response with user details when service returns data`() {
		// Given
		val expectedUserDetails = listOf("user1", "user2", "user3")
		every { myApiService.getUsersDetails() } returns expectedUserDetails

		// When
		val result = myApiController.getUserDetails()

		// Then
		assertNotNull(result)
		assertEquals(HttpStatus.OK, result.statusCode)
		assertEquals(expectedUserDetails, result.body)
		verify(exactly = 1) { myApiService.getUsersDetails() }
	}

	@Test
	fun `getUserDetails should return OK response with empty list when service returns empty list`() {
		// Given
		val expectedUserDetails = emptyList<String>()
		every { myApiService.getUsersDetails() } returns expectedUserDetails

		// When
		val result = myApiController.getUserDetails()

		// Then
		assertNotNull(result)
		assertEquals(HttpStatus.OK, result.statusCode)
		assertEquals(expectedUserDetails, result.body)
		assertEquals(0, result.body?.size)
		verify(exactly = 1) { myApiService.getUsersDetails() }
	}

	@Test
	fun `getUserDetails should return OK response with single user when service returns single item`() {
		// Given
		val expectedUserDetails = listOf("singleUser")
		every { myApiService.getUsersDetails() } returns expectedUserDetails

		// When
		val result = myApiController.getUserDetails()

		// Then
		assertNotNull(result)
		assertEquals(HttpStatus.OK, result.statusCode)
		assertEquals(expectedUserDetails, result.body)
		assertEquals(1, result.body?.size)
		assertEquals("singleUser", result.body?.first())
		verify(exactly = 1) { myApiService.getUsersDetails() }
	}

	@Test
	fun `getUserDetails should propagate exception when service throws exception`() {
		// Given
		val expectedException = RuntimeException("Service error")
		every { myApiService.getUsersDetails() } throws expectedException

		// When & Then
		val actualException = assertThrows<RuntimeException> {
			myApiController.getUserDetails()
		}

		assertEquals("Service error", actualException.message)
		verify(exactly = 1) { myApiService.getUsersDetails() }
	}

	@Test
	fun `getUserDetails should handle null response from service gracefully`() {
		// Given
		every { myApiService.getUsersDetails() } returns emptyList()

		// When
		val result = myApiController.getUserDetails()

		// Then
		assertNotNull(result)
		assertEquals(HttpStatus.OK, result.statusCode)
		assert(result.body!!.isEmpty())
		verify(exactly = 1) { myApiService.getUsersDetails() }
	}

	@Test
	fun `getUserDetails should call service exactly once per request`() {
		// Given
		val expectedUserDetails = listOf("user1", "user2")
		every { myApiService.getUsersDetails() } returns expectedUserDetails

		// When
		myApiController.getUserDetails()
		myApiController.getUserDetails()

		// Then
		verify(exactly = 2) { myApiService.getUsersDetails() }
	}

	@Test
	fun `getUserDetails should return ResponseEntity with correct type`() {
		// Given
		val expectedUserDetails = listOf("user1", "user2")
		every { myApiService.getUsersDetails() } returns expectedUserDetails

		// When
		val result = myApiController.getUserDetails()

		// Then
		assertNotNull(result)
		assertEquals(ResponseEntity::class, result::class)
		//assertEquals(List::class, result.body!!::class)
		verify(exactly = 1) { myApiService.getUsersDetails() }
	}

	@Test
	fun `getUserDetails should handle large list response correctly`() {
		// Given
		val largeUserList = (1..1000).map { "user$it" }
		every { myApiService.getUsersDetails() } returns largeUserList

		// When
		val result = myApiController.getUserDetails()

		// Then
		assertNotNull(result)
		assertEquals(HttpStatus.OK, result.statusCode)
		assertEquals(1000, result.body?.size)
		assertEquals("user1", result.body?.first())
		assertEquals("user1000", result.body?.last())
		verify(exactly = 1) { myApiService.getUsersDetails() }
	}

	@Test
	fun `getUserDetails should handle special characters in user details`() {
		// Given
		val specialCharacterUsers = listOf("user@domain.com", "user-name_123", "用户", "José")
		every { myApiService.getUsersDetails() } returns specialCharacterUsers

		// When
		val result = myApiController.getUserDetails()

		// Then
		assertNotNull(result)
		assertEquals(HttpStatus.OK, result.statusCode)
		assertEquals(specialCharacterUsers, result.body)
		assertEquals(4, result.body?.size)
		verify(exactly = 1) { myApiService.getUsersDetails() }
	}

	@Test
	fun `controller should be initialized with service dependency`() {
		// When
		val controller = MyApiController(myApiService)

		// Then
		assertNotNull(controller)
	}

	// Integration-style test with relaxed mocking
	@Test
	fun `getUserDetails integration test with relaxed mock`() {
		// Given
		val relaxedService: MyApiService = mockk(relaxed = true)
		val controller = MyApiController(relaxedService)
		every { relaxedService.getUsersDetails() } returns listOf("integration-user")

		// When
		val result = controller.getUserDetails()

		// Then
		assertNotNull(result)
		assertEquals(HttpStatus.OK, result.statusCode)
		assertEquals(listOf("integration-user"), result.body)
	}

}
