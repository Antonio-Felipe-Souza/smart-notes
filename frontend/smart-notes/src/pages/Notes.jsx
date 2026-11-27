import { useEffect, useState } from 'react'
import styles from './Notes.module.css'
import { Trash2 } from 'lucide-react'
import ConfirmModal from '../components/ConfirmModal'

const API_URL = import.meta.env.VITE_API_URL

export default function Notes() {
    const [notes, setNotes] = useState([])
    const [noteToDelete, setNoteToDelete] = useState(null)
    const [noteCategories, setNoteCategories] = useState([])
    const [selectedCategory, setSelectedCategory] = useState(null)

    useEffect(() => {
        async function loadNotes() {
            try {
                const url = selectedCategory
                    ? `${API_URL}/notes?noteCategory=${selectedCategory}`
                    : `${API_URL}/notes`

                const response = await fetch(url)

                if (!response.ok) {
                    throw new Error('Erro ao buscar anotações')
                }

                const data = await response.json()

                setNotes(
                    [...data].sort((a, b) =>
                        new Date(b.createdAt) - new Date(a.createdAt)
                    )
                )

            } catch (error) {
                console.error(error)
            }
        }

        loadNotes()
    }, [selectedCategory])

    useEffect(() => {
        async function loadCategories() {
            try {
                const response = await fetch(`${API_URL}/notes/categories`)

                if (!response.ok) {
                    throw new Error('Erro ao buscar categorias')
                }

                const categories = await response.json()
                setNoteCategories(categories)

            } catch (error) {
                console.error(error)
            }
        }

        loadCategories()
    }, [])

    async function handleDelete() {
        try {
            const response = await fetch(
                `${API_URL}/notes/delete/${noteToDelete}`,
                { method: 'DELETE' }
            )

            if (!response.ok) {
                throw new Error('Erro ao excluir anotação.')
            }

            setNotes((prevNotes) =>
                prevNotes.filter((note) => note.noteId.uuid !== noteToDelete)
            )

            setNoteToDelete(null)

        } catch (error) {
            console.error(error)
            alert('Não foi possível excluir a anotação.')
        }
    }

    function formatCategory(category) {
        const categories = {
            PERSONAL: 'Pessoal',
            WORK: 'Trabalho',
            STUDY: 'Estudos',
            MEETING: 'Reuniões',
            IDEAS: 'Ideias',
            TASKS: 'Tasks',
            FINANCE: 'Finanças',
            HEALTH: 'Saúde',
            TECHNOLOGY: 'Tecnologia',
            REMINDERS: 'Lembretes',
            OTHER: 'Outras',
        }

        return categories[category] || category
    }

    return (
        <main className={styles.notes}>
            <header className={styles.header}>
                <h1>Minhas anotações</h1>

                <section className={styles.filters}>
                    <span>Filtrar por</span>

                    <div className={styles.categories}>
                        <button
                            className={selectedCategory === null ? styles.active : ''}
                            onClick={() => setSelectedCategory(null)}
                        >
                            Todas
                        </button>

                        {noteCategories.map((category) => (
                            <button
                                key={category}
                                className={selectedCategory === category ? styles.active : ''}
                                onClick={() => setSelectedCategory(category)}
                            >
                                {formatCategory(category)}
                            </button>
                        ))}
                    </div>
                </section>
            </header>

            <section className={styles.grid}>
                {notes.map((note) => (
                    <article key={note.noteId.uuid} className={styles.card}>
                        <div className={styles.cardHeader}>
                            <span className={styles.category}>
                                {formatCategory(note.noteCategory)}
                            </span>
                            <span className={styles.date}>
                                {new Date(note.createdAt).toLocaleDateString('pt-BR')}
                            </span>
                        </div>

                        <h2>{note.title || 'Sem título'}</h2>

                        <p className={styles.transcription}>
                            {note.transcription}
                        </p>

                        <button
                            className={styles.deleteButton}
                            onClick={() => setNoteToDelete(note.noteId.uuid)}
                            title="Excluir anotação"
                        >
                            <Trash2 size={18} />
                        </button>
                    </article>
                ))}
            </section>
            {noteToDelete && (
                <ConfirmModal
                    onConfirm={handleDelete}
                    onCancel={() => setNoteToDelete(null)}
                />
            )}
        </main>
    )
}