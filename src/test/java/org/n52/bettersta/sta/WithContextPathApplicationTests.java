package org.n52.bettersta.sta;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                properties = "server.servlet.context-path=/someContextPath")
class WithContextPathApplicationTests extends AbstractApplicationTests {}
