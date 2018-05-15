package comment

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'POST'
        url '/comment'
        body("""
                  {
                          "id": 2,
                          "musicId": 1,
                          "userId": 2,
                          "content": "content",
                          "count":100,
                          "isLike":true
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
