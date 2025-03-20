package galicia.challenge.virtualAssistant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DialogflowServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private DialogflowService dialogflowService;

    private static final String DIALOGFLOW_RESPONSE = "Respuesta de Dialogflow";
    private static final String ERROR_REQUEST = "Error en la solicitud: 400 BAD_REQUEST - ";
    private static final String ERROR_SERVER = "Error del servidor de Dialogflow: 500 INTERNAL_SERVER_ERROR - ";
    private static final String ERROR_UNKNOWN = "Error desconocido al procesar la solicitud: Unknown Error";

    @Test
    public void testQueryDialogflow_Success() {
        String mockResponse = "{\"queryResult\": {\"fulfillmentText\": \"" + DIALOGFLOW_RESPONSE + "\"}}";

        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        String response = dialogflowService.queryDialogflow("¿Cómo estás?");
        assertEquals(DIALOGFLOW_RESPONSE, response);
    }

    @Test
    public void testQueryDialogflow_HttpClientError() {
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request"));

        String response = dialogflowService.queryDialogflow("¿Cómo estás?");
        assertEquals(ERROR_REQUEST, response);
    }

    @Test
    public void testQueryDialogflow_HttpServerError() {
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"));

        String response = dialogflowService.queryDialogflow("¿Cómo estás?");
        assertEquals(ERROR_SERVER, response);
    }

    @Test
    public void testQueryDialogflow_UnknownError() {
        when(restTemplate.exchange(anyString(), any(), any(), any(Class.class)))
                .thenThrow(new RuntimeException("Unknown Error"));

        String response = dialogflowService.queryDialogflow("¿Cómo estás?");
        assertEquals(ERROR_UNKNOWN, response);
    }
}
