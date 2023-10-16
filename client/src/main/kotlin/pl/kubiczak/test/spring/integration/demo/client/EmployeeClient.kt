package pl.kubiczak.test.spring.integration.demo.client

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.UUID

interface EmployeeClient {

    @GET("/employees")
    fun listEmployees(): Call<List<EmployeeDto>>

    @GET("/employees/{employeeId}")
    fun findEmployee(@Path("employeeId") id: UUID): Call<EmployeeDto?>

    data class EmployeeDto(
        val uuid: UUID,
        val name: String,
        val email: String?
    )
}
