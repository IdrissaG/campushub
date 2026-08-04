package gestion.campushub.repository;

import gestion.campushub.model.Cours;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CoursRepository {

    private final Map<String, Cours> stockageCours = new ConcurrentHashMap<>();

    public List<Cours> findAll() {
        return new ArrayList<>(stockageCours.values());
    }

    public Optional<Cours> findByCode(String code) {
        return Optional.ofNullable(stockageCours.get(code));
    }

    public Cours save(Cours cours) {
        stockageCours.put(cours.getCode(), cours);
        return cours;
    }

    public boolean deleteByCode(String code) {
        return stockageCours.remove(code) != null;
    }

    //
    public boolean existsByCode(String code) {
        return stockageCours.containsKey(code);
    }
}
