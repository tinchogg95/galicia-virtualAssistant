package galicia.challenge.dollarService.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import galicia.challenge.dollarService.service.DollarService;

@RestController
@RequestMapping("/api/dollar")
public class DollarController {

    private final DollarService dollarService;

    public DollarController(DollarService dollarService) {
        this.dollarService = dollarService;
    }

    @Operation(
        summary = "Obtener el precio del dólar oficial",
        description = "Devuelve el precio de compra y venta del dólar oficial."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Precio del dólar oficial obtenido correctamente."),
        @ApiResponse(responseCode = "500", description = "Error al consultar el precio del dólar.")
    })
    @GetMapping("/official")
    public String getOfficialDollarPrice() {
        return dollarService.getDollarPrice();
    }

    @Operation(
        summary = "Obtener el precio del dólar blue",
        description = "Devuelve el precio de compra y venta del dólar blue."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Precio del dólar blue obtenido correctamente."),
        @ApiResponse(responseCode = "500", description = "Error al consultar el precio del dólar.")
    })
    @GetMapping("/blue")
    public String getBlueDollarPrice() {
        return dollarService.getDollarBluePrice();
    }
}