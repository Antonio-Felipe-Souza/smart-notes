
import { NavLink } from 'react-router-dom'
import styles from './Navbar.module.css'

export default function Navbar() {
  return (
    <header className={styles.navbar}>
      <span className={styles.brand}>Smart Notes</span>

      <nav>
        <NavLink
          to="/"
          end
          className={({ isActive }) => isActive ? styles.active : ''}
        >
          Início
        </NavLink>

        <NavLink
          to="/anotacoes"
          className={({ isActive }) => isActive ? styles.active : ''}
        >
          Anotações
        </NavLink>
      </nav>
    </header>
  )
}
