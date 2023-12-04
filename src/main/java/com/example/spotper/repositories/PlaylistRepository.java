package com.example.spotper.repositories;


import com.example.spotper.dtos.AlbumDto;
import com.example.spotper.models.AlbumModel;
import com.example.spotper.models.PlaylistsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<PlaylistsModel, Long> {

    @Query(value = "SELECT * FROM playlist p", nativeQuery = true)
    List<PlaylistsModel> findAllPlaylists();

}
