# My Module App 🎬

Aplicación Android para gestión de películas usando la API de TMDB.

## 🚀 Configuración Inicial

### Requisitos Previos
- Clave API de TMDB ([Regístrate aquí](https://www.themoviedb.org/documentation/api))
- URL base del servicio API


### 🔑 Configuración de Credenciales
Edita el archivo nativo para agregar tus credenciales:

**Archivo:** `app/src/main/cpp/native-lib.cpp`

```cpp
// ====================================================
// ▼▼▼ REEMPLAZAR ESTOS VALORES CON TUS CREDENCIALES ▼▼▼
// ====================================================

return env->NewStringUTF("TU_TOKEN_AQUÍ"); // 🔒 Token de TMDB

return env->NewStringUTF("TU_URL_AQUÍ");   // 🌍 URL base (ej: "https://api.themoviedb.org/3/")


// ====================================================
// ▲▲▲ MODIFICAR SOLO LOS VALORES ANTERIORES ▲▲▲
// ====================================================

return env->NewStringUTF("movie-database"); // 🗃 Nombre BD (opcional cambiar)
