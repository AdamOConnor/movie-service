package edu.tus.movieservice.dto.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("library-service")
@Component
public class Config {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    private String environment;

    private String version;

    private String connectionDB;

    public String getActiveProfile() {
        return activeProfile;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getConnectionDB() {
        return connectionDB;
    }

    public void setConnectionDB(String connectionDB) {
        this.connectionDB = connectionDB;
    }
}

