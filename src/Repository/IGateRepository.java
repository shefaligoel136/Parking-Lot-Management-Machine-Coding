package Repository;

import Models.Gate;

import java.util.Optional;

public interface IGateRepository {
    public Optional<Gate> findGateById(Long id);
}
