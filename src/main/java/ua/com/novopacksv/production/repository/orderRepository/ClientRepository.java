package ua.com.novopacksv.production.repository.orderRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.novopacksv.production.model.orderModel.Client;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository <Client, Long> {

    Optional<Client> findByName(String name);

}
