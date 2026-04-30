# 🐉 Spyro The Dragon Guide App

## 📌 Introducción

Esta aplicación es una guía interactiva inspirada en el universo de *Spyro the Dragon*. 
Permite a los usuarios explorar personajes, mundos y coleccionables del juego mediante una interfaz visual y dinámica.

Además, incluye una guía inicial paso a paso para ayudar al usuario a entender el funcionamiento de la aplicación, junto con efectos visuales, sonidos y elementos ocultos (Easter Eggs) que mejoran la experiencia.

---

## ✨ Características principales

- 📖 **Guía interactiva inicial**
  - Explica el funcionamiento de la app paso a paso
  - Incluye animaciones y efectos visuales

- 🧍 **Listado de personajes**
  - Visualización mediante RecyclerView
  - Easter Egg con animación usando Canvas (Ripto)

- 🌍 **Listado de mundos**
  - Carga de datos desde XML
  - Easter Egg con vídeo al hacer triple click sobre un mundo

- 💎 **Coleccionables**
  - Visualización de elementos del juego

- 🔊 **Sonidos temáticos**
  - Efectos al avanzar en la guía
  - Música final en la pantalla de resumen

- 🎥 **Easter Eggs**
  - Vídeo oculto en pantalla completa
  - Animación personalizada con Canvas

---

## 🛠️ Tecnologías utilizadas

- **Kotlin**
- **Android Studio**
- **ViewBinding**
- **Navigation Component**
- **RecyclerView**
- **MediaPlayer / VideoView**
- **Canvas (Custom View)**
- **XML (para datos y layouts)**
- **SharedPreferences** (para mostrar la guía solo una vez)

---

## ▶️ Instrucciones de uso

1. Clonar el repositorio:

```bash
git clone https://github.com/tu-usuario/spyro-guide-app.git
````

### 2. Abrir el proyecto en Android Studio

Abrir Android Studio  
Seleccionar "Open"  
Elegir la carpeta del proyecto descargado  

### 3. Configuración del proyecto

Esperar a que Gradle descargue automáticamente las dependencias  
Es necesario tener conexión a internet  

### 4. Ejecutar la aplicación

Conectar un dispositivo Android físico o iniciar un emulador  
Pulsar ▶️ Run en Android Studio  

---

## 🎮 Uso de la aplicación

Al iniciar la app se mostrará una guía interactiva (solo la primera vez)

Navegar mediante el menú inferior:

- 🧍 Personajes  
- 🌍 Mundos  
- 💎 Coleccionables  

---

## 🥚 Easter Eggs

- 🌍 Mundos → Pulsar 3 veces seguidas sobre el mismo mundo  
- 🧍 Personajes → Mantener pulsado sobre Ripto  

---

## 🖼️ Captura de pantallas

| Inicio Guía | Tabs | Fin Guía |
|------------|------|----------|
| ![Inicio](images/inicio.png) | ![Tabs](images/tabs.png) | ![Fin](images/fin.png) |

## 🧠 Conclusiones del desarrollador

Durante el desarrollo de esta aplicación se han aplicado diversos conceptos clave del desarrollo Android, como el uso de fragments, navegación mediante Navigation Component y gestión de interfaces dinámicas.

Uno de los principales retos ha sido la implementación de funcionalidades interactivas avanzadas, como la detección de eventos complejos (triple click y pulsación prolongada) y la creación de animaciones personalizadas utilizando Canvas.

Asimismo, ha sido necesario gestionar correctamente el ciclo de vida de la aplicación para evitar problemas relacionados con la reproducción de sonido, vídeo y cambios de orientación de pantalla.

Este proyecto ha permitido consolidar conocimientos técnicos, mejorar la organización del código y desarrollar una aplicación más dinámica y atractiva mediante la inclusión de elementos interactivos y visuales.
