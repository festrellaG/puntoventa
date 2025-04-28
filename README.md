# Proyecto "Punto Venta"

El siguiente proyecto tiene como objetivo autenticarse en por medio de auht0, realizar
inserciones a orden, consultar reportes y consultar catalogos como articulos, meseros.

# Pasos para configurar BD en manejador MySQL WorkBench

1. Se sugiere descargar el manejador MyQSL WorkBench, (pasos para configuración en link)
    https://dev.mysql.com/downloads/workbench/

    nota. Una vez instalado se crea una nueva conexión de una bd.
            -clic en signo de (+), en "setup new connection" se agrega un nombre
            - se deja el hostname por default 127.0.0.1 y user "root" y pwd "admin"

2. A continuación ejecuta los DDL para las tablas que se usarán, en el orden en que se muestran.

    a. CREATE TABLE `articulo` (
        `id_articulo` int NOT NULL AUTO_INCREMENT,
        `nombre` varchar(100) NOT NULL,
        `precio` double NOT NULL,
        PRIMARY KEY (`id_articulo`)
        ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
    
    b.CREATE TABLE `mesero` (
       `id_mesero` int NOT NULL AUTO_INCREMENT,
       `email` varchar(45) NOT NULL,
       `nombre` varchar(100) NOT NULL,
       PRIMARY KEY (`id_mesero`)
       ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

    c.CREATE TABLE `orden` (
       `id_orden` int NOT NULL AUTO_INCREMENT,
       `id_mesero` int NOT NULL,
       `nombre_comensal` varchar(100) NOT NULL,
       `id_articulo` int NOT NULL,
       `cantidad` double NOT NULL,
       `total` double NOT NULL,
       `fecha_orden` varchar(45) DEFAULT NULL,
       PRIMARY KEY (`id_orden`),
       KEY `id_mesero_idx` (`id_mesero`),
       KEY `id_articulo_idx` (`id_articulo`),
       CONSTRAINT `id_articulo` FOREIGN KEY (`id_articulo`) REFERENCES `articulo` (`id_articulo`),
       CONSTRAINT `id_mesero` FOREIGN KEY (`id_mesero`) REFERENCES `mesero` (`id_mesero`)
       ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

3. Se ejecutan los DML para el llenado de los catalogos
    
    a.INSERT INTO sys.articulo (nombre,precio) VALUES('tacos al pastor', 5);
      INSERT INTO sys.articulo (nombre,precio) VALUES('coca-cola', 20);
      INSERT INTO sys.articulo (nombre,precio) VALUES('tacos de tripa', 6);
      INSERT INTO sys.articulo (nombre,precio) VALUES('tacos de maciza', 7);

    b.INSERT INTO sys.mesero (email,nombre) VALUES('test@gmail.com', 'Test');


# Pasos para levantar Api Backend
    1. En el application-dev.yml asegurarse que el datasource coincida con lo que 
        se generó en el manejador de bd

    spring:
        datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://127.0.0.1:3306/sys
        username: root
        password: admin

    2. Si usas IntelliJ en la parte izquierda con el icono "m" ejecutar el "clean" y despues
        "compile"  o en linea de comando ejecutar "mvn clean" y posterior "mvn compile"

    3. Si usas IntelliJ situarse en la clase "PuntoventaApplication" y ejecutar con el boton de play.
        o ejecutar "mvn spring-boot:run" en linea de comando

    4. Para probar en este proyecto se adjunta el postman collection con el nombre "Parrot.postman_collection.json"
        e importar desde postman 