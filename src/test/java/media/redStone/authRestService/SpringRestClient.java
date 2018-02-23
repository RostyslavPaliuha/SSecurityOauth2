package media.redStone.authRestService;

import media.redStone.authRestService.testTask.model.AuthTokenInfo;
import media.redStone.authRestService.testTask.model.User;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

public class SpringRestClient {

    public static final String REST_SERVICE_URI = "http://localhost:8080";

    public static final String AUTH_SERVER_URI = "http://localhost:8080/oauth/token";

    public static final String GRANT = "?grant_type=password&username=Rostyslav&password=1111";

    public static final String ACCESS_TOKEN = "?access_token=";

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static HttpHeaders getHeadersWithClientCredentials() {
        String plainClientCredentials = "my-trusted-client:secret";
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));

        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }


    private static AuthTokenInfo sendTokenRequest() {
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI + GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
        AuthTokenInfo tokenInfo = null;

        if (map != null) {
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccess_token((String) map.get("access_token"));
            tokenInfo.setToken_type((String) map.get("token_type"));
            tokenInfo.setRefresh_token((String) map.get("refresh_token"));
            tokenInfo.setExpires_in((int) map.get("expires_in"));
            tokenInfo.setScope((String) map.get("scope"));
            System.out.println(tokenInfo);

        } else {
            System.out.println("No user exist----------");

        }
        return tokenInfo;
    }

    private static void listAllUsers(AuthTokenInfo tokenInfo) {
        Assert.notNull(tokenInfo, "Authenticate first please......");

        System.out.println("\nTesting listAllUsers API-----------");
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI + "/user/" + ACCESS_TOKEN + tokenInfo.getAccess_token(),
                HttpMethod.GET, request, List.class);
        List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>) response.getBody();

        if (usersMap != null) {
            for (LinkedHashMap<String, Object> map : usersMap) {
                System.out.println("User : id=" + map.get("id") + ", Name=" + map.get("name") + ", Age=" + map.get("age") + ", Salary=" + map.get("salary"));
                ;
            }
        } else {
            System.out.println("No user exist----------");
        }
    }

    private static void getUser(AuthTokenInfo tokenInfo) {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting getUser API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI + "/user/1" + ACCESS_TOKEN + tokenInfo.getAccess_token(),
                HttpMethod.GET, request, User.class);
        User user = response.getBody();
        System.out.println(user);
    }

    private static void createUser(AuthTokenInfo tokenInfo) {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting create User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = new User();
        user.setName("Anya");
        user.setPassword("1111");
        user.setAge(25);
        user.setSalary(1000);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/user/" + ACCESS_TOKEN + tokenInfo.getAccess_token(),
                request);
        System.out.println("Location : " + uri.toASCIIString());
    }

    private static void updateUser(AuthTokenInfo tokenInfo) {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting update User API----------");
        RestTemplate restTemplate = new RestTemplate();
        User user = new User(1, "Tomy", 33, 70000);
        HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
        ResponseEntity<User> response = restTemplate.exchange(REST_SERVICE_URI + "/user/1" + ACCESS_TOKEN + tokenInfo.getAccess_token(),
                HttpMethod.PUT, request, User.class);
        System.out.println(response.getBody());
    }

    private static void deleteUser(AuthTokenInfo tokenInfo) {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting delete User API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI + "/user/3" + ACCESS_TOKEN + tokenInfo.getAccess_token(),
                HttpMethod.DELETE, request, User.class);
    }

    private static void deleteAllUsers(AuthTokenInfo tokenInfo) {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        restTemplate.exchange(REST_SERVICE_URI + "/user/" + ACCESS_TOKEN + tokenInfo.getAccess_token(),
                HttpMethod.DELETE, request, User.class);
    }

    public static void main(String args[]) {
        AuthTokenInfo tokenInfo = sendTokenRequest();
        listAllUsers(tokenInfo);

        getUser(tokenInfo);

        createUser(tokenInfo);
        listAllUsers(tokenInfo);

        updateUser(tokenInfo);
        listAllUsers(tokenInfo);

        deleteUser(tokenInfo);
        listAllUsers(tokenInfo);

        deleteAllUsers(tokenInfo);
        listAllUsers(tokenInfo);
    }
}