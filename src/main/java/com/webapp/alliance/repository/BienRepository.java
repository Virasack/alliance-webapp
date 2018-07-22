package com.webapp.alliance.repository;

/**
 * Created by Nikos on 22/07/2018.
 */

import com.webapp.alliance.model.Bien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BienRepository extends JpaRepository<Bien, Long> {
}
