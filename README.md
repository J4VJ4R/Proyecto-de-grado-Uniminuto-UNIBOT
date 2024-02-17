# Aplicativo móvil para el control de brazo robótico

## Introducción

Se quiere construir un aplicativo móvil que permita el control de un brazo robótico, de tal manera que se pueda enviar movimientos por medio de scripts que manipulen los nodos del brazo robótico.

![Brazo Robótico](./images_readme/unibot.png)

Texto adicional sobre la introducción y detalles del proyecto...

## Movimiento en grados o tiempo para un brazo de tres grados de libertad

A continuación, se detallan los movimientos para un brazo de tres grados de libertad:

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
class Rotation {
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

class Claw {
    +getOpenClosed() boolean
}

IInstruction <|--Rotation
IInstruction <|--Claw
Rotation..>Axis
Rotation..>Direction
InstructionsService..>IInstruction
InstructionsService..>Promise
InstructionsService..>IInstructionsTransmitter
IInstructionsTransmitter..>SendInstructionRequest
```

## Tecnologías Utilizadas 🚀

- Android 📱
- Java ☕
- Canva 🎨
- Diagramas Draw ✏️

## Autor

- **Javier Jaramillo**
- **Jeysson Guevara**
