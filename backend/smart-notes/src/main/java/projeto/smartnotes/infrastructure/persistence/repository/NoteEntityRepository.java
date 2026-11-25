package projeto.smartnotes.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto.smartnotes.domain.NoteCategory;
import projeto.smartnotes.infrastructure.persistence.entity.NoteEntity;

import java.util.List;
import java.util.UUID;

public interface NoteEntityRepository extends JpaRepository<NoteEntity, UUID> {
    List<NoteEntity> findAllByNoteCategory(NoteCategory noteCategory);
}
