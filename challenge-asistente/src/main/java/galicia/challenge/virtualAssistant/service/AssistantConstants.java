package galicia.challenge.virtualAssistant.service;

public class AssistantConstants {

    // URLs
    public static final String BASE_DIALOGFLOW_URL = "https://dialogflow.googleapis.com/v2/projects/";
    public static final String PROJECT_ID = "invertible-tree-454007-m1";
    public static final String DIALOGFLOW_LANGUAGE_CODE = "es";
    public static final String API_TIME_URL = "http://localhost:8082/api/time/current";
    public static final String API_WEATHER_URL = "http://localhost:8083/api/weather/current";
    public static final String API_DOLLAR_OFFICIAL_URL = "http://localhost:8081/api/dollar/official";
    public static final String API_DOLLAR_BLUE_URL = "http://localhost:8081/api/dollar/blue";

    // Mensajes de error
    public static final String NOT_FOUND = "No entiendo la pregunta.";
    public static final String ERROR_JSON_CREATION = "Error al generar la solicitud JSON: ";
    public static final String ERROR_REQUEST = "Error en la solicitud: ";
    public static final String ERROR_SERVER = "Error del servidor de Dialogflow: ";
    public static final String ERROR_UNKNOWN = "Error desconocido al procesar la solicitud: ";
    public static final String ERROR_LOCAL_SERVER = "Lo siento, ahora no podemos responderte esa pregunta :( Ocurrió un error inesperado al procesar tu solicitud";

    // Claves
    public static final String KEY_HORA = "hora";
    public static final String KEY_CLIMA = "clima";
    public static final String KEY_DOLAR_OF = "dolar oficial";
    public static final String KEY_DOLAR_BL = "dolar blue";
    public static final String KEY_TEXT = "text";
    public static final String KEY_LANGUAGE_CODE = "languageCode";
    public static final String KEY_QUERY_INPUT = "queryInput";

    // Token de autenticación
    public static final String TOKEN = "ya29.c.c0ASRK0GZZ0paTL2jeuVV3ShcIQ34aAkRkJKg5ukssXQYW3_ol8uNRKljwTjEw7BopxeY28PaEnOhi5cHnI06q0uexuJ8jiLk2vHpTZJ9-wmAWht1rRVQrigvzdKcUsKvv_fLeMPWuXoudvKESQ9uUoSnXnRrtdbY8y6pbgeXBpI4cJvHxHsk2SdAew2KNcJclqXl6J8VRV3-6pmk4Bs5f4vp-Ec-syW-dzch5kVFjp4MPCiUa1DIKUWQsHVwkstLqjHDaHL784rOr9ibU9gD-K7vlMt-e5lHK_ejxe_ZE0Q7vlYRXaidVi5vpaxvz5qpWh3uLloIR8sEke09Z9NZ_U9mM4sjoz_rI3cyEMUNc7HqPgTWS-QQ_f4f-6wN387Cydd71yyi7gid3BYvdnJtW662lezc0d5m6txx7wQJw5YoFIxmjQwzgVwqdUSW87yIfo3pYop57QwU14zQpQQvx3uVctdzdyeyMM0xbapqYWSduW6a1akZp5Z2old__OybSxtBoMUj9j9-2uof3mkJeMQn34cMp1J3vI6_vhF8wXS8p6-9XMwgqW7oISRkRop22n1yQ6U0F4IU_WbQ1pYZsF8X4YxnQhj3Ow2J0X-oIk01ut5BhpJIv5dBRZnyheyWda-lJ8Qw40nbMkrvXF9qVQvuyg347Uemyw4je1YJO7SnjQnRdo9pZa9WIFq9v1wl0okpgazui4nSQOd9ItF8bm5xQyvnm4cwvtXu84U5vy7JW-8UF0tZbSI-dJvQj7j2_3vipUXtUXv0ahla3wJquw9bs2-8WV3J3oyskO_emWivbfYovZagvfW0n70MmVrzjmU042V5M4tuXirFvjQfS_cQm4jFqQeimueR2Owb2aRJV99ztru2Zpn7iOWtucm128Ue6Fypdngqd5lyV8-V0IbIBi38F1FfYWrWurlSQ3aydMzvnQ5x4c4FFQbXFmiX-gl6Unc1x7153zjyWJ5ihBtdVV2qskpSbR76-g90_W4gdkwi3W0UeBve";
}