package reqres;

import models.lombok.RegistrationBodyLombokModel;
import models.lombok.UserBodyLombokModel;
import models.pojo.RegistrationBodyPojoModel;
import models.pojo.UserBodyPojoModel;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static specs.BaseSpecs.baseRequestSpec;
import static specs.BaseSpecs.baseResponseSpec;
import static specs.CreateSpecs.createRequestSpec;
import static specs.CreateSpecs.createResponseSpec;
import static specs.RegistrationSpecs.registrationRequestSpec;
import static specs.RegistrationSpecs.registrationResponseSpec;
import static specs.UpdateSpecs.updateSpecs;

public class InReqresTests {
    @Test
    void registrationWithPojoTest() {
        RegistrationBodyPojoModel registrationBody = new RegistrationBodyPojoModel();
        registrationBody.setEmail("eve.holt@reqres.in");
        registrationBody.setPassword("pistol");

        given()
                .spec(registrationRequestSpec)
                .body(registrationBody)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void registrationWithLombokTest() {
        RegistrationBodyLombokModel registrationBody = new RegistrationBodyLombokModel();
        registrationBody.setEmail("eve.holt@reqres.in");
        registrationBody.setPassword("pistol");

        given()
                .spec(registrationRequestSpec)
                .body(registrationBody)
                .when()
                .post()
                .then()
                .spec(registrationResponseSpec)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
    @Test
    void updateWithPojoTest() {
        UserBodyPojoModel userBody = new UserBodyPojoModel();
        userBody.setName("morpheus");
        userBody.setJob("leader");
        given()
                .spec(createRequestSpec)
                .body(userBody)
                .when()
                .put("/2")
                .then()
                .spec(updateSpecs)
                .body("name", is(userBody.getName()))
                .body("job", is(userBody.getJob()));
    }
    @Test
    void updateWithLombokTest() {
        UserBodyLombokModel userBody = new UserBodyLombokModel();
        userBody.setName("morpheus");
        userBody.setJob("leader");
        given()
                .spec(createRequestSpec)
                .body(userBody)
                .when()
                .put("/2")
                .then()
                .spec(updateSpecs)
                .body("name", is(userBody.getName()))
                .body("job", is(userBody.getJob()));
    }
    @Test
    void createPojoTest() {
        UserBodyPojoModel userBody = new UserBodyPojoModel();
        userBody.setName("morpheus");
        userBody.setJob("leader");

        given()
                .spec(createRequestSpec)
                .body(userBody)
                .when()
                .post()
                .then()
                .spec(createResponseSpec)
                .body("name", is(userBody.getName()))
                .body("job", is(userBody.getJob()));
    }
    @Test
    void createLombokTest() {
        UserBodyLombokModel userBody = new UserBodyLombokModel();
        userBody.setName("morpheus");
        userBody.setJob("leader");

        given()
                .spec(createRequestSpec)
                .body(userBody)
                .when()
                .post()
                .then()
                .spec(createResponseSpec)
                .body("name", is(userBody.getName()))
                .body("job", is(userBody.getJob()));
    }
    @Test
    void deleteTest() {
        given()
                .spec(baseRequestSpec)
                .when()
                .delete("/api/users/2")
                .then()
                .spec(baseResponseSpec)
                .statusCode(204);
    }
    @Test
    void listUsersTest() {
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(baseResponseSpec)
                .statusCode(200);
    }
    @Test
    void listUsersWithGroovyTest() {
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(baseResponseSpec)
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("michael.lawson@reqres.in"))
                .body("data.first_name.flatten()",
                        hasItem("Lindsay"))
                .body("data.last_name.flatten()",
                        hasItem("Edwards"))
                .body("data.findAll{it.avatar =~/.*?.jpg/}.avatar.flatten()",
                        hasItem("https://reqres.in/img/faces/12-image.jpg"));
    }


    @Test
    void idUserTest() {
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(baseResponseSpec)
                .statusCode(200)
                .body("data.id", hasItems(7, 8, 9, 10, 11, 12));
    }
    @Test
    void idUserWithGroovyTest() {
        given()
                .spec(baseRequestSpec)
                .when()
                .get("/api/users?page=2")
                .then()
                .spec(baseResponseSpec)
                .statusCode(200)
                .body("data.id.findAll{it>1}", hasItems(7, 8, 9, 10, 11, 12));
    }

}

