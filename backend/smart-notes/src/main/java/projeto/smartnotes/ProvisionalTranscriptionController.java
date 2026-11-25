package projeto.smartnotes;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projeto.smartnotes.domain.Note;
import projeto.smartnotes.domain.NoteCategory;
import projeto.smartnotes.domain.NoteRepository;

import java.time.Instant;

@RestController
@RequestMapping("/STTs")
// provisório que faz a transcrição de um áudio para texto - Speech To Text
public class ProvisionalTranscriptionController {
    private final TranscriptionModel transcriptionModel;
    private final NoteRepository noteRepository;

    public ProvisionalTranscriptionController(TranscriptionModel transcriptionModel, NoteRepository noteRepository) {
        this.transcriptionModel = transcriptionModel;
        this.noteRepository = noteRepository;
    }

    @CrossOrigin(origins = "http://localhost:5173/")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String transcription(@RequestParam("file")MultipartFile file){
        var resource = file.getResource();
        var result = transcriptionModel.transcribe(resource);

        noteRepository.save(
                new Note("", NoteCategory.FINANCE, result)
        );

        return result;
    }
}