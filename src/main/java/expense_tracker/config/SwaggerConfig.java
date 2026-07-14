package expense_tracker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Personal Expense Tracker API")
                        .version("1.0")
                        .description("RESTful backend API for managing personal finances. Features include multi-tenant custom categories, pagination, and date-range filtering.")
                        .contact(new Contact()
                                .name("Litheesh Kannaa D")
                                .email("litheeshkannaa@gmail.com")
                                .url("https://github.com/Litheesh-kannaa/expense-tracker-backend")));
    }
}