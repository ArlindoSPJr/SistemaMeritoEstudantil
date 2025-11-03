package lab.dev.meritoEstudantil.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		final String securitySchemeName = "bearerAuth";
		return new OpenAPI()
				.addServersItem(new Server().url("/"))
				.components(new Components()
						.addSecuritySchemes(securitySchemeName, new SecurityScheme()
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
						)
				)
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
				.info(new Info()
						.title("Mérito Estudantil API")
						.version("v1")
				);
	}
}


