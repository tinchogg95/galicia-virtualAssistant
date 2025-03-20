package galicia.challenge.virtualAssistant.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AssistantServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AssistantService assistantService;

    @BeforeEach
    public void setUp() {
        // Configurar respuestas simuladas para los mocks de RestTemplate con los endpoints de localhost
        lenient().when(restTemplate.getForObject(eq("http://localhost:8083/api/weather/clima"), eq(String.class)))
                .thenReturn("El clima está soleado.");
        
        lenient().when(restTemplate.getForObject(eq("http://localhost:8082/api/time/hora"), eq(String.class)))
                .thenReturn("La hora actual es: 2025-03-18 00:06:28");

        lenient().when(restTemplate.getForObject(eq("http://localhost:8081/api/dollar/oficial"), eq(String.class)))
                .thenReturn("{\"compra\": 1040.00, \"venta\": 1099.00}");

        lenient().when(restTemplate.getForObject(eq("http://localhost:8081/api/dollar/blue"), eq(String.class)))
                .thenReturn("{\"compra\": 2000.00, \"venta\": 2050.00}");
    }

    @Test
    public void testHandleQuestion_Weather() {
        String result = assistantService.handleQuestion("¿Cuál es el clima?");
        System.out.println("--------------------" + result);
        System.out.println("--------------------" + assistantService.getRestTemplate());

        assertEquals("El clima está soleado.", result);
    }

    @Test
    public void testHandleQuestion_Time() {
        String result = assistantService.handleQuestion("¿Qué hora es?");
        assertEquals("La hora actual es: 2025-03-18 00:06:28", result);
    }

    @Test
    public void testHandleQuestion_DollarOfficial() {
        String result = assistantService.handleQuestion("¿Cuál es el dólar oficial?");
        assertEquals("{\"compra\": 1040.00, \"venta\": 1099.00}", result);
    }

    @Test
    public void testHandleQuestion_DollarBlue() {
        String result = assistantService.handleQuestion("¿Cuál es el dólar blue?");
        assertEquals("{\"compra\": 2000.00, \"venta\": 2050.00}", result);
    }

    @Test
    public void testHandleQuestion_Error() {
        String result = assistantService.handleQuestion("Pregunta no válida");
        assertEquals("Lo siento, ahora no podemos responderte esa pregunta :( Ocurrió un error inesperado al procesar tu solicitud", result);
    }

    @Test
    public void testHandleQuestion_DollarOfficial_Success() {
        String result = assistantService.handleQuestion("¿Cuál es el valor del dólar oficial?");
        assertNotNull(result);  // Comprobar que no sea null
    }

    @Test
    public void testHandleQuestion_DollarBlue_Success() {
        String result = assistantService.handleQuestion("¿Cuál es el valor del dólar blue?");
        assertNotNull(result);  // Comprobar que no sea null
    }
}
