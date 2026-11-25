package projeto.smartnotes;

import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnabledIfEnvironmentVariable(named = "API_KEY", matches = ".+")
public class GeminiTranscriptionModelIT {
    @Autowired
    TranscriptionModel transcriptionModel;

    @ParameterizedTest
    @CsvSource({
           "arrogante-neymar.mp3, arrogante",
           "foi-quando-gyro-finalmente-entendeu.mp3, universo"
    })
    public void should_containExpectedKeywords_when_audioFilesAreProcessed(String fileName, String expectedKeyWord){
        var recording = new ClassPathResource("audio/" + fileName);

        var response = transcriptionModel.transcribe(recording);

        assertThat(response).contains(expectedKeyWord);
        System.out.println("Transcrição: [" + response + "]");
    }
}
