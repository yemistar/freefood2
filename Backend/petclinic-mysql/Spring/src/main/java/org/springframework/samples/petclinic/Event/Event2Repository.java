package org.springframework.samples.petclinic.Event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Event2Repository extends JpaRepository<Event2,Integer> {
}
