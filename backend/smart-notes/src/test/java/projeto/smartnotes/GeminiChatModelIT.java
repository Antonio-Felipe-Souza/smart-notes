package projeto.smartnotes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "API_KEY", matches = ".+")
public class GeminiChatModelIT {

    @Autowired
    ChatModel chatModel;

    @Test
    void should_receiveResponse_when_chatModelIsCalled() {

        var response = chatModel.call(
                "Qual o maior jogador de League of Legends?"
        );

        assertThat(response).isNotEmpty();
        System.out.println(response);
    }
}