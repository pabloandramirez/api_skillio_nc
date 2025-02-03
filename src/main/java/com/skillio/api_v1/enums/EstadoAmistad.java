package com.skillio.api_v1.enums;

import lombok.Getter;

@Getter
public enum EstadoAmistad {
    ACEPTADO("ACEPTADA"),
    PENDIENTE("PENDIENTE"),
    RECHAZADA("RECHAZADA"),
    BLOQUEADA("BLOQUEADA");

    private final String estadoAmistad;

    EstadoAmistad(String estadoAmistad){ this.estadoAmistad = estadoAmistad;}
}
