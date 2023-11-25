package contracts.employees

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return sample employee with email")
    request {
        method GET()
        url '/employees/00000000-0000-4000-a000-000000000001'
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(
                uuid: "00000000-0000-4000-a000-000000000001",
                name: "John Doe",
                email: "john.doe@example.com"
        )
    }
}
