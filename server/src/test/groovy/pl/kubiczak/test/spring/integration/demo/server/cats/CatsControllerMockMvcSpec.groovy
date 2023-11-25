package pl.kubiczak.test.spring.integration.demo.server.cats

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import pl.kubiczak.test.spring.integration.demo.server.MockMvcSpringBaseTest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CatsControllerMockMvcSpec extends MockMvcSpringBaseTest {

    @Autowired
    private MockMvc mockMvc

    def "Should return list of cats with their IDs"() {
        expect:
        mockMvc.perform(get('/cats'))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("\$[0].id").value("00000000-0000-0000-b000-000000000001")
                )
                .andExpect(
                        jsonPath("\$[1].id").value("00000000-0000-0000-b000-000000000002")
                )
    }

    def "Should return list of cats with their names"() {
        expect:
        mockMvc.perform(get('/cats'))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("\$[0].name").isEmpty()
                )
                .andExpect(
                        jsonPath("\$[1].name").value("Thomas")
                )
    }

    def "Should return list of cats with their facts"() {
        expect:
        mockMvc.perform(get('/cats'))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("\$[0].fact").value("Cats have 9 lives")
                )
                .andExpect(
                        jsonPath("\$[1].fact").value("")
                )
    }
}
