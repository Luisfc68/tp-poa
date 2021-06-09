
## TP integrador programación con objetos avanzada

### Descripcion 

Este trabajo consiste en una API Rest para un sistema de canje de puntos a cambio de productos, las tecnologías que utiliza son Spring boot, Hibernate para el acceso a los datos y Jwt para la autenticación, está hecha para conectarse a una base de datos (en este caso MySQL) con el siguiente modelo:
 
![Diagrama entidad relacion](http://www.plantuml.com/plantuml/png/VP5FQm8n4CNl-HI3bzQ2Og6zB292eL1wwQNd4fFHdRgRf9E98gxVlHlNnVuHRvDvx_5xipkFebbINQaRACaYI8aGWnTrV1bYKw5gHRtnP26mNrHOHzYIbA3Tga8BXlqsmX9HL5oJ2vfr3HKvb5r0y2obLmgTaEnWyCRU9YD-03f2WBq2k2Fx4Kxh0iW9JAShTCXQqCvc2c0qoZQBqJ05GzuLiD5iIistzvF9C8l6nrCwlzhPh0iGShkyEPpvxp5ffWwVUdhgbjVWpjSBnZFtuN7OEfEZhuJPbXiuOHtHwL-kJc96VoqSaXCV2_ZJzacxJpnsDNrNarTNclu_v_crOoonXHTWM_zFnejWVKJGHKHEN_v9lTHze3OaPBNjRqXDCnxxlL4c3ysU51ryEpH1pT7PL5VV)

### Endpoints 

Los endpoints con los que cuenta el trabajo son los siguienes, todos están protegidos y solo se puede acceder después de autenticarse excepto los que estan en **negrita**:

#### Entidad usuario

* **/usuario/login (post)**: endpoint para que el usuario se autentique, recibe un objeto que tenga los atributos "nombre" y "contrasena". Devuelve un token para poner en el encabezado "Authorization" de las siguientes peticiones.
* /usuario (get): endpoint para ver la información del usuario que realizó la petición.
* /usuario/{id} (get): endpoint para ver la información de un usuario específico.
* **/usuario/signup (post)**: endpoint para que el usuario se registre y pueda autenticarse posteriormente. Espera un objeto que tenga como atributos los campos "nombre", "contrasena" y "correo". Devuelve el objeto del usuario creado.

#### Entidad canje 

* /canje/{id} (get): endpoint para que el usuario autenticado visualice uno de sus canjes. Cabe recalcar que el si el usuario intenta ver un canje de otro usuario que no es él, no será permitido.

* /canje (get): endpoint que le devuelve al usuario autenticado una lista de sus canjes, dicha lista está paginada, se le puede indicar el offset por un parametro en la url. Por ejemplo: ?offset=5. Si no se le indica un offset el valor por defecto es 0.

* /canje (post): endpoint para que el usuario intente realizar un canje. Este endpoint espera un vector de objetos que representarían los items del canje, donde cada objeto debe tener los atributos "producto" el cual es el id del producto que se quiere canjear y "cantidad" que es la cantidad de dicho producto a canjear. El endpoint devuelve el objeto del canje en el caso que se haya realizado con éxito.

#### Entidad producto

* /productos/{id} (get): endpoint para que el usuario pueda ver un producto en específico.

* /productos (get): endpoint para que un usuario pueda ver todos los productos. Como devuelve un vector de productos, está paginado, por lo que se le puede indicar el offset en la url al igual que en el endpoint de canjes. En este caso el offset por defecto también es 0.



