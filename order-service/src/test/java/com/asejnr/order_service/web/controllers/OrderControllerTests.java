package com.asejnr.order_service.web.controllers;

import com.asejnr.order_service.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

@Sql("/test-orders.sql")
class OrderControllerTests extends AbstractIntegrationTest {

    @Nested
    class CreateOrderTests {
        @Test
        void shouldCreateOrderSuccessfully() {
            var payload =
                    """
                        {
                            "customer" : {
                                "name": "Felix",
                                "email": "felix@gmail.com",
                                "phone": "712345678"
                            },
                            "deliveryAddress" : {
                                "addressLine1": "Mombasa Rd, Nairobi",
                                "addressLine2": "Westlands",
                                "city": "Nairobi",
                                "state": "Nairobi",
                                "zipCode": "00100",
                                "country": "Kenya"
                            },
                            "items": [
                                {
                                    "code": "P100",
                                    "name": "Product 1",
                                    "price": 25.50,
                                    "quantity": 1
                                }
                            ]
                        }
                    """;
            given().contentType(ContentType.JSON)
//                    .header("Authorization", "Bearer " + getToken())
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

//        @Test
//        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
//            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
//            given().contentType(ContentType.JSON)
//                    .header("Authorization", "Bearer " + getToken())
//                    .body(payload)
//                    .when()
//                    .post("/api/orders")
//                    .then()
//                    .statusCode(HttpStatus.BAD_REQUEST.value());
//        }
    }

//    @Nested
//    class GetOrdersTests {
//        @Test
//        void shouldGetOrdersSuccessfully() {
//            List<OrderSummary> orderSummaries = given().when()
//                    .header("Authorization", "Bearer " + getToken())
//                    .get("/api/orders")
//                    .then()
//                    .statusCode(200)
//                    .extract()
//                    .body()
//                    .as(new TypeRef<>() {});
//
//            assertThat(orderSummaries).hasSize(2);
//        }
//    }

//    @Nested
//    class GetOrderByOrderNumberTests {
//        String orderNumber = "order-123";
//
//        @Test
//        void shouldGetOrderSuccessfully() {
//            given().when()
//                    .header("Authorization", "Bearer " + getToken())
//                    .get("/api/orders/{orderNumber}", orderNumber)
//                    .then()
//                    .statusCode(200)
//                    .body("orderNumber", is(orderNumber))
//                    .body("items.size()", is(2));
//        }
//    }
}

