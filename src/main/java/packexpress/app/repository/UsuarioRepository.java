package packexpress.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import packexpress.app.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca un usuario por su correo y clave (para login).
     * 
     * @param correo Correo del usuario
     * @param clave Clave del usuario
     * @return Usuario si coincide, o null si no existe
     */
    Usuario findByCorreoAndClave(String correo, String clave);

    /**
     * Busca un usuario por correo (para validar duplicados al registrar).
     * 
     * @param correo Correo a verificar
     * @return Usuario si ya existe, o null si está disponible
     */
    Usuario findByCorreo(String correo);
}
