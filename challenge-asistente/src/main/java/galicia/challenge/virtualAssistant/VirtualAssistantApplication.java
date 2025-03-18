package galicia.challenge.virtualAssistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "galicia.challenge.virtualAssistant")
public class VirtualAssistantApplication {

    public static void main(String[] args) {
        SpringApplication.run(VirtualAssistantApplication.class, args);
        System.out.println("✅ Aplicación iniciada correctamente.");
    }
}
