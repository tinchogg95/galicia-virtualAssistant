package galicia.challenge.virtualAssistant.service;

import galicia.challenge.virtualAssistant.config.AppConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AssistantService {

    private final AppConfig appConfig;

    private RestTemplate restTemplate;
    private final DialogflowService dialogflowService;

    public AssistantService(RestTemplate restTemplate, DialogflowService dialogflowService, AppConfig appConfig) {
        this.restTemplate = restTemplate;
        this.dialogflowService = dialogflowService;
        this.appConfig = appConfig;
    }

    public String handleQuestion(String question) {
        String lowerCaseQuestion = question.toLowerCase();

        if (lowerCaseQuestion.contains(AssistantConstants.KEY_HORA)) {
            return getApiResponse(AssistantConstants.API_TIME_URL);
        } else if (lowerCaseQuestion.contains(AssistantConstants.KEY_CLIMA)) {
            return getApiResponse(AssistantConstants.API_WEATHER_URL);
        } else if (lowerCaseQuestion.contains(AssistantConstants.KEY_DOLAR_OF)) {
            return getApiResponse(AssistantConstants.API_DOLLAR_OFFICIAL_URL);
        } else if (lowerCaseQuestion.contains(AssistantConstants.KEY_DOLAR_BL)) {
            return getApiResponse(AssistantConstants.API_DOLLAR_BLUE_URL);
        } else {
            return dialogflowService.queryDialogflow(question);
        }
    }

    private String getApiResponse(String apiUrl) {
        try {
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            return AssistantConstants.ERROR_LOCAL_SERVER;
        }
    }

    public void setRestTemplate(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public DialogflowService getDialogflowService() {
        return dialogflowService;
    }

    

    
}
