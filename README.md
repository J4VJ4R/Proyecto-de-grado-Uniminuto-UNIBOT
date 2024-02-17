# Aplicativo móvil para el control de brazo robótico

## Introducción

Se quiere construir un aplicativo móvil que permita el control de un brazo robótico, de tal manera que se pueda enviar movimientos por medio de scripts que manipulen los nodos del brazo robótico.

![Brazo Robótico](./images_readme/unibot.png)

Texto adicional sobre la introducción y detalles del proyecto...

## Movimiento en grados o tiempo para un brazo de tres grados de libertad

A continuación, se detallan los movimientos para un brazo de tres grados de libertad:

**Tabla 1**

_Esquema de un mensaje de movimiento (instrucción)_

| Parte del brazo | Eje | Sentido en las manecillas del reloj | Grados/Tiempo |
| --------------- | --- | ----------------------------------- | ------------- |
| Base            | z   | A favor                             | 90            |
| Brazo           | y   | En contra                           | 45            |
| Antebrazo       | x   | En contra                           | 90            |
| Pinza           | on/off | on/off                             | on/off        |

## Diagrama de las partes del brazo

![Brazo Robótico](./images_readme/brazo.png)

## Diagrama de componentes de la App

![Diagrama de Componentes](./images_readme/mobile-app-components.png)

## Diagramas de clases de la App

A continuación, aparecen los diagramas de clase de algunos componentes:

### Modelo de instrucciones

```mermaid
classDiagram
class IInstruction {
    <<Interface>>
    +getPartId() byte
}
class Axis {
    <<Enumeration>>
    X
    Y
    Z
}
class Direction {
    <<Enumeration>>
    Clockwise
    CounterClockwise
}
class RotationInstruction {
    +getAxis() Axis
    +getAngle() float
    +getDirection() Direction
}

class InstructionsService {
    +sendInstruction(IInstruction instruction) Promise
}

class SendInstructionRequest {
    +getInstruction() IInstruction
    +accept()
    +reject()
}

class IInstructionsTransmitter {
    <<Interface>>
    +sendInstruction(SendInstructionRequest instruction) Runnable
}

class Promise {
    +then(Lambda) Promise
    +catch(Lambda) Promise
}

class ClawInstruction {
    +getOpenClosed() boolean
}

IInstruction <|--RotationInstruction
IInstruction <|--ClawInstruction
RotationInstruction..>Axis
RotationInstruction..>Direction
InstructionsService..>IInstruction
InstructionsService..>Promise
InstructionsService..>IInstructionsTransmitter
IInstructionsTransmitter..>SendInstructionRequest
```

### Transmisión de instrucciones

```mermaid
classDiagram
class IInstructionsTransmitter {
    <<Interface>>
    +sendInstruction(SendInstructionRequest instruction) Runnable
}

class BluetoothInstructionsTransmitter

class HttpInstructionsTransmitter

class IInstructionSerializer {
    <<Interface>>
    +serialize(IInstruction instruction) byte[]
}

IInstructionsTransmitter<|--BluetoothInstructionsTransmitter
IInstructionsTransmitter<|--HttpInstructionsTransmitter
BluetoothInstructionsTransmitter..>IInstructionSerializer
HttpInstructionsTransmitter..>IInstructionSerializer
```

### Serialización de Instrucciones

```mermaid
classDiagram
class IInstructionSerializer {
    <<Interface>>
    +serialize(IInstruction instruction) byte[]
}

note for PADVInstructionSerializer "PADV (Part, Axis, Direction, Value): Serializa\nla instrucción usando la convención:\nParte, Eje, Dirección, valor (ver Tabla 1)"
class PADVInstructionSerializer 

IInstructionSerializer<|--PADVInstructionSerializer
```

## Diagramas de secuencia

```mermaid
sequenceDiagram
participant Activity
participant InstructionsService
participant Promise
participant IInstructionTransmitter
participant IInstructionSerializer
actor Robot

activate Activity
Activity->>InstructionsService: sendInstruction(IInstruction)

activate InstructionsService

activate IInstructionTransmitter
InstructionsService->>IInstructionTransmitter:sendInstruction(SendInstructionRequest)

activate IInstructionSerializer
IInstructionTransmitter->>IInstructionSerializer:serialize(Instruction)
IInstructionSerializer-->>IInstructionTransmitter:byte[]
deactivate IInstructionSerializer

IInstructionTransmitter->>Robot:sendInstruction(byte[])
IInstructionTransmitter-->>InstructionsService:Runnable


activate Promise
InstructionsService->>Promise:new Promise(Runnable)
InstructionsService-->>Activity:Promise
deactivate InstructionsService

note over Activity,Robot: Esperar a que el robot de una respuesta, mientras tanto, puede seguir con su vida.

Robot-->>IInstructionTransmitter: Response
IInstructionTransmitter-->>Promise: accept()
deactivate IInstructionTransmitter

Promise->>Activity: accept()
deactivate Promise

deactivate Activity
```

## Tecnologías Utilizadas 🚀

- Android 📱
- Java ☕
- Canva 🎨
- Diagramas Draw ✏️
- Lucid Charts
- Mermaid

## Autor

- **Javier Jaramillo**
- **Jeysson Guevara**
