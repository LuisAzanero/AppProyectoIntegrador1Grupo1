# 🚚 Sistema de Gestión de Flota y Mantenimiento Vehicular - TRANSVISA

Este proyecto comprende el diseño e implementación del sistema core para la empresa **TRANSVISA E.I.R.L.**, desarrollado bajo un enfoque ágil y estructurado mediante una **Arquitectura en Capas Decapoplada** utilizando el lenguaje Java (Maven) conforme a los estándares académicos del curso Integrador 1 de la Universidad Tecnológica del Perú (UTP).

---

## 📝 1. Descripción del Problema
TRANSVISA, empresa dedicada al transporte en el sector avícola, gestionaba el control de ingresos, salidas de garita e historial de talleres mediante bitácoras físicas y hojas de cálculo independientes. 

La falta de centralización provocaba:
* Registro de datos de kilometraje inconsistentes o erróneos.
* Despacho de unidades con mantenimientos vencidos o fallas mecánicas críticas.
* Incremento en costos por reparaciones correctivas y retraso en decisiones gerenciales.

### 💡 Solución Desarrollada
Un sistema web integrado que centraliza las operaciones en garita y taller, implementando un algoritmo de **Bloqueo Preventivo de Despacho** que impide la salida de cualquier camión que posea órdenes de mantenimiento abiertas.

---

## 🛠️ 2. Módulos del Sistema Integrado
1. **Módulo 1: Seguridad y Control de Accesos:** Autenticación basada en credenciales y control de acceso por roles (`ADMIN`, `MECANICO`, `OPERADOR`, `GERENTE`).
2. **Módulo 2: Gestión de Flota:** Panel de administración de vehículos (CRUD de Placa, Marca, Modelo, Kilometraje y Estado Operativo).
3. **Módulo 3: Control de Garita:** Registro en tiempo real de entradas y salidas asociando conductor, destino y kilometraje actual.
4. **Módulo 4: Gestión de Mantenimiento:** Apertura y cierre de órdenes de servicio preventivas o correctivas asignadas a mecánicos específicos.
5. **Módulo 5: Gestión de Repuestos:** Control transaccional de inventarios que descuenta de forma automática el stock usado en taller.

---

## 🏗️ 3. Arquitectura del Software
Para cumplir con los lineamientos del **Taller de Arquitectura de la Semana 9**, el sistema implementa una arquitectura desacoplada basada en la **Inversión de Dependencias (IoC)** e **Inyección por Constructor**, aislando por completo las reglas del negocio de los componentes físicos de persistencia.

### Diagrama de Paquetes y Componentes

```text
pe.edu.utp.transvisa
│
├── domain                  <-- Capa de Dominio (Entidades puras / Clases POJO)
│   ├── Vehiculo.java
│   └── MovimientoGarita.java
│
├── persistence             <-- Capa de Persistencia (Abstracciones / Interfaces)
│   ├── VehiculoRepository.java (Interfaz)
│   └── MovimientoRepository.java (Interfaz)
│
├── persistence.mock        <-- Infraestructura de Simulación (Componentes Mock)
│   ├── MockVehiculoRepository.java
│   └── MockMovimientoRepository.java
│
├── business                <-- Capa de Negocio (Lógica de Servicios Estrictos)
│   └── MovimientoService.java
│
└── presentation            <-- Capa de Presentación (Controlador / Orquestador)
    └── MainApplication.java
