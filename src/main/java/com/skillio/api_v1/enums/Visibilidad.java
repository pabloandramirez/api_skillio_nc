package com.skillio.api_v1.enums;

import lombok.Getter;

@Getter
public enum Visibilidad {
    PRIVADO("PRIVADO"),
    SOLO_AMIGOS("SOLO AMIGOS"),
    PUBLICO("PUBLICO");

    private final String visibilidad;

    Visibilidad(String visibilidad){ this.visibilidad = visibilidad;}
}
