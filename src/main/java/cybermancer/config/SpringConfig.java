package cybermancer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { DaoConfig.class })
public class SpringConfig {

}
