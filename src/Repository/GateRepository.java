package Repository;

import Models.Gate;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class GateRepository implements IGateRepository{

    private Map<Long, Gate> gates = new TreeMap<>();
    public Optional<Gate> findGateById(Long id){
        if(gates.containsKey(id)){
            return Optional.of(gates.get(id));
        }
        return Optional.empty();
    }

}
