package pl.kubiczak.test.spring.integration.demo.employees


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import pl.kubiczak.test.spring.integration.demo.MockMvcSpringBaseTest

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class EmployeesControllerMockMvcSpec extends MockMvcSpringBaseTest {

    @Autowired
    private MockMvc mockMvc

    def "Assertions on content().string"() {

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(content().string('[]'))
    }

    def "Assertions on content().json"() {

        expect:
        mockMvc.perform(get('/employees'))
                .andExpect(status().isOk())
                .andExpect(content().json('[  ]'))
    }
}
