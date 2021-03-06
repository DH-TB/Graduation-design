package comment

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url value(consumer(regex('/comment/\\d+')),producer('/comment/1'))
    }
    response {
        status 200
        body("""
                 [
                    {
                    "id":1,
                    "musicId":1,
                    "userId":1,
                    "content":"content",
                    "count":100,
                    "nickName":"nickName",
                    "image":"image",
                    "createTime":"2018-05-08 10:13:10.0",
                    "like":true
                    }
                 ]
                """)
    }
}