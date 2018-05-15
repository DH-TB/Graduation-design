package collection

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'DELETE'
        url('/collection/undo') {
            queryParameters {
                parameter('musicId', 1)
                parameter('userId', 2)

            }
        }
    }
    response {
        status 204
    }
}