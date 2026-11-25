package projeto.smartnotes.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import projeto.smartnotes.domain.Note;
import projeto.smartnotes.domain.NoteCategory;
import projeto.smartnotes.domain.ResourceId;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteEntity {
    @Id
    private UUID noteId;
    private String title;

    @Enumerated(EnumType.STRING)
    private NoteCategory noteCategory;

    @Column(columnDefinition = "TEXT")
    private String transcription;
    private Instant createdAt;
    private Instant updatedAt;

    public static NoteEntity toEntity(Note note){
        return new NoteEntity(
                note.getNoteId().uuid(),
                note.getTitle(),
                note.getNoteCategory(),
                note.getTranscription(),
                note.getCreatedAt(),
                note.getUpdatedAt()
        );
    }

    public Note toClass(){
        return new Note(
                new ResourceId(this.noteId),
                this.title,
                this.noteCategory,
                this.transcription,
                this.createdAt,
                this.updatedAt
        );
    }
}
