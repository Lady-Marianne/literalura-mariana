# Proyecto literalura-mariana:

**Descripción**:  
literalura-mariana es una aplicación que permite gestionar una biblioteca virtual. El sistema interactúa con la 
API de Gutendex para obtener información sobre libros y autores, y guarda esos datos en una base de datos local 
usando Spring Boot y PostgreSQL. El proyecto fue diseñado para ser simple, pero extensible, con el objetivo de 
ofrecer una plataforma para consultar y almacenar información sobre libros y autores.

Esta aplicación está sujeta a mejoras futuras.
La clase DatabaseInitializer está comentada porque no he logrado hacer que se cree la base
de datos desde cero al correr el programa por primera vez. Es una posible mejora a futuro.
La clase Resultado está comentada porque, finalmente, no se usó.

---

## Tecnologías Utilizadas:

- **Java 21**: Lenguaje de programación principal.
- **Spring Boot**: Framework para el desarrollo del servicio backend.
- **JPA (Java Persistence API)**: Utilizado para interactuar con la base de datos.
- **PostgreSQL**: Sistema de gestión de base de datos relacional.
- **Lombok**: Biblioteca que simplifica el código Java eliminando la necesidad de escribir métodos repetitivos
(como getters, setters y constructores).
- **Jackson**: Biblioteca utilizada para la conversión de objetos Java a JSON y viceversa.
- **API de Gutendex**: API pública para acceder a libros y autores en formato JSON.

---

## Estructura del Proyecto:

El proyecto sigue una estructura estándar para aplicaciones Spring Boot:

```
com.alura.literalura_mariana
│
├── model/                # Clases de modelo que representan entidades de la base de datos (Autor, Libro, 
|                            enum Lenguaje).
├── record/               # Records para mapear datos provenientes de la API (DatosAutor, DatosLibro, 
|                           DatosResultado).
├── repository/           # Repositorios JPA para la interacción con la base de datos.
├── service/              # Lógica de negocio (servicios que consumen la API y gestionan la persistencia).
├── principal/            # Clase principal que arranca la aplicación.
├── initializer/          # Inicialización y configuración de la aplicación.
└── resources/            # Archivos de configuración y recursos (application.properties).
```

---

## Contenido de los paquetes:

- initializer: LiteraluraMarianaApplication.java

- principal: Principal.java

- model: - Autor.java
         - Lenguaje.java
         - Libro.java

- record: - DatosAutor.java
          - DatosLibro.java
          - DatosResultado.java

- repository: - libroRepository.java
              - autorRepository.java

- service: - ConsumirAPI.java
           - ConvertirDatos.java
           - IConvertirDatos.java
           - LibroService.java

- resources. - application.properties
- pom.xml
- README.md

---
## Funcionalidades:

- **Consumo de la API de Gutendex:**:  
  Se obtiene información de libros, autores y otros datos relevantes de la API de Gutendex,
  proporcionando el título o parte del título de un libro.
  Esta información se convierte en objetos Java y se verifica si está guardada en la base de datos PostgreSQL.
  Si no lo está, se guarda.

- **Gestión de libros y autores:**:  
  El sistema permite almacenar información de libros y autores. Los libros están vinculados a un 
  solo autor, según la consigna dada.

- **Muestra de listados de libros y autores:**:
  Se muestra la lista de autores y libros guardados en la base de datos, junto con las estadísticas
  de descargas.

- **Top 10 de libros más descargados:**:
  Se muestran los libros más descargados entre los que están guardados en la base de datos.

- **Búsqueda de libros por autor y por idioma:**:  
  La aplicación permite buscar libros filtrando por nombre de autor o por idioma
  en la base de datos.

- **Búsqueda de autores por fecha:**:
  Dado un año por el usuario, se buscan en la base de datos los autores vivos ese año.

---

### Pasos para ejecutar el proyecto:

1. **Clonar el repositorio:**:
   ```bash
   git clone https://github.com/tu_usuario/literalura-mariana.git
   ```

2. **Configurar la base de datos:**:
    - Crea una base de datos en PostgreSQL llamada `literalura`.
    - Asegúrate de que los detalles de la conexión estén correctamente configurados en el archivo
   `src/main/resources/application.properties`:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
      spring.datasource.username=tu_usuario
      spring.datasource.password=tu_contraseña
      ```

## Modelos de Datos:

- **Libro:**:  
  Representa la entidad `Libro` en la base de datos. Cada libro tiene un título, un autor y un idioma.

- **Autor:**:  
  Representa la entidad `Autor`. Un autor tiene un nombre, año de nacimiento y, opcionalmente, un año de muerte.

- **Lenguaje:**:  
  Representa el idioma de un libro.

- Los records DatosAutor, DatosLibro y DatosResultado nos mapean los datos traídos desde la API.

---

## Consideraciones:

- El sistema está diseñado para trabajar con un único autor por libro, según las especificaciones 
  del trabajo práctico.
- Los idiomas de los libros están representados por una lista de valores predefinidos en el sistema.

---

## Créditos:

- **Mariana Andra Lois**: Desarrolladora del proyecto.
- **Alura Latam**: Para los recursos educativos.

---


