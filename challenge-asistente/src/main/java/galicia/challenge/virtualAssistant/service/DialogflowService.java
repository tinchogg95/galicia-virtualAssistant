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
public class DialogflowService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DialogflowService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String queryDialogflow(String question) {
        String sessionId = UUID.randomUUID().toString();
        String url = AssistantConstants.BASE_DIALOGFLOW_URL + AssistantConstants.PROJECT_ID + "/agent/sessions/" + sessionId + ":detectIntent";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(AssistantConstants.TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody;
        try {
            requestBody = buildRequestBody(question);
        } catch (Exception e) {
            return AssistantConstants.ERROR_JSON_CREATION + e.getMessage();
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
            return extractResponse(response.getBody());
        } catch (HttpClientErrorException e) {
            return AssistantConstants.ERROR_REQUEST + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (HttpServerErrorException e) {
            return AssistantConstants.ERROR_SERVER + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        } catch (Exception e) {
            return AssistantConstants.ERROR_UNKNOWN + e.getMessage();
        }
    }

    private String buildRequestBody(String question) throws Exception {
        Map<String, Object> textMap = new HashMap<>();
        textMap.put(AssistantConstants.KEY_TEXT, question);
        textMap.put(AssistantConstants.KEY_LANGUAGE_CODE, AssistantConstants.DIALOGFLOW_LANGUAGE_CODE);

        Map<String, Object> queryInputMap = new HashMap<>();
        queryInputMap.put(AssistantConstants.KEY_TEXT, textMap);

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put(AssistantConstants.KEY_QUERY_INPUT, queryInputMap);

        return objectMapper.writeValueAsString(requestMap);
    }

    private String extractResponse(String responseBody) throws Exception {
        JsonNode rootNode = objectMapper.readTree(responseBody);
        String result = rootNode.path("queryResult").path("fulfillmentText").asText();
        return result.isEmpty() ? AssistantConstants.NOT_FOUND : result;
    }
}
