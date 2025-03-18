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
    public static final String TOKEN = "ya29.c.c0ASRK0Gav0hXy8eX6_SB-Q6eh9aZJIAoz98mD_50eGfc1MFss6qxcJINWm16Wx9A0q5F6NCUZKlsK9bo9WnAb3tXd2-pa6CC5gc-_Gbto7jHduUCt6BvLdK1vG_gryzjhk82vlMsB5cVfhpj0SVj3MinVzPcGDi8uEJu6-4_jbdLovJ5-QJpZ2KrO-Fb6Nrc4UsrMVD4wGpVojl07CDfPAa46E4a7eE0ncg_VJ8ZwOgJshXzGiL0PGoiYPzpNNUZ0ORB0Xntxv9O9c8UIOglEbnusZoPavraXv8fOMdXPGgNP1YyrVU4E5us281SD-jCZnIQd45ahXLyEMUuS_M3Tr_b2h7MrJHf4Es7JSReg7Eq3ocBEIWVCPXWz1tcH388DvbhBFt7MZZfe6ZfOWFO_0nqMRO9YJnOmvn487_f70cjmIh_8e28jh9I4Y9-27zWhQfntgX740_I11ujjek-6sswZWXUFJqgQWql9Ykf9Oyh6Uxmyn_aqXgFQs38IfnduiBjl7ellWVFapRf2dvSm6ss5sk3cJskdiUFI17-UBoSOWUxoShzgiOIb27M0BsMwmvOc_b0v7yJ2JnfvSg82BVo958UV6poOQ9lotd-ui6-6lF5QF3dBixnS18F6mW7nZbqIaZ-yadVdtBFhFYBu8y-OtQ1_UI39mSzagmrrjip1Mkc5vz856RWIpJOUFWhUq1swXq2ezs0QmUgJ-FnRzQYg0QfMep_Mhe0VczXfX2cpc52OFw-Bl839UmWztzcvwI8mWjt9h_J3ud9t-WFJbs6sfcmfJWhsJqckdkWIhimmRw-ByfmFnrs4B8ujuQc-d9MUvub-emOup-Z8aJSUmtv71FFjfl_dv0B575kb3M7ksQMW5boaIl6ReUdReJiryssvy4wMvu5f0dQ16n7twe1d201iwldW7-oyhefeZ9mFbIR7jgIhmig8lSysgkvZ11_hYFhypf88yc-I-2hJx8qxixFfQ2rm2l_lS3MX_Sbr6n4YitZYBta";
}