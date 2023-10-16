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
        body '''\
		[
		    {
                    "uuid": "00000000-0000-0000-a000-000000000001",
                    "name": "John Doe",
                    "email": "john.doe@example.com"
		    },
		    {
                    "uuid": "00000000-0000-0000-a000-000000000002",
                    "name": "Jane Smith"
		    }
		]
	'''
    }
}
