package pl.kubiczak.test.spring.integration.demo.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Configuration
class RetrofitConfiguration {

    @Bean
    fun employeeClient(@Value("\${remotes.employees.domain}") employeeDomain: String): EmployeeClient = try {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(employeeDomain)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(EmployeeClient::class.java)
    } catch (iae: IllegalArgumentException) {
        throw iae
    }
}
