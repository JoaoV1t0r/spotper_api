package com.example.spotper.repositories;

import com.example.spotper.models.AlbumModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<AlbumModel, Long> {

    @Query(value = "SELECT * FROM album", nativeQuery = true)
    List<AlbumModel> findAllAlbums();

    @Query(value = "with all_precos as (select a.preco_compra, count(*) as qtd from album a group by a.preco_compra) select * from album a2 where a2.preco_compra >(select sum(preco_compra * qtd)/ sum(qtd) as media_precos from all_precos ap)", nativeQuery = true)
    List<AlbumModel> findAllAlbumsAboveAveragePrice();
}
