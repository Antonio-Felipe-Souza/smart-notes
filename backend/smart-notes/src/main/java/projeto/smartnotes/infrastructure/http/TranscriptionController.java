package projeto.smartnotes.infrastructure.http;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projeto.smartnotes.application.usecase.TranscribeAndSaveUseCase;
import projeto.smartnotes.domain.Note;

import java.io.IOException;

@RestController
@RequestMapping("/STT")
@CrossOrigin(origins = "http://localhost:5173")
public class TranscriptionController {
    private final TranscribeAndSaveUseCase transcribeAndSaveUseCase;


    public TranscriptionController(TranscribeAndSaveUseCase transcribeAndSaveUseCase) {
        this.transcribeAndSaveUseCase = transcribeAndSaveUseCase;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Note> transcription(
            @RequestParam("file") MultipartFile file) throws IOException {

        var note = transcribeAndSaveUseCase.execute(file.getBytes());

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(note);
    }
}
