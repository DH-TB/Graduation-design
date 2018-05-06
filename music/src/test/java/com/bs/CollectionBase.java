package com.bs;

import com.bs.entity.Collection;
import com.bs.entity.User;
import com.bs.repository.CollectionRepository;
import com.bs.repository.UserRepository;
import com.jayway.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class CollectionBase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CollectionRepository collectionRepository;

    @Autowired
    private FlywayService flywayService;

    private static boolean initialized = false;

    @Before
    public void setup() throws Exception {
        if (!initialized) {
            flywayService.migrateDatabase();
            initCollection();
            initialized = true;
        }
        RestAssuredMockMvc.webAppContextSetup(this.wac);
    }

    private void initCollection(){
        collectionRepository.save(new Collection((long)1,(long)1,(long)1,(long)100,5));
    }
}
