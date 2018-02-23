package media.redStone.authRestService.testTask;

import media.redStone.authRestService.testTask.configuration.MainJavaConfig;
import media.redStone.authRestService.testTask.security.AuthorizationServerConfiguration;
import media.redStone.authRestService.testTask.security.MethodSecurityConfig;
import media.redStone.authRestService.testTask.security.OAuth2SecurityConfiguration;
import media.redStone.authRestService.testTask.security.ResourceServerConfiguration;
import media.redStone.authRestService.testTask.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MainJavaConfig.class, AuthorizationServerConfiguration.class, MethodSecurityConfig.class, OAuth2SecurityConfiguration.class, ResourceServerConfiguration.class})
@WebAppConfiguration
public class ITControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private Filter filterChainProxy;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private UserService userService;
    private MultiValueMap<String, String> paramsMap = new LinkedMultiValueMap<>();
    private String accessToken;
    private String refreshToken;
    private final String usersUrl = "/user/?access_token=";

    @Before
    public void setUp() {
        paramsMap.add("grant_type", "password");
        paramsMap.add("username", "Rostyslav");
        paramsMap.add("password", "1111");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).addFilters(filterChainProxy).build();
    }

    // @Test
    public void shouldPassAuthenticationAndRetrieveData() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/oauth/token").params(paramsMap).headers(getHeadersWithClientCredentials())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.access_token").exists())
                .andExpect(jsonPath("$.token_type", is("bearer")))
                .andExpect(jsonPath("$.refresh_token").exists())
                .andExpect(jsonPath("$.expires_in").exists())
                .andExpect(jsonPath("$.scope", is("read write trust")))
                .andReturn();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        accessToken = parser.parseMap(mvcResult.getResponse().getContentAsString()).get("access_token").toString();
        refreshToken = parser.parseMap(mvcResult.getResponse().getContentAsString()).get("refresh_token").toString();
        mockMvc.perform(get(usersUrl + accessToken).accept(MediaType.APPLICATION_JSON_UTF8))
                //.andExpect(jsonPath("$.length", is(4)))
                .andExpect(jsonPath("$.[0].name", is("Sam")))
                .andExpect(jsonPath("$.[1].name", is("Tom")))
                .andExpect(jsonPath("$.[2].name", is("Jerome")))
                .andExpect(jsonPath("$.[3].name", is("Silvia")));
    }


    private static HttpHeaders getHeadersWithClientCredentials() {
        String plainClientCredentials = "my-trusted-client:secret";
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

}
