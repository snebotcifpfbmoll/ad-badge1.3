# FileCreator-3
Genera tu propia aplicación completa, a partir de los otros proyectos o no, que
persista la información adecuadamente. Puede ser restful. Aspectos:
- Basada en Spring
- Debe exponer los datos a través de un servicio rest
- Debe generar un fichero en CARPETA DE USUARIO para persistir
- Debe usar técnicas adecuadas (marshalling).
- Debe demostrar la persistencia en los reinicios.
## Datos
Una libreta de contactos que permite almacenar contactos. Cada contacto puede tener los atributos:
- Name
- Last name
- Email
- Address
- Phone
## Archivo de persistencia
Los datos se guardan en formato XML en el archivo `contacts.xml` en la carpeta del usuario.