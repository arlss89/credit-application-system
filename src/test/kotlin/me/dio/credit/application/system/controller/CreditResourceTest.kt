package me.dio.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import me.dio.credit.application.system.dto.request.CreditDto
import me.dio.credit.application.system.dto.request.CustomerDto
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.repository.CustomerRepository
import org.json.JSONObject
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditResourceTest {

    @Autowired private lateinit var creditRepository: CreditRepository
    @Autowired private lateinit var customerRepository: CustomerRepository
    @Autowired private lateinit var mockMvc: MockMvc
    @Autowired private lateinit var objectMapper: ObjectMapper
    @Autowired private lateinit var context: WebApplicationContext

    companion object {
        const val URL = "/api/credits"
    }

    @BeforeEach
    @WithMockUser(username = "demo", password = "demo")
    fun setup() {

        creditRepository.deleteAll()
        customerRepository.deleteAll()

        this.mockMvc = MockMvcBuilders
            .webAppContextSetup(this.context)
            .build()
    }

    @AfterEach
    fun tearDown() = customerRepository.deleteAll()

    @Test
    fun ` should create a credit and return 201 status `() {
        val customerId = createAndGetCustomerId()

        //creating a credit with customerId from customer added.
        val creditDto = builderCreditDto(customerId = customerId)
        val creditResultAction = postCredit(creditDto)

        //verifying if credit was created
        creditResultAction.andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun ` should not create a credit and return 400 status, because date of first Installment is today `() {
        val customerId = createAndGetCustomerId()

        //creating a credit with customerId from customer added.
        val creditDto = builderCreditDto(customerId = customerId, dayFirstOfInstallment = LocalDate.now())
        val creditResultAction = postCredit(creditDto)

        //verifying if credit was created
        creditResultAction.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun ` should find all credits by customerId and return 201 status `() {
        val customerId = createAndGetCustomerId()

        //creating a credit1 with customerId from customer added.
        val creditDto1 = builderCreditDto(customerId = customerId)
        val creditResultAction1 = postCredit(creditDto1)

        //creating a credit2 with customerId from customer added.
        val creditDto2 = builderCreditDto(customerId = customerId)
        val creditResultAction2 = postCredit(creditDto2)

        //verifying if credit was created
        creditResultAction1.andExpect(MockMvcResultMatchers.status().isCreated)
        creditResultAction2.andExpect(MockMvcResultMatchers.status().isCreated)

        val createdList = creditRepository.findAllByCustomerId(customerId).map { it.creditCode.toString() }

        assert(createdList.size == 2)
        getCreditUsingCustomerID(customerId).andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun ` should find all credits by customerId that not exists and return 200 status and contentAsString equals a emptyArray `() {
        val customerId = 10L
        val createdList = creditRepository.findAllByCustomerId(customerId).map { it.creditCode.toString() }

        val result = getCreditUsingCustomerID(customerId)

        result.andExpect(MockMvcResultMatchers.status().isOk)
        assert(result.andReturn().response.contentAsString == "[]")
    }

    private fun postCredit(creditDto: CreditDto): ResultActions {
        val creditAsString = objectMapper.writeValueAsString(creditDto)
        return mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(creditAsString)
        )
    }

    private fun getCreditUsingCustomerID(customerId: Long): ResultActions {
        return mockMvc.perform(
            MockMvcRequestBuilders.get("$URL?customerId=$customerId")
        )
    }

    private fun createAndGetCustomerId(): Long {
        //create a customer
        val customerDto = builderCustomerDto()
        val customerAsString = objectMapper.writeValueAsString(customerDto)
        //adding customer
        val customerResultAction = mockMvc.perform(
            MockMvcRequestBuilders.post(CustomerResourceTest.URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerAsString)
        )
        val jsonCustomerInserted = JSONObject(customerResultAction.andReturn().response.contentAsString)
        val customerId = (jsonCustomerInserted["id"].toString()).toLong()

        return customerId
    }

    //TODO findbycreditcode

    private fun builderCustomerDto(
        firstName: String = "Alyson",
        lastName: String = "Reis",
        cpf: String = "28475934625",
        email: String = "alyson@mail.com",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        password: String = "1234",
        zipCode: String = "000000",
        street: String = "Rua rua, 123",
    ) = CustomerDto(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        income = income,
        password = password,
        zipCode = zipCode,
        street = street
    )

    private fun builderCreditDto (
        creditValue: BigDecimal = 10.toBigDecimal(),
        dayFirstOfInstallment: LocalDate = LocalDate.now().plusDays(30),
        numberOfInstallments: Int = 12,
        customerId: Long
    ) = CreditDto (
        creditValue = creditValue,
        dayFirstOfInstallment = dayFirstOfInstallment,
        numberOfInstallments = numberOfInstallments,
        customerId = customerId
    )

}
