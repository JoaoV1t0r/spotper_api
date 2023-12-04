package com.example.spotper.dtos;

import java.util.List;

public record AdicionarFaixaPlaylistDto(
        Long playlist_id,
        List<Long> faixas) {
}
