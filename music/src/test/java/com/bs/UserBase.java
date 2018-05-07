package com.bs;

import com.bs.entity.User;
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

import static com.bs.utils.Md5Util.getMD5;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class UserBase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlywayService flywayService;

    private static boolean initialized = false;

    @Before
    public void setup() throws Exception {
        if (!initialized) {
            flywayService.migrateDatabase();
            initCart();
            initialized = true;
        }
        RestAssuredMockMvc.webAppContextSetup(this.wac);
    }

    private void initCart(){
        String md5Password = getMD5(getMD5("root"));
        userRepository.save(new User((long)1,"root",md5Password));
    }
}
