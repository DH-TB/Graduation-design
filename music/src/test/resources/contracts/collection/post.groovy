package collection

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/collection'
        body("""
                 {
                          "id": 2,
                          "userId":1,
                          "musicId":1,
                          "count": 100,
                          "love": 5

                  }
                """)
        headers {
            header('Content-Type', 'application/json;charset=UTF-8')
        }
    }
    response {
        status 201
    }
}
