package projeto.smartnotes.application.usecase;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import projeto.smartnotes.domain.Note;
import projeto.smartnotes.domain.NoteRepository;
import projeto.smartnotes.infrastructure.ai.NoteAIService;

@Service
public class TranscribeAndSaveUseCase {
    private final TranscriptionModel transcriptionModel;
    private final NoteAIService noteAIService;

    private final NoteRepository noteRepository;

    public TranscribeAndSaveUseCase(TranscriptionModel transcriptionModel, NoteAIService noteAIService, NoteRepository noteRepository) {
        this.transcriptionModel = transcriptionModel;
        this.noteAIService = noteAIService;
        this.noteRepository = noteRepository;
    }

    public Note execute(byte[] audio){
        var resource = new ByteArrayResource(audio){
            @Override
            public String getFilename(){
                return "audio.mp3";
            }
        };

        var transcription = transcriptionModel.transcribe(resource);

        var analysis = noteAIService.analyze(transcription);

        var note = new Note(
                analysis.title(),
                analysis.noteCategory(),
                transcription
        );

        return noteRepository.save(note);
    }
}
