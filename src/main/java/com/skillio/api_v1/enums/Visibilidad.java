package com.skillio.api_v1.enums;

public enum Visibilidad {
    PRIVADO("Privado"),
    SOLO_AMIGOS("Solo Amigos"),
    PUBLICO("Publico");

    private final String visibilidad;

    Visibilidad(String visibilidad){ this.visibilidad = visibilidad;}
}
