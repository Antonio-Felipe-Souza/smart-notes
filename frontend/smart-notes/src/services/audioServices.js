const API_URL = import.meta.env.VITE_API_URL

export async function processAudio(audio, name) {
    const formData = new FormData()

    formData.append('file', audio, name)

    const response = await fetch(`${API_URL}/STT`, {
        method: 'POST',
        body: formData,
    })

    if (!response.ok) {
        throw new Error('Erro ao processar áudio.')
    }

    console.log('Resposta do backend:', response.text)

    return response.text()
}