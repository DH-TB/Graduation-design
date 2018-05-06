package music

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method 'GET'
        url value(consumer(regex('/music/\\d+')),producer('/music/1'))

        response {
            status 200
            body("""
                  {
                          "music": {
                                    "id":1,
                                    "singerId":1,
                                    "music": "music",
                                    "album":"album",
                                    "pop":"pop",
                                    "collected":true,
                                    "image":"image"
                                    },
                          "singer": "singer",
                          "comment": [],

                  }
                """)
        }
    }
}
