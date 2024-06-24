package com.alura.literAlura.service;

import com.alura.literAlura.model.DatosLibro;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class <T> clase) ;
}
