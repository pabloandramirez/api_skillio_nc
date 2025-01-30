package com.skillio.api_v1.enums;

import lombok.Getter;

@Getter
public enum EstadoAmistad {
    ACEPTADO("Aceptado"),
    PENDIENTE("Pendiente"),
    RECHAZADA("Rechazada"),
    BLOQUEADA("Bloqueada");

    private final String estadoAmistad;

    EstadoAmistad(String estadoAmistad){ this.estadoAmistad = estadoAmistad;}
}
