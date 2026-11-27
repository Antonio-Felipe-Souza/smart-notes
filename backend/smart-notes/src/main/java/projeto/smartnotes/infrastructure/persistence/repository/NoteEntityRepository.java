package projeto.smartnotes.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import projeto.smartnotes.domain.NoteCategory;
import projeto.smartnotes.infrastructure.persistence.entity.NoteEntity;

import java.util.List;
import java.util.UUID;

public interface NoteEntityRepository extends JpaRepository<NoteEntity, UUID> {
    List<NoteEntity> findAllByNoteCategory(NoteCategory noteCategory);

    @Query("SELECT DISTINCT n.noteCategory FROM NoteEntity n")
    List<NoteCategory> findDistinctCategories();
}
