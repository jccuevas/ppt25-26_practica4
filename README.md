# ppt25-26_practica4
Servidor web sencillo sobre TCP que siga el protocolo HTTP/1.1 [1] [2], empleando sockets en el lenguaje Java
El servidor HTTP deberá implementar la siguiente funcionalidad:
1. Estructura del servidor: el servidor deberá soportar varias conexiones de manera concurrente a través de hebras. Cada una de estas hebras deberá seguir el protocolo HTTP/1.1 exclusivamente con la funcionalidad descrita en los demás puntos.
2. Compatibilidad: el servidor deberá funcionar ante peticiones de navegadores conocidos como Chrome o Firefox.
3. Funcionalidad limitada: el servidor deberá soportar solamente el método GET (RFC 9110 [1]) y no mantendrá la conexión abierta para emular un comportamiento HTTP/1.0.
4. Servidores virtuales: se deberá extraer la cabecera Host de la petición del cliente, mostrar por consola su valor y obtener los recursos de una carpeta con el mismo nombre que el valor de dicha cabecera para emular el comportamiento de servidores virtuales.
5. Recursos disponibles: deberá devolver el recurso solicitado (sin comprimir), con código de estado 200 [1] incorporando la funcionalidad asociada para las cabeceras de respuesta siguientes:
 a. Content-Type: según los recursos mínimos que deberán estar disponibles en el servidor, los valores de esta cabecera deberán ser, al menos: texto html (text/html), texto CSS (text/css) e imagen JPEG (image/jpeg). La elección de la cabecera deberá estar basada en la extensión del fichero del recurso solicitado (*.html, *.html, *.css, *.jpeg o *.jpg). Mire la especificación RFC 9110.
b. Content-Length: según RFC 9110 y RFC9112.
6. Soporte a errores: el servidor deberá soportar y poder enviar los siguientes códigos de estado, según corresponda [1]:
  a. 400 Bad request.
  b. 404 Not found.
  c. 405 Method not allowed.
7. Ficheros de recursos: en cada carpeta creada para la emulación del directorio base de la web y los servidores virtuales, al menos, deberán existir tres recursos en el servidor:
  a. Página por defecto index.html con un título (h1) con el nombre de la práctica, un párrafo con los datos personales del estudiante/s y la imagen siguiente del punto b. incrustada. También deberá emplear la hoja de estilos style.css del punto c.
  b. Imagen: fichero imagen.jpeg con el escudo de la Universidad de Jaén.
  c. Hoja de estilos: fichero style.css con la definición de un estilo para el elemento de HTML h1.
