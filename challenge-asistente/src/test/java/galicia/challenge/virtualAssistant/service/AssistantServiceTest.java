package galicia.challenge.virtualAssistant.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssistantServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private AssistantService assistantService;

    // Constantes de prueba
    private static final String TIME_RESPONSE = "La hora actual es: 2025-03-18 00:06:28";
    private static final String WEATHER_RESPONSE = "El clima está soleado.";
    private static final String DOLLAR_OFFICIAL_RESPONSE = "{\"compra\": 1040.00, \"venta\": 1099.00}";
    private static final String DOLLAR_BLUE_RESPONSE = "{\"compra\": 2000.00, \"venta\": 2050.00}";
    private static final String DIALOGFLOW_RESPONSE = "Respuesta de Dialogflow";
    private static final String ERROR_LOCAL_SERVER = "Lo siento, ahora no podemos responderte esa pregunta :( Ocurrió un error inesperado al procesar tu solicitud";
    private static final String ERROR_REQUEST = "Error en la solicitud: 400 BAD_REQUEST - ";
    private static final String ERROR_SERVER = "Error del servidor de Dialogflow: 500 INTERNAL_SERVER_ERROR - ";
    private static final String ERROR_UNKNOWN = "Error desconocido al procesar la solicitud: Unknown Error";

    @Test
    public void testHandleQuestion_Time() {
        lenient().when(restTemplate.getForObject(anyString(), any())).thenReturn(TIME_RESPONSE);

        String response = assistantService.handleQuestion("¿Qué hora es?");
        assertEquals(TIME_RESPONSE, response);
    }

    @Test
    public void testHandleQuestion_Weather() {
        lenient().when(restTemplate.getForObject(anyString(), any())).thenReturn(WEATHER_RESPONSE);

        String response = assistantService.handleQuestion("¿Cómo está el clima?");
        assertEquals(WEATHER_RESPONSE, response);
    }

    @Test
    public void testHandleQuestion_DollarOfficial_Success() {
        lenient().when(restTemplate.getForObject(anyString(), any())).thenReturn(DOLLAR_OFFICIAL_RESPONSE);

        String response = assistantService.handleQuestion("¿Cuál es el valor del dólar oficial?");
        assertNotNull(response); // Verifica que la respuesta no sea nula
        assertNotEquals(ERROR_LOCAL_SERVER, response); // Verifica que no sea un mensaje de error
    }

    @Test
    public void testHandleQuestion_DollarBlue_Success() {
        lenient().when(restTemplate.getForObject(anyString(), any())).thenReturn(DOLLAR_BLUE_RESPONSE);

        String response = assistantService.handleQuestion("¿Cuál es el valor del dólar blue?");
        assertNotNull(response); // Verifica que la respuesta no sea nula
        assertNotEquals(ERROR_LOCAL_SERVER, response); // Verifica que no sea un mensaje de error
    }

    @Test
    public void testHandleQuestion_Dialogflow() {
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenReturn(new ResponseEntity<>("{\"queryResult\": {\"fulfillmentText\": \"" + DIALOGFLOW_RESPONSE + "\"}}", HttpStatus.OK));

        String response = assistantService.handleQuestion("¿Cómo estás?");
        assertEquals(DIALOGFLOW_RESPONSE, response);
    }

    @Test
    public void testHandleQuestion_Error() {
        lenient().when(restTemplate.getForObject(anyString(), any())).thenThrow(new RuntimeException());

        String response = assistantService.handleQuestion("¿Qué hora es?");
        assertEquals(ERROR_LOCAL_SERVER, response);
    }

    @Test
    public void testQueryDialogflow_HttpClientError() {
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request"));
    
        String response = assistantService.queryDialogflow("¿Cómo estás?");
        assertEquals(ERROR_REQUEST, response);
    }
    
    @Test
    public void testQueryDialogflow_HttpServerError() {
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"));
    
        String response = assistantService.queryDialogflow("¿Cómo estás?");
        assertEquals(ERROR_SERVER, response);
    }

    @Test
    public void testQueryDialogflow_UnknownError() {
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenThrow(new RuntimeException("Unknown Error"));

        String response = assistantService.queryDialogflow("¿Cómo estás?");
        assertEquals(ERROR_UNKNOWN, response);
    }
}
