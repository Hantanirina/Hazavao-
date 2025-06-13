package school.hei.hazavao.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Hazavao implements Function<String, String> {
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "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"; // sera donnée à l'examen
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String apply(String teny) {
        try {
            Map<String, Object> message = Map.of(
                    "role", "user",
                    "content", "Amin'ny teny malagasy, hazavao amin'ny fomba fohy sy mazava ny dikan'ny teny hoe \"" + teny + "\". Aza manao fiarahabana."
            );

            Map<String, Object> requestBody = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", List.of(message),
                    "temperature", 0.7
            );

            String json = objectMapper.writeValueAsString(requestBody);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(json, StandardCharsets.UTF_8))
                    .build();

            var client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Map<?, ?> body = objectMapper.readValue(response.body(), Map.class);
            Map<?, ?> choice = (Map<?, ?>) ((List<?>) body.get("choices")).get(0);
            Map<?, ?> msg = (Map<?, ?>) choice.get("message");
            return (String) msg.get("content");

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Diso ny fangatahana.";
        }
    }

}
