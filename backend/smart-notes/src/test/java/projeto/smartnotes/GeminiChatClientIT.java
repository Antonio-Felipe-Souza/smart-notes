package projeto.smartnotes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "API_KEY", matches = ".+")
public class GeminiChatClientIT {

    @Autowired
    ChatModel chatModel;

    @Test
    void should_executeSum_when_prompted(){
        var chatCliente = ChatClient.builder(chatModel).defaultSystem("Você é um matemático").build();

        var response = chatCliente.prompt("Soma 10 mais 20. Depois subtraia 30 do resultado anterior. Exiba apenas o resultado final sem explicações")
                .call().content();

        assertThat(response).contains("0");
        System.out.println(response);
    }
}
