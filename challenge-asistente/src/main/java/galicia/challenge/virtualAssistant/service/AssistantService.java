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

    public AssistantService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String handleQuestion(String question) {
        String lowerCaseQuestion = question.toLowerCase();
    
        // Verificar palabras clave
        if (lowerCaseQuestion.contains(AssistantConstants.KEY_HORA)) {
            return getApiResponse(AssistantConstants.API_TIME_URL);
        } else if (lowerCaseQuestion.contains(AssistantConstants.KEY_CLIMA)) {
            return getApiResponse(AssistantConstants.API_WEATHER_URL);
        } else if (lowerCaseQuestion.contains(AssistantConstants.KEY_DOLAR_OF)) {
            return getApiResponse(AssistantConstants.API_DOLLAR_OFFICIAL_URL);
        } else if (lowerCaseQuestion.contains(AssistantConstants.KEY_DOLAR_BL)) {
            return getApiResponse(AssistantConstants.API_DOLLAR_BLUE_URL);
        } else {
            return queryDialogflow(question);
        }
    }
    
    private String getApiResponse(String apiUrl) {
        try {
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            return AssistantConstants.ERROR_LOCAL_SERVER;
        }
    }

    public String queryDialogflow(String question) {  
    
        String sessionId = UUID.randomUUID().toString();
        String url = AssistantConstants.BASE_DIALOGFLOW_URL + AssistantConstants.PROJECT_ID + "/agent/sessions/" + sessionId + ":detectIntent";
    
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AssistantConstants.TOKEN); // Usar el token desde AssistantConstants
        headers.setContentType(MediaType.APPLICATION_JSON);
    
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody;
        try {
            Map<String, Object> textMap = new HashMap<>();
            textMap.put(AssistantConstants.KEY_TEXT, question);
            textMap.put(AssistantConstants.KEY_LANGUAGE_CODE, AssistantConstants.DIALOGFLOW_LANGUAGE_CODE);
    
            Map<String, Object> queryInputMap = new HashMap<>();
            queryInputMap.put(AssistantConstants.KEY_TEXT, textMap);
    
            Map<String, Object> requestMap = new HashMap<>();
            requestMap.put(AssistantConstants.KEY_QUERY_INPUT, queryInputMap);
    
            requestBody = objectMapper.writeValueAsString(requestMap);
        } catch (Exception e) {
            return AssistantConstants.ERROR_JSON_CREATION + e.getMessage();
        }
    
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
    
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            JsonNode rootNode = objectMapper.readTree(response.getBody());
            String result = rootNode.path("queryResult").path("fulfillmentText").asText();
            String return_string = (result.isEmpty() ? AssistantConstants.NOT_FOUND : result);
            return return_string;
        } catch (HttpClientErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            return AssistantConstants.ERROR_REQUEST + e.getStatusCode() + " - " + (responseBody != null ? responseBody : "No response body");
        } catch (HttpServerErrorException e) {
            String responseBody = e.getResponseBodyAsString();
            return AssistantConstants.ERROR_SERVER + e.getStatusCode() + " - " + (responseBody != null ? responseBody : "No response body");
        } catch (Exception e) {
            return AssistantConstants.ERROR_UNKNOWN + e.getMessage();
        }
    }
}