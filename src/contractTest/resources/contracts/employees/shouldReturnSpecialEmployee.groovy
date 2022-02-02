package contracts.employees

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return special employee")
    request {
        method GET()
        url '/employees/00000000-0000-0000-0000-000000000000'
    }
    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body(uuid: '00000000-0000-0000-0000-000000000000', name: 'John Doe', email: 'john.doe@example.com')
    }
}
