package galicia.challenge.virtualAssistant.controller;

import galicia.challenge.virtualAssistant.service.AssistantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {

    private final AssistantService assistantService;

    public AssistantController(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @Operation(
        summary = "Interactuar con el chatbot",
        description = "Envía una pregunta o un saludo al chatbot. Puede responder sobre el clima, la hora, el valor del dólar oficial o del dólar blue."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Respuesta exitosa del chatbot",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(value = "{\"response\": \"Estoy perfecto, gracias!\"}")
            )),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida", content = @Content),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    })
    @PostMapping("/ask")
    public ResponseEntity<Map<String, String>> handleQuestion(
        @RequestBody(
            description = "Mensaje que se envía al chatbot",
            required = true,
            content = @Content(
                mediaType = "application/json",
                examples = {
                    @ExampleObject(name = "Saludo", value = "\"Hola, ¿cómo estás?\""),
                    @ExampleObject(name = "Pregunta sobre el clima", value = "\"¿Cómo está el clima hoy?\""),
                    @ExampleObject(name = "Consulta sobre el dólar", value = "\"¿Cuánto está el dólar blue?\"")
                }
            )
        )
        @org.springframework.web.bind.annotation.RequestBody String question // Esta es la anotación de Spring
    ) {
        String response = assistantService.handleQuestion(question);
       return ResponseEntity.ok(Collections.singletonMap("response", response));
    }
}
