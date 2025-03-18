package galicia.challenge.virtualAssistant.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppConfigTest {

    private AppConfig appConfig;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        appConfig = new AppConfig();
        restTemplate = appConfig.restTemplate();
    }

    @Test
    void testRestTemplateConfiguration() {
        assertNotNull(restTemplate);//evaluar que se haya seteado y no este nulo
        DefaultResponseErrorHandler errorHandler = (DefaultResponseErrorHandler) restTemplate.getErrorHandler();
        assertNotNull(errorHandler);//chequear si el manejador de errores no es nulo
    }

    @Test
    void testCustomErrorHandlerOnForbidden() throws IOException {
        ClientHttpResponse response = mock(ClientHttpResponse.class);
        when(response.getStatusCode()).thenReturn(HttpStatus.FORBIDDEN);//prueba de forbidden

        DefaultResponseErrorHandler errorHandler = (DefaultResponseErrorHandler) restTemplate.getErrorHandler(); 
        //se verifica que no se llama al handler por el status FORBIDDEN

        errorHandler.handleError(response);
    }

    @Test
    void testCustomErrorHandlerOnOtherStatus() throws IOException {
        ClientHttpResponse response = mock(ClientHttpResponse.class);
        when(response.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);//validacion bad request

      
    }
}
