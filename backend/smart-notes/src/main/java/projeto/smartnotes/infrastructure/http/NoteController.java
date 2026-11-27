package projeto.smartnotes.infrastructure.http;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projeto.smartnotes.application.usecase.TranscribeAndSaveUseCase;
import projeto.smartnotes.domain.Note;
import projeto.smartnotes.domain.NoteCategory;
import projeto.smartnotes.domain.NoteRepository;
import projeto.smartnotes.domain.ResourceId;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "http://localhost:5173")
public class NoteController {
    private final TranscribeAndSaveUseCase transcribeAndSaveUseCase;
    private final NoteRepository noteRepository;

    public NoteController(TranscribeAndSaveUseCase transcribeAndSaveUseCase, NoteRepository noteRepository) {
        this.transcribeAndSaveUseCase = transcribeAndSaveUseCase;
        this.noteRepository = noteRepository;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Note> transcription(
            @RequestParam("file") MultipartFile file) throws IOException {

        var note = transcribeAndSaveUseCase.execute(file.getBytes());

        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(note);
    }

//    @GetMapping
//    List<Note> listar(){
//        return noteRepository.findAll();
//    }

    @GetMapping
    public ResponseEntity<List<Note>> findAll(
            @RequestParam(required = false) NoteCategory noteCategory) {

        var notes = noteCategory == null ? noteRepository.findAll() : noteRepository.findAllByCategory(noteCategory);

        return ResponseEntity.ok(notes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id){
        var resourceId = new ResourceId(id);

        var exists = noteRepository.findById(resourceId);

        if (exists.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        noteRepository.deleteById(resourceId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<NoteCategory>> findAllCategories(){
        var categories = noteRepository.findAllCategories();

        return ResponseEntity.ok(categories);
    }
}
