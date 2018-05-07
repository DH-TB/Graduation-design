package collection

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'DELETE'
        url value(consumer(regex('/collection/\\d+')),producer('/collection/1'))
    }
    response {
        status 204
    }
}