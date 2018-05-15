package comment

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'DELETE'
        url value(consumer(regex('/comment/\\d+')),producer('/comment/2'))
    }
    response {
        status 204
    }
}