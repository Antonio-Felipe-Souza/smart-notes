
import styles from './ConfirmModal.module.css'

export default function ConfirmModal({ onConfirm, onCancel }) {
    return (
        <div className={styles.overlay} onClick={onCancel}>
            <div
                className={styles.modal}
                onClick={(event) => event.stopPropagation()}
                role="dialog"
                aria-modal="true"
                aria-labelledby="modal-title"
            >
                <div className={styles.icon}>
                    <span>!</span>
                </div>

                <h2 id="modal-title">Excluir anotação?</h2>

                <p>
                    Essa ação não poderá ser desfeita.
                    Tem certeza de que deseja excluir esta anotação?
                </p>

                <div className={styles.actions}>
                    <button
                        className={styles.cancelButton}
                        onClick={onCancel}
                    >
                        Cancelar
                    </button>

                    <button
                        className={styles.deleteButton}
                        onClick={onConfirm}
                    >
                        Excluir
                    </button>
                </div>
            </div>
        </div>
    )
}
