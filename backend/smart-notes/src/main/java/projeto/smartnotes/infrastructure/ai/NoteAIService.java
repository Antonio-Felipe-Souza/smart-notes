package projeto.smartnotes.infrastructure.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import projeto.smartnotes.application.dto.NoteAnalysis;

@Service
public class NoteAIService {
    private final ChatClient chatClient;

    public NoteAIService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public NoteAnalysis analyze(String transcription){
        var analysis = chatClient.prompt()
                .user("""
                        Analise a transcrição abaixo.
                        
                        Gere:
                        - Um título curto.
                        - Uma categoria adequada.
                        
                        Transcrição: %s
                        """.formatted(transcription))
                .call()
                .entity(NoteAnalysis.class);

        return analysis;
    }
}
