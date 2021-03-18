package com.cinmunten.lil.learningSpring.data.repository;

import com.cinmunten.lil.learningSpring.data.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository  extends CrudRepository<Room, Long> {

}
