package com.pado.idleworld.contents;

import com.pado.idleworld.domain.Contents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.EntityManager;
import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long> {

}
