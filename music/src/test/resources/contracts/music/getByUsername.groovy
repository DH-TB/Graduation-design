package music

import org.springframework.cloud.contract.spec.Contract

Contract.make {
        request {
         method 'GET'
            url('/music') {
                queryParameters {
                    parameter('musicName', "m")
                }
            }
        response {
            status 200
            body("""
                  [{
                          "id": 1,
                          "info": "music-singer"
                  }]
                """)
        }
    }
}