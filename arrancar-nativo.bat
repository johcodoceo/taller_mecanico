@echo off
title Arranque Secuencial - Taller Mecanico
echo =======================================================
echo     INICIANDO ECOSISTEMA DE MICROSERVICIOS NATIVO
echo =======================================================
echo.

:: ---------------------------------------------------------
:: 1. SERVIDOR DE DESCUBRIMIENTO (Eureka Server)
:: ---------------------------------------------------------
echo [1/3] Lanzando Servidor de Descubrimiento (Eureka Server)...
cd eureka-server
start "Eureka Server" cmd /k "mvn spring-boot:run"
cd ..

echo Esperando 20 segundos a que Eureka este completamente activo...
timeout /t 20 /nobreak
echo.

:: ---------------------------------------------------------
:: 2. MICROSERVICIOS DE NEGOCIO Y SEGURIDAD
:: ---------------------------------------------------------
echo [2/3] Lanzando los 10 Microservicios en paralelo...

cd auth-service
start "Auth Service" cmd /k "mvn spring-boot:run"
cd ..

cd cliente-service
start "Cliente Service" cmd /k "mvn spring-boot:run"
cd ..

cd inventario-service
start "Inventario Service" cmd /k "mvn spring-boot:run"
cd ..

cd mecanico-service
start "Mecanico Service" cmd /k "mvn spring-boot:run"
cd ..

cd orden-trabajo-service
start "Orden Trabajo Service" cmd /k "mvn spring-boot:run"
cd ..

cd pago-service
start "Pago Service" cmd /k "mvn spring-boot:run"
cd ..

cd security-service
start "Security Service" cmd /k "mvn spring-boot:run"
cd ..

cd user-service
start "User Service" cmd /k "mvn spring-boot:run"
cd ..

cd vehiculo-service
start "Vehiculo Service" cmd /k "mvn spring-boot:run"
cd ..

cd venta-service
start "Venta Service" cmd /k "mvn spring-boot:run"
cd ..

echo Esperando 30 segundos a que todos los servicios se registren en Eureka...
timeout /t 30 /nobreak
echo.

:: ---------------------------------------------------------
:: 3. API GATEWAY (Punto de entrada unico)
:: ---------------------------------------------------------
echo [3/3] Lanzando API Gateway...
cd api-gateway
start "API Gateway" cmd /k "mvn spring-boot:run"
cd ..

echo.
echo =======================================================
echo    ¡Todo el ecosistema ha sido lanzado con exito!
echo =======================================================
pause