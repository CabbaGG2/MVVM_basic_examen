package com.dam.mvvm_basic

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyViewModel(): ViewModel() {

    // etiqueta para logcat
    private val TAG_LOG = "miDebug"

    // estados del juego
    // usamos LiveData para que la IU se actualice
    // patron de diseño observer
    val estadoActual = MutableStateFlow(Estados.INICIO)

    // este va a ser nuestra lista para la secuencia random
    // usamos mutable, ya que la queremos modificar
    var _numbers = MutableStateFlow(0)

    //variable de la cuenta atras observada
    // var es variable mutable
    // val es variable INMUTABLE

    var cuentaAtras = MutableStateFlow(0)

    // inicializamos variables cuando instanciamos
    init {
        // estado inicial
        Log.d(TAG_LOG, "Inicializamos ViewModel - Estado: ${estadoActual.value}")
    }

    /**
     * crear entero random
     */
    fun crearRandom() {
        // cambiamos estado, por lo tanto la IU se actualiza
        estadoActual.value = Estados.GENERANDO
        _numbers.value = (0..3).random()
        Log.d(TAG_LOG, "creamos random ${_numbers.value} - Estado: ${estadoActual.value}")
        actualizarNumero(_numbers.value)
        //volvemos a setear el contador a 5
        cuentaAtras.value = 5
        Datos.cuentaAtras.value = cuentaAtras.value

    }

    /**
     * actualizarNumero()
     * esta funcion actualiza el numero almacenado en Datos con el valor random generado
     * se inicializa las corrutinas con la función de estadosAuxiliares()
     */
    fun actualizarNumero(numero: Int) {
        Log.d(TAG_LOG, "actualizamos numero en Datos - Estado: ${estadoActual.value}")
        Datos.numero = numero
        // cambiamos estado, por lo tanto la IU se actualiza
        estadoActual.value = Estados.ADIVINANDO
        estadosAuxiliares()
    }

    /**
     * comprobar si el boton pulsado es el correcto
     * @param ordinal: Int numero de boton pulsado
     *ordinal es una convención para referirse al índice de una colección
     * si el ordinal coincide con el numero random detenemos la corrutina del contandor con pararCorrutina()
     * @return Boolean si coincide TRUE, si no FALSE
     */
    fun comprobar(ordinal: Int): Boolean {
        Log.d(TAG_LOG, "comprobamos - Estado: ${estadoActual.value}")
        return if (ordinal == Datos.numero) {
            Log.d(TAG_LOG, "es correcto")
            estadoActual.value = Estados.INICIO
            Log.d(TAG_LOG, "GANAMOS - Estado: ${estadoActual.value}")
            pararCorrutina()
            true
        } else {
            Log.d(TAG_LOG, "no es correcto")
            estadoActual.value = Estados.ADIVINANDO
            Log.d(TAG_LOG, "otro intento - Estado: ${estadoActual.value}")
            false
        }
    }

    /**
     * Corutina que lanza estados auxiliares
     * en esta función se añade la lógica del contador utilizando los estados auxiliares como apoyo visual.
     */
    fun estadosAuxiliares(msg: String = "") {
        viewModelScope.launch {
            cuentaAtras.value = Datos.cuentaAtras.value

            // guardamos los estados auxiliares
            var estadoAux = EstadosAuxiliares.AUX1

            //hacemos los cambios a los estados auxiliares con corrutinas
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1000)
            if(cuentaAtras.value > 0) {
                cuentaAtras.value--
                Datos.cuentaAtras.value = cuentaAtras.value
                estadoAux = EstadosAuxiliares.AUX2
            }else {
                return@launch
            }

            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1000)
            if(cuentaAtras.value > 0) {
                cuentaAtras.value--
                Datos.cuentaAtras.value = cuentaAtras.value
                estadoAux = EstadosAuxiliares.AUX3
            }else {
                return@launch
            }

            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1000)
            if(cuentaAtras.value > 0) {
                cuentaAtras.value--
                Datos.cuentaAtras.value = cuentaAtras.value
                estadoAux = EstadosAuxiliares.AUX4
            }else {
                return@launch
            }

            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1000)
            if(cuentaAtras.value > 0) {
                cuentaAtras.value--
                Datos.cuentaAtras.value = cuentaAtras.value
                estadoAux = EstadosAuxiliares.AUX5
            }else {
                return@launch
            }
            Log.d(TAG_LOG, "estado (corutina): ${estadoAux}")
            delay(1000)
            if(cuentaAtras.value >= 0) {
                cuentaAtras.value = 5
                Datos.cuentaAtras.value = cuentaAtras.value
                estadoActual.value = Estados.INICIO
                estadoAux = EstadosAuxiliares.AUX5
            }else {
                return@launch
            }


        }
    }

    /**
     * metodo que para la corrutina
     */
    fun pararCorrutina(){
        viewModelScope.launch {
            cuentaAtras.value = 0
            Datos.cuentaAtras.value = cuentaAtras.value
        }
    }
}