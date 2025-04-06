package com.danahub.zipitda.terms.repository;

import com.danahub.zipitda.terms.domain.Terms;
import com.danahub.zipitda.terms.domain.TermsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TermsRepository extends JpaRepository<Terms, TermsId> {

    @Query("SELECT t " +
            "FROM Terms t " +
            "WHERE t.id.version = (" +
            "    SELECT MAX(t2.id.version) " +
            "    FROM Terms t2 " +
            "    WHERE t2.id.title = t.id.title" +
            ")")
    List<Terms> findLatestTermsByTitle();

    @Query("SELECT MAX(t.id.version) FROM Terms t WHERE t.id.title = :title")
    Optional<Integer> findMaxVersionByTitle(@Param("title") String title);
}