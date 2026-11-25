
import { useEffect, useRef, useState } from 'react'
import styles from './Recorder.module.css'

const fmt = (s) =>
  `${String(Math.floor(s / 60)).padStart(2, '0')}:${String(s % 60).padStart(2, '0')}`

export default function Recorder({ onProcess, busy }) {
  const [recording, setRecording] = useState(false)
  const [seconds, setSeconds] = useState(0)
  const [audio, setAudio] = useState(null)
  const [error, setError] = useState('')

  const recorderRef = useRef(null)
  const chunksRef = useRef([])
  const timerRef = useRef(null)
  const fileRef = useRef(null)

  useEffect(() => () => clearInterval(timerRef.current), [])

  useEffect(() => {
    return () => {
      if (audio?.url) URL.revokeObjectURL(audio.url)
    }
  }, [audio])

  function setNewAudio(blob, name) {
    setAudio({ blob, name, url: URL.createObjectURL(blob) })
  }

  async function start() {
    setError('')

    try {
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true })
      const rec = new MediaRecorder(stream)

      chunksRef.current = []

      rec.ondataavailable = (e) => {
        if (e.data.size) chunksRef.current.push(e.data)
      }

      rec.onstop = () => {
        stream.getTracks().forEach((t) => t.stop())
        const blob = new Blob(chunksRef.current, {
          type: rec.mimeType || 'audio/webm',
        })
        setNewAudio(blob, 'gravacao.webm')
      }

      rec.start()
      recorderRef.current = rec
      setSeconds(0)
      setRecording(true)

      timerRef.current = setInterval(() => {
        setSeconds((s) => s + 1)
      }, 1000)
    } catch {
      setError('Não foi possível acessar o microfone. Libere a permissão no navegador e tente de novo.')
    }
  }

  function stop() {
    recorderRef.current?.stop()
    clearInterval(timerRef.current)
    setRecording(false)
  }

  function onFile(e) {
    const file = e.target.files?.[0]
    e.target.value = ''

    if (!file) return

    const isMp3 =
      file.type === 'audio/mpeg' ||
      file.name.toLowerCase().endsWith('.mp3')

    if (!isMp3) {
      setError('Envie um arquivo MP3.')
      return
    }

    setError('')
    setNewAudio(file, file.name)
  }

  async function handleProcess() {
    try {
      await onProcess(audio.blob, audio.name)
      setAudio(null)
      setSeconds(0)
    } catch {
      // O erro já é exibido pelo Home.
    }
  }

  return (
    <section className={styles.recorder} aria-label="Enviar áudio">
      <div className={styles.recorderActions}>
        {recording ? (
          <button className={`${styles.btn} ${styles.btnStop}`} onClick={stop}>
            Parar gravação
          </button>
        ) : (
          <button
            className={`${styles.btn} ${styles.btnPrimary}`}
            onClick={start}
            disabled={busy}
          >
            Gravar áudio
          </button>
        )}

        <button
          className={styles.btn}
          onClick={() => fileRef.current.click()}
          disabled={recording || busy}
        >
          Enviar arquivo MP3
        </button>

        <input
          ref={fileRef}
          type="file"
          accept=".mp3,audio/mpeg"
          hidden
          onChange={onFile}
        />
      </div>

      {recording && (
        <p className={styles.recStatus} role="status">
          <span className={styles.recDot} />
          Gravando <span className={styles.timer}>{fmt(seconds)}</span>
        </p>
      )}

      {error && (
        <p className={styles.error} role="alert">{error}</p>
      )}

      {audio && !recording && (
        <div className={styles.preview}>
          <p className={styles.previewName}>{audio.name}</p>
          <audio controls src={audio.url} />

          <button
            className={`${styles.btn} ${styles.btnPrimary}`}
            onClick={handleProcess}
            disabled={busy}
          >
            {busy ? 'Processando…' : 'Processar áudio'}
          </button>
        </div>
      )}

      {busy && (
        <p className={styles.processing} role="status">
          <span className={styles.spinner} />
          A IA está transcrevendo e organizando sua anotação…
        </p>
      )}
    </section>
  )
}