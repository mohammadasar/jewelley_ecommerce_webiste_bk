package com.example.jewellery.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        
        config.setAllowedOrigins(List.of(
        	    "http://localhost:8080",
        	    "http://127.0.0.1:5501",
        	    "http://localhost:5173",
        	    "http://127.0.0.1:5500"
        	   
        	));
       
//        config.setAllowedOrigins(List.of("https://devwerxoil.netlify.app")); // ✅ Allow your Netlify domain
        config.setAllowedHeaders(List.of("Origin", "Content-Type", "Accept", "Authorization","X-Requested-With"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

//         Allow cookies to flow both ways
        config.setExposedHeaders(List.of("Set-Cookie")); // crucial for browser to store cookie
          
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}