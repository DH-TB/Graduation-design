package com.bs;

import com.bs.entity.Comment;
import com.bs.entity.User;
import com.bs.repository.CommentRepository;
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
public class CommentBase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private FlywayService flywayService;

    private static boolean initialized = false;

    @Before
    public void setup() throws Exception {
        if (!initialized) {
            flywayService.migrateDatabase();
            initComment();
            initialized = true;
        }
        RestAssuredMockMvc.webAppContextSetup(this.wac);
    }
        private void initComment(){
        commentRepository.save(new Comment((long)1,(long)1,(long)1,"content",100,true,"nickName","image","2018-05-08 10:13:10.0"));
    }
}
