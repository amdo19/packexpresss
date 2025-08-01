package packexpress.app.repository;
import packexpress.app.model.Tienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Long> {

    // Ya deberías tener este si hiciste búsquedas:
    List<Tienda> findByNombreContainingIgnoreCase(String nombre);

    // ✅ AGREGA ESTE PARA DETALLES POR NOMBRE
    Optional<Tienda> findByNombreIgnoreCase(String nombre);
}
