package com.example.spotper.dtos;

import java.util.List;

public record CriarPlaylist(
        String nome,
        List<Long> faixas
) {
}
