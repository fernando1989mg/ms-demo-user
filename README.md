# Api Rest, ejercicio BCI - By Fernando Muñoz

Esta es una API Rest para guardar usuarios y probar autenticación mediante UUID token, está programada con Java
usando Spring Framework con SpringBoot

## Cómo ejecutar la API en local?

*   Puede situarse en la raiz del proyecto y ejecutar el comando 

    ```
    ./gradlew bootRun
    ```

## Cómo funciona?

*   La API solo expone tres endpoint:

    ```
    [POST]
    /api/users
    
    [JSON para request]
    {
        "name": "fernando",
        "email": "ferg@mailcom",
        "password": "Hola1234.",
        "phones":[
            {
                "number": 12343132,
                "cityCode": 33,
                "countryCode": 56
            }
        ]
    }
    ```
    ```
    [POST]
    /api/users/login?email=ferg@mailcom&password=Hola1234.

    ```
    
    ```
    [GET]
    /api/users/{ID de usuario}
    
    [HEADER CON AUTORIZATION TOKEN UUID]
    Authorization = Bearer 6062c148-6ff3-4ce2-aa0a-ac53ed81355b
    ```

*   Para Invocar a /api/users/login primero debe crear un usuario mediante [POST]/api/users
*   El UUID de autorización lo puede obtener llamando al path /api/user/login indicado arriba

