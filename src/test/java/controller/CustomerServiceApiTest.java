package controller;

import config.RootConfig;
import config.WebConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class})
@Transactional
public class CustomerServiceApiTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void metadataAndStatusQueryReturnRealData() throws Exception {
        mockMvc.perform(get("/api/customer-services/metadata"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"serviceTypes\"")))
                .andExpect(content().string(containsString("\"users\"")));

        mockMvc.perform(get("/api/customer-services").param("status", "新创建"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"success\":true")))
                .andExpect(content().string(containsString("\"items\"")));
    }

    @Test
    public void createEndpointCreatesNewService() throws Exception {
        String body = "{\"customerNo\":\"KH202608310000001\","
                + "\"customerName\":\"北京阳光实业有限公司\","
                + "\"type\":\"咨询\",\"title\":\"接口测试服务\","
                + "\"request\":\"验证创建服务接口。\"}";
        mockMvc.perform(post("/api/customer-services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"success\":true")))
                .andExpect(content().string(containsString("\"svrStatus\"")))
                .andExpect(content().string(containsString("\"svrId\"")));
    }
}
