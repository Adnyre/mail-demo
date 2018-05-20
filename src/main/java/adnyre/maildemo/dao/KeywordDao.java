package adnyre.maildemo.dao;

import adnyre.maildemo.model.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface KeywordDao extends JpaRepository<Keyword, Long> {
    List<Keyword> findByNameIn(Set<String> names);
}