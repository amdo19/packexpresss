package packexpress.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import packexpress.app.model.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar por contenido (servicio o tipo de entrega)
    List<Pedido> findByContenidoContainingIgnoreCase(String contenido);
    List<Pedido> findByCliente(String cliente);
    

}
