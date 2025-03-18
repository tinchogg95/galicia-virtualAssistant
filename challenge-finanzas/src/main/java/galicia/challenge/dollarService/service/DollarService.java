package galicia.challenge.dollarService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class DollarService {

    private static final String BLUELYTICS_API_URL = "https://api.bluelytics.com.ar/v2/latest";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public DollarService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public String getDollarBluePrice() {
        return getDollarPriceByType("blue", "dólar blue");
    }

    public String getDollarPrice() {
        return getDollarPriceByType("oficial", "dólar oficial");
    }

    private String getDollarPriceByType(String type, String label) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
            headers.set("Accept", "application/json");
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> responseEntity = restTemplate.exchange(BLUELYTICS_API_URL, HttpMethod.GET, entity, String.class);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String responseString = responseEntity.getBody();
                Map<String, Object> response = objectMapper.readValue(responseString, Map.class);

                if (response.containsKey(type)) {
                    Map<String, Object> rates = (Map<String, Object>) response.get(type);

                    double buyPrice = parseDouble(rates.get("value_buy"));
                    double sellPrice = parseDouble(rates.get("value_sell"));

                    return String.format("El precio del %s es: Compra = $%.2f, Venta = $%.2f", label, buyPrice, sellPrice);
                }
            }
        } catch (Exception e) {
            return "Error al obtener el precio del " + label + ": " + e.getMessage();
        }
        return "No se pudo obtener el precio del " + label + " en este momento.";
    }

    private double parseDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0.0;
    }
}
