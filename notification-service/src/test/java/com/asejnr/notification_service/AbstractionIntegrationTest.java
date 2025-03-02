package com.asejnr.notification_service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestcontainersConfiguration.class)
public abstract class AbstractionIntegrationTest {
    //    @MockitoBean
    //    protected NotificationService notificationService;
}
