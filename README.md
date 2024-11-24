
# Nombre del Proyecto: API
Plataforma de servicios de integracion - challenge
## Descripción

Esta API  permite crear un modulo transaciones en las cuales el usuario podra crear y leer las diferentes 
transacciones a travez de peticiones HTTP 

## Requisitos previos

1. **Lenguaje y Framework**:

    - Java 17
    - Spring boot 3.2

2. **Dependencias**:
    - `maven`
    - `H2 Database`

3. **Librerias Adicionales**:
    - `Map Struct`
   
4. **Otras herramientas**:
    - `Postman`

## Diseño y Arquitectura de la API
La entidad Transaction es la clase base que representa una transacción general,
pero se utiliza un enfoque de herencia para modelar diferentes tipos de transacciones. 
Esto significa que existen subtipos específicos de transacción, cada uno con su propia tabla en la base de datos. 
Las entidades que heredan de Transaction son Credit PaymentMethod PeerToPeer
Cada una de estas subclases representa un tipo de transacción con características particulares,
pero todas comparten algunos atributos comunes que están definidos en la clase Transaction.
La herencia se modela utilizando el patrón "Single Table Inheritance". 
Esto significa que cada una de las subclases (Credit, PaymentMethod, PeerToPeer)
tiene su propia tabla en la base de datos, pero todas están asociadas a través del campo id de la transacción.
Este enfoque optimiza el uso de la base de datos,y  Al mismo tiempo puede
gestionar diferentes tipos de transacciones de forma flexible y estructurada.
Este diseño con herencia permite manejar distintos tipos de transacciones en el
sistema de manera eficiente, manteniendo la simplicidad y la flexibilidad al mismo tiempo.






## Arquitectura de la base de datos
La arquitectura de la base de datos está basada en un modelo relacional. Aquí hay una descripción de las principales tablas y sus relaciones:

1. **Transacciones**
   - Tabla: `Transaction`
   - Descripción: Contiene la información de las transacciones.
   - Campos:
      - `id`: Identificador único del usuario.
      - `nombre`: Nombre completo del usuario.
      - `email`: Correo electrónico del usuario.


2. **TransaccionesLogError**
    - Tabla: `TransactionLogError`
    - Descripción: Contiene la información de las transacciones fallidas.
    - Campos:
        - `id`: Identificador único del usuario.
        - `nombre`: Nombre completo del usuario.
        - `email`: Correo electrónico del usuario.

3. **Currency**
   - Tabla: `Currency`
   - Descripción: Almacena las monedas que soporta la API .
   - Campos:
      - `id`: Identificador único de la moneda.
      - `descripcion`: Descripcion de la moneda.
      - `symbol`: Symbolo de la moneda.
   -
3. **Metodo de pago**
    - Tabla: `PaymentMethod`
    - Descripción: Almacena los metodos de pago que soporta la API .
    - Campos:
        - `id`: Identificador único del metodo de pago.
        - `descripcion`: Descripcion del metodo de pago.


4.   **Status**
    - Tabla: `Status`
    - Descripción: Almacena los posibles estados de una trasaccion .
    - Campos:
        - `id`: Identificador único del metodo del estado.
        - `descripcion`: Descripcion del estado.       


5. **Relación entre tablas**
  La clase Transaction establece relaciones con tres entidades principales: Currency, Status, y PaymentMethod. Estas relaciones  representar cómo se 
  vincula una transacción con la moneda utilizada, su estado y el método de pago empleado.
  Cada transacción tiene asociada una única moneda,estado, y metodo de pago.
  Varias transacciones pueden usar la misma moneda,estado, metodo de pago por lo que las relaciónes es de tipo "muchos a uno".

   
## Instalación
### 1. Clonar el repositorio
`git clone https://github.com/ImanolOliva/challenge.git`

### 2. Ejecutar la API
La app se ejecuta en el puerto 8080 y automaticamente
se levantara la base de datos en memoria que se utiliza para el proyecto.
No requiere configuracion adicional. 

### 3.Rutas de la API
POST /api/createTransaction : Crear transacciones (Soporta una o varias a la ves)
curl --location 'http://localhost:8080/api/createTransaction' \
--header 'Content-Type: application/json' \
--data '[
{
"currency": "USD",
"type": "transfer",
"amount": 98457,
"cuil": "20418949458",
"recipientAccount": "20418949458"
},
{
"currency": "YUAN",
"type": "peer_to_peer",
"amount": 432981,
"senderId": "20418949458",
"recipientId": "27555112428"   
},
{
"currency": "ARS",
"type": "credit",
"amount": 34234,
"cardId": 43829,
"cuil": "20418949458"

    }
]'
### POST /api/createTransactionError: Crea transacciones en estado 'ERROR'
curl --location 'http://localhost:8080/api/createTransactionError' \
--header 'Content-Type: application/json' \
--data '[
{
"currency": "USD",
"type": "transfer",
"amount": 92.821,
"cuil": "20418949458",
"recipientAccount": "20418949458"
},
{
"currency": "YUAN",
"type": "peer_to_peer",
"amount": 92.821,
"senderId": "20418949458",
"recipientId": "27555112428"   
},
{
"currency": "ARS",
"type": "credit",
"amount": 92.821,
"cardId": 43829,
"cuil": "20418949458"

    }
]'
### POST /api/reversTransactions: Toma las transacciones en estado 'ERROR' y actualiza su estado a 'COMPLETED'
curl --location --request POST 'http://localhost:8080/api/reversTransactions'


### GET  /api/paymentMethods: Obtiene los tipos de transacciones que admite la api
curl --location 'http://localhost:8080/api/paymentMethods'
### GET  /api/coins: Obtiene los tipos de monedas que admite la api
curl --location 'http://localhost:8080/api/coins'
### GET  /api/statusTransaction: Obtiene el estado de las transacciones
curl --location 'http://localhost:8080/api/status/7'
### GET  /api/transactions : Obtiene las transacciones por CUIL, admite filtros
curl --location 'http://localhost:8080/api/transactions/20418949458?startDate=2024-11-19&endDate=2024-11-25&page=0&size=20'
### GET  /api/transactionsHistory: Obtiene el historia de transacciones, admite filtros
curl --location 'http://localhost:8080/api/transactionsHistory?startDate=2024-11-18&endDate=2024-11-25'
### GET  /api/transactionsHistoryStatus: Obtiene el historial de transacciones por estado, admite filtros
curl --location 'http://localhost:8080/api/transactionsHistoryStatus?description=ERROR'


