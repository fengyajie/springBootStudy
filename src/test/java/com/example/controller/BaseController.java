package java.com.example.controller;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author fyj
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseController {

    public MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setup() { this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build(); }

    public static final Logger logger = LoggerFactory.getLogger(BaseController.class);
}
