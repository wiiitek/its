package contracts.employees

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description("Should return 404 for non-existing employee")
    request {
        method GET()
        url '/employees/00000000-0000-0000-0000-000000000000'
    }
    response {
        status NOT_FOUND()
    }
}
