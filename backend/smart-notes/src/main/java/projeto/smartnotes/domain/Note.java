package projeto.smartnotes.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class Note {
    private ResourceId noteId;
    private String title;
    private NoteCategory noteCategory;
    private String transcription;
    private Instant createdAt;
    private Instant updatedAt;

    public Note(String title, NoteCategory noteCategory, String transcription) {
        this.noteId = new ResourceId();
        this.title = title;
        this.noteCategory = noteCategory;
        this.transcription = transcription;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void updateTranscription(String transcription) {
        this.transcription = transcription;
        this.updatedAt = Instant.now();
    }
}