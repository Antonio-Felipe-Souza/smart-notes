
import { useEffect, useState } from 'react'

const API_URL = import.meta.env.VITE_API_URL

export default function Notes() {
    const [notes, setNotes] = useState([])

    useEffect(() => {
        async function loadNotes() {
            try {
                const response = await fetch(`${API_URL}/listAll`, {
                    method: 'GET',
                })

                if (!response.ok) {
                    throw new Error('Erro ao buscar anotações')
                }

                const data = await response.json()

                console.log(data)

                setNotes(data)

            } catch (error) {
                console.error(error)
            }
        }

        loadNotes()
    }, [])

    return (
        <div>
            <p>Minhas anotações</p>

            {notes.map((note) => (
                <div key={note.noteId.uuid}>
                    <p>{note.noteId.uuid}</p>
                    <h2>Titulo: {note.title}</h2>
                    <p>Transcrição:{note.transcription}</p>
                    <p>{note.noteCategory}</p>
                </div>
            ))}
        </div>
    )
}
