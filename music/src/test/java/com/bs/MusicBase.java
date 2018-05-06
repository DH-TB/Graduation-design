package com.bs;

import com.bs.entity.Music;
import com.bs.entity.Singer;
import com.bs.repository.MusicRepository;
import com.bs.repository.SignerRepository;
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
public class MusicBase {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MusicRepository musicRepository;

    @Autowired
    private SignerRepository singerRepository;

    @Autowired
    private FlywayService flywayService;

    private static boolean initialized = false;


    @Before
    public void setup() throws Exception {
        if (!initialized) {
            flywayService.migrateDatabase();
            initMusic();
            initSinger();
            initialized = true;
        }
        RestAssuredMockMvc.webAppContextSetup(this.wac);
    }

    private void initMusic(){
        musicRepository.save(new Music((long)1,(long)1,"music","album","pop",true,"image"));
    }

    private void initSinger(){
        singerRepository.save(new Singer((long)1,"tag","singer"));
    }
}