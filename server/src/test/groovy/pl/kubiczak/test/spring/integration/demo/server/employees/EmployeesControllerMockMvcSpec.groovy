package pl.kubiczak.test.spring.integration.demo.server.employees

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import pl.kubiczak.test.spring.integration.demo.server.MockMvcSpringBaseTest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class EmployeesControllerMockMvcSpec extends MockMvcSpringBaseTest {

    @Autowired
    private MockMvc mockMvc

    def "Assertions on content().json"() {

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(content().json("""[
                  {
                    "uuid": "00000000-0000-4000-a000-000000000001",
                    "name": "John Doe",
                    "email": "john.doe@example.com"
                  },
                  {
                    "uuid": "00000000-0000-4000-a000-000000000002",
                    "name": "Jane Smith",
                    "email": null
                  }
                ]""", false)
                )
    }

    def "Assertions on jsonPath().value()"() {

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("\$[0].uuid").value("00000000-0000-4000-a000-000000000001")
                )
                .andExpect(
                        jsonPath("\$[0].name").value("John Doe")
                )
                .andExpect(
                        jsonPath("\$[0].email").value("john.doe@example.com")
                )
                .andExpect(
                        jsonPath("\$[1].uuid").value("00000000-0000-4000-a000-000000000002")
                )
                .andExpect(
                        jsonPath("\$[1].name").value("Jane Smith")
                )
                .andExpect(
                        jsonPath("\$[1].email").doesNotExist()
                )
    }
}
