Servicio publicado en la web:

Swagger URL: https://springboot-app-personas-5d9d3a9e6b6c.herokuapp.com/api/swagger-ui/index.html#/

Requerimientos para configurar de manera local:
* JDK 17 o superior
* Maven 3.6.3
* Git para poder clonar repositorio de Github

1 - Ir a la ruta local donde se desea clonar el repositorio.

2 - Abrir terminal (Powershell, Git Bash, cmd, etc) en la ruta específica donde se quiere descargar el proyecto.

3 - Ejecutar dentro de la terminal la siguiente línea:
	git clone https://github.com/glaime/personas.git
	
4 - Una vez clonado el repositorio abrir el archivo descargado que contiene el codigo fuente.

5 - Abrir terminal (Powershell, Git Bash, cmd, etc) dentro del archivo (que contiene el codigo fuente) y ejecutar la siguiente línea:
    mvn clean install
	
6 - Una vez finalizada la ejecución del comando anterior debe figurar "BUILD SUCCESS" en la terminal.

7 - Una vez finalizado el paso anterior se generará un nuevo archivo llamado "target", ingresar a ese archivo, abrir terminal y ejecutar siguiente línea:
	java -jar reba-0.0.1-SNAPSHOT.jar

8 - Una vez ejecutado el comando anterior abrir navegador e ir a la ruta http://localhost:8080/api/swagger-ui/index.html#/ para ver swagger del proyecto.

Nota: Los formatos de fecha son dd-MM-yyyy


En caso de querer ingresar a la base de datos (H2) ingresar en la siguiente url:

http://localhost:8080/api/h2-console

con los siguientes datos:

Saved Settings: Generic H2(Embedded)
Setting Name: Generic H2 (Embedded)
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
User Name: sa
Password: password


La base de datos está precargada de la siguiente manera:

-- PAIS

| ID | DESCRIPCION |
|----|-------------|
|  1 | Argentina   |
|  2 | Paraguay    |
|  3 | Perú        |
|  4 | Bolivia     |
|  5 | Venezuela   |
|  6 | Brasil      |


-- TIPO_DOC

| ID | DESCRIPCION |
|----|-------------|
|  1 | DNI         |
|  2 | CUIL        |
|  3 | CUIT        |


-- TIPO_RELACION

| ID | DESCRIPCION |
|----|-------------|
|  1 | PADRE       |
