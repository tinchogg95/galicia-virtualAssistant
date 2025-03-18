package galicia.challenge.virtualAssistant.controller;

import galicia.challenge.virtualAssistant.service.AssistantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {

    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @PostMapping("/ask")
    public ResponseEntity<String> handleQuestion(@RequestBody String question) {
        String response = assistantService.handleQuestion(question);
        return ResponseEntity.ok(response);
    }
}
