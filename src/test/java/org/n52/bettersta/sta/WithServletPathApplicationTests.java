package org.n52.bettersta.sta;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                properties = "spring.mvc.servlet.path=/someServletPath")
class WithServletPathApplicationTests extends AbstractApplicationTests {}
