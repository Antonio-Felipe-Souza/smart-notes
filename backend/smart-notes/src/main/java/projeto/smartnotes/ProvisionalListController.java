package projeto.smartnotes;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projeto.smartnotes.domain.Note;
import projeto.smartnotes.domain.NoteRepository;
import projeto.smartnotes.infrastructure.persistence.repository.JpaNoteRepository;

import java.util.List;

@RestController
@RequestMapping("/listAll")
// provisório que faz a listagem de todas as notas
public class ProvisionalListController {
    private final NoteRepository noteRepository;

    public ProvisionalListController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
    List<Note> listar(){
        return noteRepository.findAll();
    }
}
