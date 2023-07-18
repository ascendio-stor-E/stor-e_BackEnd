package com.ascendio.store_backend.repository;

import com.ascendio.store_backend.model.Favourite;
import com.ascendio.store_backend.model.StoryBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FavouritesRepository extends JpaRepository<Favourite, UUID> {
}
