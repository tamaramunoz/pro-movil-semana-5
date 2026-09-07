# Tarea 5: Las animaciones, gráficos y multimedia en Android

Este repositorio contiene la aplicación móvil desarrollada para la Semana 5 de la asignatura **Herramientas de Programación Móvil**.

## Objetivo de la Semana
El objetivo principal de esta unidad fue elaborar contenido multimedia en Android, considerando los componentes de audio, video, animaciones y gráficos en el desarrollo de una aplicación móvil para facilitar la comunicación de datos o información, integrando además el manejo del contrato Media Store.

## Detalles del Proyecto
* **Lenguaje:** Kotlin.
* **Proyecto:** (Aplicación orientada a premiar a los mejores vendedores de la empresa de retail "My Retail").
* **Componentes Multimedia y Gráficos:**
    * **Animaciones:** Implementación de animación basada en imágenes (`AnimationDrawable`) estructurada mediante fotogramas en formato XML dentro del directorio `res/drawable/` para la pantalla inicial.
    * **Audio:** Reproducción de audio mediante la clase `MediaPlayer` vinculada a un archivo multimedia almacenado localmente en la carpeta `res/raw/` y activada al seleccionar al tercer vendedor del listado.
    * **Cámara y Media Store:** Integración de un `Intent` implícito para la captura de fotografías del vendedor destacado, utilizando las abstracciones de `Media Store` y `ContentResolver` para gestionar el almacenamiento externo.
    * **Gráficos Estadísticos:** Despliegue de un gráfico de barras utilizando la biblioteca externa `MPAndroidChart` para exponer el rendimiento de ventas de los vendedores.

## Cómo ejecutar localmente
1. Clonar este repositorio.
2. Abrir la carpeta del proyecto utilizando **Android Studio**.
3. Sincronizar las dependencias del proyecto con Gradle (asegurando la incorporación del repositorio `jitpack.io` en el archivo `settings.gradle` y la biblioteca de gráficos en `build.gradle`).
4. Iniciar un emulador virtual (ADV) desde el **Device Manager**.
5. Presionar el botón **Run** en la barra superior para compilar e instalar la aplicación en el dispositivo.

## Desarrollado por:
- Tamara Muñoz