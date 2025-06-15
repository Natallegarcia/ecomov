package com.abelix.DTO;

import java.time.LocalDate;
import java.util.List;

public class ItinerarioCalendarioDTO {

    private LocalDate data;
    private List<String> rotas;

    public ItinerarioCalendarioDTO(LocalDate data, List<String> rotas) {
        this.data = data;
        this.rotas = rotas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<String> getRotas() {
        return rotas;
    }

    public void setRotas(List<String> rotas) {
        this.rotas = rotas;
    }
}
