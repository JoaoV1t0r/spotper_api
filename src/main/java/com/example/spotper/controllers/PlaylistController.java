package com.example.spotper.controllers;

import com.example.spotper.dtos.AdicionarFaixaPlaylistDto;
import com.example.spotper.dtos.CriarPlaylist;
import com.example.spotper.dtos.RemoverFaixaPlaylistDto;
import com.example.spotper.models.AlbumModel;
import com.example.spotper.models.FaixaModel;
import com.example.spotper.models.PlaylistsModel;
import com.example.spotper.repositories.AlbumRepository;
import com.example.spotper.repositories.PlaylistRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import jakarta.persistence.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@RestController
public class PlaylistController {

    final
    PlaylistRepository playlistRepository;
    AlbumRepository albumRepository;
    @PersistenceUnit
    EntityManagerFactory emf;

    public PlaylistController(
            PlaylistRepository playlistRepository,
            AlbumRepository albumRepository
    ) {
        this.playlistRepository = playlistRepository;
        this.albumRepository = albumRepository;
    }

    @GetMapping("/playlists")
    public ResponseEntity<List<PlaylistsModel>> getPlaylists() {
        return ResponseEntity.status(HttpStatus.OK).body(playlistRepository.findAllPlaylists());
    }

    @GetMapping("/albums")
    public ResponseEntity<List<AlbumModel>> getAlbums() {
        return ResponseEntity.status(HttpStatus.OK).body(albumRepository.findAllAlbums());
    }

    @GetMapping("/albums/above-average-price")
    public ResponseEntity<List<AlbumModel>> getAlbumsAboveAveragePrice() {
        return ResponseEntity.status(HttpStatus.OK).body(albumRepository.findAllAlbumsAboveAveragePrice());
    }

    @GetMapping("/faixas")
    public ResponseEntity<List<FaixaModel>> getFaixas() {

        String sql = "select * from faixa";
        EntityManager em = emf.createEntityManager();
        List<FaixaModel> faixas = em.createNativeQuery(sql, FaixaModel.class).getResultList();
        em.close();
        return ResponseEntity.status(HttpStatus.OK).body(faixas);
    }

    @GetMapping("/compositor-mais-playlists")
    public ResponseEntity<List> getCompositorMaisPlaylists() {

        String sql = """
                    select
                        c.nome,
                        count(*) as qtd_playlists
                    from compositor c
                    join faixa_compositor fc on fc.compositor_id =c.compositor_id
                    join faixa f on f.faixa_id = fc.faixa_id
                    join playlist_faixa pf on pf.faixa_id = f.faixa_id
                    group by c.nome
                    order by qtd_playlists desc
                    limit 1
                """;
        EntityManager em = emf.createEntityManager();
        List faixas = em.createNativeQuery(sql).getResultList();
        em.close();
        return ResponseEntity.status(HttpStatus.OK).body(faixas);
    }

    @GetMapping("/gravadora-mais-faixa-dvorack")
    public ResponseEntity<List> getGravadoraMaisFaixaDvorack() {

        String sql = """
                    select
                        g.nome,
                        count(*) as qtd_playlists
                    from compositor c
                    join faixa_compositor fc on fc.compositor_id =c.compositor_id
                    join faixa f on f.faixa_id = fc.faixa_id
                    join playlist_faixa pf on pf.faixa_id = f.faixa_id
                    join album a on a.album_id = f.album_id
                    join album_gravadora ag on ag.album_id = a.album_id
                    join gravadora g on g.gravadora_id = ag.gravadora_id
                    where c.nome = 'Dvorack'
                    group by g.nome
                    order by qtd_playlists
                    limit 1
                """;
        EntityManager em = emf.createEntityManager();
        List faixas = em.createNativeQuery(sql).getResultList();
        em.close();
        return ResponseEntity.status(HttpStatus.OK).body(faixas);
    }

    @GetMapping("/faixas-playlists")
    public ResponseEntity<List> getFaixasPlaylists() {

        String sql = """
                    select
                        pf.playlist_id,
                        pf.faixa_id
                    from  playlist_faixa pf
                """;
        EntityManager em = emf.createEntityManager();
        List faixas = em.createNativeQuery(sql).getResultList();
        em.close();
        return ResponseEntity.status(HttpStatus.OK).body(faixas);
    }

    @PostMapping("/remover-faixa-playlist")
    public ResponseEntity<Integer> removerFaixaPlaylist(@RequestBody RemoverFaixaPlaylistDto payload) {
        String sql = """
                    delete from playlist_faixa where playlist_id = :playlist_id and faixa_id = :faixa_id
                """;
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Query query = em.createNativeQuery(sql);
        query.setParameter("playlist_id", payload.playlist_id());
        query.setParameter("faixa_id", payload.faixa_id());
        int rows = query.executeUpdate();
        em.getTransaction().commit();
        em.close();
        return ResponseEntity.status(HttpStatus.OK).body(rows);
    }

    @PostMapping("/criar-playlist")
    public ResponseEntity<PlaylistsModel> criarPlaylist(@RequestBody CriarPlaylist payload) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Time tempo_total_execucao = new Time(0);
        tempo_total_execucao.setHours(0);
        for (int i = 0; i < payload.faixas().size(); i++) {
            Time t = (Time) em.createNativeQuery("select tempo_execucao from faixa where faixa_id = :faixa_id")
                    .setParameter("faixa_id", payload.faixas().get(i))
                    .getSingleResult();
            tempo_total_execucao = new Time(t.getTime() + tempo_total_execucao.getTime());
        }
        PlaylistsModel playlist = new PlaylistsModel();
        playlist.setNome(payload.nome());
        playlist.setData_criacao(new Date());
        playlist.setTempo_total_execucao(tempo_total_execucao);
        playlistRepository.save(playlist);
        for (int i = 0; i < payload.faixas().size(); i++) {
            em.createNativeQuery("insert into playlist_faixa (playlist_id, faixa_id) values (:playlist_id,:faixa_id)")
                    .setParameter("playlist_id", playlist.getPlaylist_id())
                    .setParameter("faixa_id", payload.faixas().get(i))
                    .executeUpdate();
        }
        em.getTransaction().commit();
        em.close();
        return ResponseEntity.status(HttpStatus.OK).body(playlist);
    }

    @PostMapping("/adicionar-faixa-playlist")
    public ResponseEntity<Boolean> adicionarFaixaPlaylist(@RequestBody AdicionarFaixaPlaylistDto payload) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        for (int i = 0; i < payload.faixas().size(); i++) {
            em.createNativeQuery("insert into playlist_faixa (playlist_id, faixa_id) values (:playlist_id,:faixa_id)")
                    .setParameter("playlist_id", payload.playlist_id())
                    .setParameter("faixa_id", payload.faixas().get(i))
                    .executeUpdate();
        }
        em.getTransaction().commit();
        em.close();
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
