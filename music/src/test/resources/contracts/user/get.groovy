package user

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url('/user') {
            queryParameters {
                parameter('username', "root")
                parameter('password', "root")

            }
        }
        response {
            status 200
            body("""
                  {
                          "id": 1,
                          "username": "root",
                          "password": "root"

                  }
                """)
        }
    }
}