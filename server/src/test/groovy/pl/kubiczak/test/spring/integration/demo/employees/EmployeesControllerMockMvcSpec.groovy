package pl.kubiczak.test.spring.integration.demo.employees

import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = [
        EmployeesController.class
])
class EmployeesControllerMockMvcSpec extends Specification {

    @Autowired
    private MockMvc mockMvc;

    def "get employees + expect content().string"() {

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(content().string('[]'))
    }


    def "get employees + expect content().json"() {

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(content().json('[  ]'))
    }

}
