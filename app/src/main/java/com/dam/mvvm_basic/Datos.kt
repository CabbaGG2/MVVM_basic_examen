package com.dam.mvvm_basic

import androidx.compose.ui.graphics.Color

/**
 * Clase para almacenar los datos del juego
 */
object Datos {
    var numero = 0
}

/**
 * Colores utilizados
 * color: Color color normal
 * color_suave: Color color suave para el parpadeo, por defecto Transparente
 * txt: String nombre del color
 */
enum class Colores(val color: Color, val color_suave: Color = Color.Transparent, val txt: String) {
    CLASE_ROJO(color = Color.Red, txt = "roxo"),
    CLASE_VERDE(color = Color.Green, txt = "verde"),
    CLASE_AZUL(color = Color.Blue, txt = "azul"),
    CLASE_AMARILLO(color = Color.Yellow, txt = "melo"),
    CLASE_START(color = Color.Magenta, color_suave = Color.Red, txt = "Start")
}

/**
 * Estados del juego
 * INICIO: estado inicial
 * GENERANDO: generando numero random
 * ADIVINANDO: adivinando el numero
 * @param start_activo: Boolean si el boton Start esta activo
 * @param boton_activo: Boolean si los botones de colores estan activos
 */
enum class Estados(val start_activo: Boolean, val boton_activo: Boolean) {
    INICIO(start_activo = true, boton_activo = false),
    GENERANDO(start_activo = false, boton_activo = false),
    ADIVINANDO(start_activo = false, boton_activo = true)
}

/**
 * Utilizando las funciones Lambda
 * logramos que una colleción enum según el estado en que se encuentre el programa
 * pueda devolver un texto en mayuscula, minuscula o siga igual.
 */
enum class EstadosAuxiliares (val txt: String,var unit:(String)->String) {

    AUX1(
        txt = "aux1",
        unit = fun(txt): String {
            return txt
        }
    ),
    AUX2(
        txt = "aux2",
        unit = fun(txt): String {
            return txt.lowercase()
        }
    ),
    AUX3(txt = "aux3", fun(txt): String {
        return txt.uppercase()
    }
    )
}