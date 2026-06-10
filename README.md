# FastSnack - Sistema de Comida Rapida - Grupo: Las Turistas

Aplicacion de escritorio desarrollada en **Java (Swing)** con conexion a base de datos **MySQL**, para la gestion de un local de comida rapida. Permite el registro e inicio de sesion de clientes y empleados, la realizacion de pedidos desde un menu interactivo, y la administracion mediante un panel para empleados.

---

## Tecnologias utilizadas

- **Lenguaje:** Java
- **Interfaz grafica:** Swing (JFrame)
- **Base de datos:** MySQL
- **Conexion:** JDBC (driver mysql-connector-j)
- **Entorno de desarrollo:** NetBeans

---

## Funcionalidades

- Registro de usuarios diferenciado: el **cliente** puede usar cedula, RUC, pasaporte u otro; el **empleado** solo cedula.
- Validacion de documentos (cedula de 10 digitos, RUC de 13 digitos).
- Inicio de sesion con documento y contrasena.
- **Clientes:** menu interactivo con imagenes, carrito de compras, calculo de subtotal, IVA (15%), envio y total, numero de pedido automatico, metodo de pago, tiempo estimado y personalizacion del pedido.
- **Empleados:** panel con tres pestanas (Pedidos, Clientes e Inventario), con opcion de actualizar el stock.
- Toda la informacion de usuarios y pedidos se guarda en MySQL.

---

## Estructura del proyecto (separacion por capas)

- **Modelo:** clases de datos (Persona, Usuario, Producto, ItemPedido, Sesion, Catalogo) y la excepcion personalizada (DocumentoInvalidoException).
- **BD:** acceso a la base de datos (Conexion, UsuarioDAO, PedidoDAO, ProductoDAO).
- **Vista:** formularios de la interfaz (FrmBienvenida, FrmRegistro, FrmLogin, FrmInicioApp, FrmMenu, FrmConfirmacion, FrmExito, FrmPanelEmpleado, Img).

---

## Conceptos tecnicos aplicados

- **Patron Singleton:** la clase `Conexion` tiene constructor privado y una unica instancia (`getInstancia()`), centralizando la conexion a la base de datos.
- **Herencia y clase abstracta:** la clase abstracta `Persona` define los datos comunes; `Usuario` hereda de ella (`extends Persona`) e implementa el metodo abstracto `getTipoUsuario()` (polimorfismo).
- **Manejo de excepciones:** uso de `try-catch` en las operaciones de base de datos y una excepcion personalizada `DocumentoInvalidoException` que se lanza con `throw`.
- **Conexion JDBC** con MySQL mediante el driver mysql-connector-j.

---

## Como ejecutar el proyecto

1. **Crear la base de datos:** ejecutar en MySQL Workbench el script `fastfood.sql` (crea la base `fastfood` con las tablas `usuarios`, `pedidos` y `productos`).
2. **Configurar la conexion:** en `BD/Conexion.java`, colocar el usuario y la contrasena de tu MySQL.
3. **Agregar el driver:** anadir el archivo `mysql-connector-j.jar` a las librerias del proyecto.
4. **Colocar las imagenes** en el paquete `Imagenes`.
5. **Ejecutar** la clase `Main`.

---

## Autora

Melany - Tercero de Informatica A

---

# Analisis tecnico (preguntas del trabajo)

## 1. Patron de diseno Singleton

**Problema si varias clases crean multiples instancias de configuracion:** se desperdiciarian recursos abriendo conexiones repetidas, podria haber configuraciones inconsistentes entre clases, y el mantenimiento seria complicado porque un cambio (como la contrasena) habria que hacerlo en muchos lugares.

**Como lo resuelve Singleton:** garantiza una sola instancia y un punto de acceso global. En este proyecto, la clase `Conexion` centraliza la configuracion de la base de datos; con constructor privado e instancia unica, toda la aplicacion comparte la misma conexion.

**Escenarios reales:** conexion a base de datos, configuracion global, registro de logs, o la sesion del usuario activo (en este proyecto, la clase `Sesion` comparte el usuario logueado y el carrito).

**Riesgos si se usa mal:** puede volverse una variable global dificil de rastrear, complica las pruebas, y en entornos con multiples hilos podria crear instancias duplicadas si no se sincroniza.

## 2. Clases abstractas e interfaces

**Cuando usar una clase abstracta:** cuando varias clases comparten atributos y comportamiento comun y se quiere evitar repetir codigo, obligando ademas a implementar ciertos metodos. En el proyecto, `Persona` es abstracta y de ella hereda `Usuario`.

**Que heredar desde la clase abstracta:** los atributos y metodos comunes (nombre, telefono y sus getters/setters). Los metodos que cambian en cada subclase se declaran abstractos.

**Cuando usar interfaz en lugar de clase abstracta:** cuando solo se necesita definir un contrato sin jerarquia de herencia, o cuando una clase debe cumplir varios contratos (en Java se hereda de una sola clase pero se implementan varias interfaces).

**Ventajas de las interfaces en proyectos grandes:** desacoplan el codigo, permiten cambiar implementaciones sin romper el resto, y facilitan las pruebas.

## 3. Manejo de excepciones: try...catch y throw

**Funcion del try-catch:** el `try` contiene codigo que puede fallar y el `catch` captura el error para manejarlo sin que el programa se cierre. En el proyecto se usa en todas las operaciones de base de datos.

**Diferencia entre catch y throw:** `catch` atrapa y maneja un error que ya ocurrio; `throw` lanza deliberadamente una excepcion para indicar que algo salio mal.

**Cuando lanzar una excepcion personalizada:** cuando el error es propio del dominio del problema. En el proyecto se creo `DocumentoInvalidoException`, que se lanza con `throw` cuando un documento no cumple el formato y se captura con `try-catch`.

**Problemas si no se controlan las excepciones:** el programa se cerraria ante cualquier fallo, el usuario veria errores tecnicos, podrian perderse datos, y seria dificil depurar.

## 4. Conexion Java + MySQL con Maven y dependencias

**Por que manejar dependencias automaticamente:** garantiza la version correcta de cada libreria y sus dependencias sin buscarlas manualmente.

**Si cada desarrollador instala librerias a mano:** habria versiones distintas, errores de "en mi maquina funciona", perdida de tiempo y proyectos dificiles de compartir.

**Funcion del pom.xml:** es el archivo de configuracion de Maven; define el proyecto, la version de Java y las dependencias que se descargan automaticamente.

**Dependencia para conectar Java con MySQL:** `mysql-connector-j` (groupId `com.mysql`), el driver JDBC de MySQL.

## 5. Reflexion final

**Por que analizar el problema antes de elegir patron, estructura o tecnica:** porque la tecnologia esta al servicio del problema. Analizar primero permite elegir la solucion mas simple y adecuada, ahorra tiempo y produce un sistema mantenible. En este proyecto, separar el codigo en capas desde el inicio permitio agregar funciones (productos nuevos, panel de empleado) sin desordenar el sistema.
