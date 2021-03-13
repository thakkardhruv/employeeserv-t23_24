import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeservApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeResourceImplTest {

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper mapper;

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void test_1_testAddEmloyeeSuccess() throws Exception {
        String emp = "{\"first_name\": \"F_NAME\", \"last_name\": \"K_NAME\", \"date_of_birth\": \"13/03/1990\", \"address\": { \"line1\": \"N1\", \"line2\": \"N2\", \"city\": \"city1\", \"state\": \"STATE1\", \"country\": \"India\", \"zip_code\": \"560037\" } }";

        Employee e = mapper.readValue(emp, Employee.class);
        HttpEntity<Employee> entity = new HttpEntity<>(e, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/v1/bfs/employees/add"), HttpMethod.POST, entity, String.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Employee responseEmp = mapper.readValue(response.getBody(), Employee.class);
        Assert.assertEquals(responseEmp.getAdditionalProperties().get("success"), true);
    }

    @Test
    public void test_2_testAddEmloyeeValidationFails() throws Exception {
        String emp = "{ \"last_name\": \"K_NAME\", \"date_of_birth\": \"13/03/1990\", \"address\": { \"line1\": \"N1\", \"line2\": \"N2\", \"city\": \"city1\", \"state\": \"STATE1\", \"country\": \"India\", \"zip_code\": \"560037\" } }";

        Employee e = mapper.readValue(emp, Employee.class);
        HttpEntity<Employee> entity = new HttpEntity<>(e, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/v1/bfs/employees/add"), HttpMethod.POST, entity, String.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Employee responseEmp = mapper.readValue(response.getBody(), Employee.class);
        Assert.assertEquals(responseEmp.getAdditionalProperties().get("success"), false);
        Assert.assertEquals(responseEmp.getAdditionalProperties().get("error"), "Invalid Request parameters");
    }

    @Test
    public void test_3_testDOBInWrongFormat() throws Exception {
        String emp = "{\"first_name\": \"F_NAME\", \"last_name\": \"K_NAME\", \"date_of_birth\": \"13-03-1990\", \"address\": { \"line1\": \"N1\", \"line2\": \"N2\", \"city\": \"city1\", \"state\": \"STATE1\", \"country\": \"India\", \"zip_code\": \"560037\" } }";

        Employee e = mapper.readValue(emp, Employee.class);
        HttpEntity<Employee> entity = new HttpEntity<>(e, headers);

        ResponseEntity<String> response = testRestTemplate.exchange(
                createURLWithPort("/v1/bfs/employees/add"), HttpMethod.POST, entity, String.class);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Employee responseEmp = mapper.readValue(response.getBody(), Employee.class);
        Assert.assertEquals(responseEmp.getAdditionalProperties().get("success"), false);
        Assert.assertEquals(responseEmp.getAdditionalProperties().get("error"), "Invalid Date of Birth format, Please provide DOB as dd/MM/YYYY");
    }

    @Test
    public void test_4_testGetEmployee() throws Exception {
        ResponseEntity<String> response = testRestTemplate.getForEntity(createURLWithPort("/v1/bfs/employees/1"), String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Employee responseEmp = mapper.readValue(response.getBody(), Employee.class);
        Assert.assertEquals(responseEmp.getId().intValue(), 1);

    }

    @Test
    public void test_5_testGetNotPresentEmployee() throws Exception {
        ResponseEntity<String> response = testRestTemplate.getForEntity(createURLWithPort("/v1/bfs/employees/2"), String.class);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
        Assert.assertEquals(response.getBody(), null);

    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
