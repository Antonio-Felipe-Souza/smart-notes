
import { useState } from 'react'
import Recorder from '../components/Recorder'
import { processAudio } from '../services/audioServices'
import styles from './Home.module.css'

export default function Home() {
    const [busy, setBusy] = useState(false)
    const [error, setError] = useState('')
    const [transcription, setTranscription] = useState('')

    async function handleProcess(audio, name) {
        setBusy(true)
        setError('')
        setTranscription('')

        try {
            const result = await processAudio(audio, name)
            setTranscription(result)
        } catch (err) {
            setError(err.message || 'Não foi possível processar o áudio.')
        } finally {
            setBusy(false)
        }
    }

    return (
        <div className={styles.home}>
            <div>
                <h1>Fale. A gente organiza.</h1>

                <p className={styles.lead}>
                    Grave um áudio ou envie um MP3 e receba uma anotação com título,
                    tópico e resumo.
                </p>

                <Recorder
                    onProcess={handleProcess}
                    busy={busy}
                />

                {error && (
                    <p className={styles.error} role="alert">
                        {error}
                    </p>
                )}

                {transcription && (
                    <div className={styles.result}>
                        <h2>Transcrição do áudio</h2>
                        <p>{transcription}</p>
                    </div>
                )}
            </div>
        </div>
    )
}