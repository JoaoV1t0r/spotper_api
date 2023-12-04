package com.example.spotper.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RemoverFaixaPlaylistDto(
       @NotBlank Long playlist_id,
        @NotNull Long faixa_id) {
}
