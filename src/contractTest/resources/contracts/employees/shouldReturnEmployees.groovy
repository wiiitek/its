package contracts.employees

import org.springframework.cloud.contract.spec.Contract


Contract.make {
    description("Should return empty array")
    request {
        method GET()
        url '/employees'
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body([])
    }
}
