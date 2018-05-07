package collection

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url value(consumer(regex('/collection/\\d+')),producer('/collection/1'))
    }
    response {
        status 200
        body("""
                  [{
                          "id": 1,
                          "userId":1,
                          "musicId":1,
                          "count": 100,
                          "love": 5

                  }]
                """)
    }
}