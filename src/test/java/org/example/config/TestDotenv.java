package org.example.config;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;

public class TestDotenv {

    @Test
    public void testDotenv() {
        // Load the .env file
        Dotenv dotenv = Dotenv.load();

        // Iterate over all key-value pairs and print them
        dotenv.entries().forEach(entry -> {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        });
    }
}
