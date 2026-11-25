package projeto.smartnotes.infrastructure.persistence.repository;

import org.springframework.stereotype.Repository;
import projeto.smartnotes.domain.Note;
import projeto.smartnotes.domain.NoteCategory;
import projeto.smartnotes.domain.NoteRepository;
import projeto.smartnotes.domain.ResourceId;
import projeto.smartnotes.infrastructure.persistence.entity.NoteEntity;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaNoteRepository implements NoteRepository {
    private final NoteEntityRepository noteEntityRepository;

    public JpaNoteRepository(NoteEntityRepository noteEntityRepository) {
        this.noteEntityRepository = noteEntityRepository;
    }

    @Override
    public Note save(Note note) {
        var entity = NoteEntity.toEntity(note);
        var saved = noteEntityRepository.save(entity);

        return saved.toClass();
    }

    @Override
    public List<Note> findAll() {
        var listOfEntities = noteEntityRepository.findAll();

        var listOfClasses = listOfEntities
                .stream()
                .map(entity -> entity.toClass())
                .toList();

        return listOfClasses;
    }

    @Override
    public List<Note> findAllByCategory(NoteCategory noteCategory) {
        var entitiesByCategory = noteEntityRepository.findAllByNoteCategory(noteCategory);

        var classByCategory = entitiesByCategory
                .stream()
                .map(entity -> entity.toClass())
                .toList();

        return classByCategory;
    }

    @Override
    public Optional<Note> findById(ResourceId id) {
        var foundEntity = noteEntityRepository.findById(id.uuid());

        return foundEntity.map(entity -> entity.toClass());
    }

    @Override
    public void deleteById(ResourceId id) {
        if (!noteEntityRepository.existsById(id.uuid())) {
            throw new RuntimeException("Not found");
        }

        noteEntityRepository.deleteById(id.uuid());
    }
}
