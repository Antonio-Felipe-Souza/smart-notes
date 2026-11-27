package projeto.smartnotes.domain;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {
    Note save(Note note);
    List<Note> findAll();
    List<NoteCategory>findAllCategories();
    List<Note> findAllByCategory(NoteCategory noteCategory);
    Optional<Note> findById(ResourceId id);
    void deleteById(ResourceId id);
}
