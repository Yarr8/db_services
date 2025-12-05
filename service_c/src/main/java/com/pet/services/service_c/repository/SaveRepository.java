package com.pet.services.service_c.repository;

import com.pet.services.service_c.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveRepository extends JpaRepository<Message, Long> {
}
