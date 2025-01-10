package common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;

public abstract class ControllerTestSupport {

    protected final MockMvc mockMvc;
    protected final ObjectMapper objectMapper;

    protected ControllerTestSupport(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }
}
