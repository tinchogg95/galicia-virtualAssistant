package galicia.challenge.virtualAssistant.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AssistantService {

    private final RestTemplate restTemplate;
    private static final String BASE_DIALOGFLOW_URL = "https://dialogflow.googleapis.com/v2/projects/";
    private static final String PROJECT_ID = "invertible-tree-454007-m1";
    private static final String DIALOGFLOW_ACCESS_TOKEN_ENV = "DIALOGFLOW_ACCESS_TOKEN";
    private static final String DIALOGFLOW_LANGUAGE_CODE = "es";
    private static final String API_TIME_URL = "http://localhost:8082/api/time/current";
    private static final String API_WEATHER_URL = "http://localhost:8083/api/weather/current";
    private static final String API_DOLLAR_OFFICIAL_URL = "http://localhost:8081/api/dollar/official";
    private static final String API_DOLLAR_BLUE_URL = "http://localhost:8081/api/dollar/blue";
    
    private static final String NOT_FOUND = "No entiendo la pregunta.";
    private static final String ERROR_TOKEN_NOT_FOUND = "Error: No se encontró el token de autenticación.";
    private static final String ERROR_JSON_CREATION = "Error al generar la solicitud JSON: ";
    private static final String ERROR_REQUEST = "Error en la solicitud: ";
    private static final String ERROR_SERVER = "Error del servidor de Dialogflow: ";
    private static final String ERROR_UNKNOWN = "Error desconocido al procesar la solicitud: ";
    static final String ERROR_LOCAL_SERVER = "Lo siento, ahora no podemos responderte esa pregunta :( Ocurrió un error inesperado al procesar tu solicitud";

    public AssistantService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String handleQuestion(String question) {
        String lowerCaseQuestion = question.toLowerCase();
    
        // Verificar palabras clave
        if (lowerCaseQuestion.contains("hora")) {
            return getApiResponse(API_TIME_URL);
        } else if (lowerCaseQuestion.contains("clima")) {
            return getApiResponse(API_WEATHER_URL);
        } else if (lowerCaseQuestion.contains("dolar oficial")) {
            return getApiResponse(API_DOLLAR_OFFICIAL_URL);
        } else if (lowerCaseQuestion.contains("dolar blue")) {
            return getApiResponse(API_DOLLAR_BLUE_URL);
        } else {
            return queryDialogflow(question);
        }
    }
    
    private String getApiResponse(String apiUrl) {
        try {
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            return ERROR_LOCAL_SERVER;
        }
    }

    public String queryDialogflow(String question) {
        String token = "ya29.c.c0ASRK0GYWoVvolmozkhwfrYX21add38tache26KAcMv-ENfpZZbpWBh6PWftWfLy1oKmajYZybuIrKzS8Tdmmpj-T37-L44X_15G6EqHAJ9qIqIDmxDpAcKVMzg27qHYULqLVeHOxEhRD7tyrkQoRfYYBBFd4kD7Qddktv2GtCNn0x4iQFNonNjdtRY_fq7JwpEPpxr0P8XHeNN6bhk1XBZVZokvKA9EiJEMD7kVuMooQHlN5AMOSaK-8d3Gg8HQUYvCP68cwNLLQZH8ZHyQALMeQGS90idToE760BH-pOJVZnuEP1cN2ieORCYvf2zHKvD-PdZbnnuDPVc_09aztYfcpbodPc-2uLDEecCdKmWa5vE1UjuiOHconPDIL388CwIcwB0nXlSm2BzpXeUwqXB4eVfSW641Y91z6Q--86qatmVVBbgUzrmuawWn-3lYkkkVliRj9bmUyOduVXVw3zoQlxFvSux1Yxi3sBU5sFbvcfzv048aIXoQsrjgZcvBZ9Fx_898_0b4WoBBVMx3XV-y5bFhWup4J9s0iugir-7x6h0eF28Yhms-vRX8nMFM2Y-MuwbaeZ1s_o0vuQqmR1WWg_1euVVz2ZxlQvjh_bVBkSppdU1O4tkqIizJRxz714qQtzZvZ8390XcSpxaScrjWJfvgxc9m0s5Fw2BtI_hjw1wicqc2phSjFlnYJ7yx56yoQ5W6zS_q0iR95uBJvo5JvkZwpnSFuswO_77IxWZhFd_I5agF58j4McdmVv4o5vmUcrs-eac1l2xOMtRmgOIMhcBg53fe81Xw-YO9k54ach7Fn8fchuBIVtp0Zqpa9zZbZMw0Xhmtnt7VduMws9yZulMFwU1ujFf_297ifI428Yx5ghSBSqOryFMYmZZMeqXvmlgSbyaiXJYd556ySo8Bqbz6Oar411f1Uaph9ifk_rF9u6oa4nubj0_JuZcSJc2OFV2jcSzY0h1p_shp0iwMe5gs-sptgnhtqj1R-c6Xnw4ahXbsqWc_";  
    
        String sessionId = UUID.randomUUID().toString();
        String url = BASE_DIALOGFLOW_URL + PROJECT_ID + "/agent/sessions/" + sessionId + ":detectIntent";
    
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); 
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            Map<String, Object> textMap = new HashMap<>();
            textMap.put("text", question);
            textMap.put("languageCode", DIALOGFLOW_LANGUAGE_CODE);
    
            Map<String, Object> queryInputMap = new HashMap<>();
            queryInputMap.put("text", textMap);
    
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put("queryInput", queryInputMap);
    
            requestBody = objectMapper.writeValueAsString(requestMap);
        } catch (Exception e) {
            return ERROR_JSON_CREATION + e.getMessage();
        }
    
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String result = rootNode.path("queryResult").path("fulfillmentText").asText();
            String return_string = (result.isEmpty()?NOT_FOUND:result);
            return return_string;
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            return ERROR_REQUEST + e.getStatusCode() + " - " + (responseBody != null ? responseBody : "No response body");
        } catch (HttpServerErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            return ERROR_SERVER + e.getStatusCode() + " - " + (responseBody != null ? responseBody : "No response body");
        } catch (Exception e) {
            return ERROR_UNKNOWN + e.getMessage();
        }
    }
}
