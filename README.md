# 🛠️ KEvaluación - Sistema de Gestión de Proyectos y Tareas

Sistema completo de gestión de proyectos y tareas desarrollado con arquitectura full-stack moderna, implementando autenticación JWT, roles de usuario y CRUD completo.

## 🚀 Características Principales

- ✅ **Autenticación y Autorización**: JWT con roles ADMIN/USER
- ✅ **Gestión de Usuarios**: CRUD con restricciones por rol
- ✅ **Gestión de Proyectos**: Cada usuario maneja sus propios proyectos
- ✅ **Gestión de Tareas**: Asignación de tareas entre usuarios y proyectos
- ✅ **Control de Acceso**: Restricciones basadas en roles
- ✅ **API RESTful**: Documentada con Swagger/OpenAPI
- ✅ **Interfaz Moderna**: Dashboard responsivo con componentes reutilizables
- ✅ **Containerización**: Deploy completo con Docker Compose

## 🏗️ Arquitectura del Sistema

### Stack Tecnológico

#### Backend
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.2.2** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL 15** - Base de datos relacional
- **JWT (JSON Web Tokens)** - Autenticación stateless
- **MapStruct 1.5.5** - Mapeo entre DTOs y entidades
- **Validation API** - Validación de datos
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias

#### Frontend
- **Next.js 15.4.3** - Framework React con SSR/SSG
- **React 19.1.0** - Biblioteca de interfaces de usuario
- **TypeScript** - Tipado estático
- **Tailwind CSS** - Framework de estilos utilitarios
- **Radix UI** - Componentes primitivos accesibles
- **React Hook Form** - Gestión de formularios
- **Zod** - Validación de esquemas
- **TanStack React Table** - Tablas avanzadas
- **Zustand** - Gestión de estado global
- **Axios** - Cliente HTTP
- **Lucide React** - Iconografía
- **Date-fns** - Manipulación de fechas

#### DevOps & Tools
- **Docker & Docker Compose** - Containerización y orquestación
- **PostgreSQL 15** - Base de datos en contenedor
- **Maven Wrapper** - Build tool del backend
- **ESLint** - Linting para JavaScript/TypeScript

## 📁 Estructura del Proyecto

```
kevaluacion1/
├── 📋 docker-compose.yml              # Orquestación de servicios
├── 📄 README.md                       # Documentación principal
├── 🗂️ Backend/
│   └── kevaluacion/                   # API Spring Boot
│       ├── 🐳 Dockerfile             # Imagen del backend
│       ├── 📋 pom.xml                # Dependencias Maven
│       ├── 🔧 mvnw & mvnw.cmd        # Maven Wrapper
│       └── 📁 src/
│           ├── main/java/com/kruger/kevaluacion/
│           │   ├── 🎯 controller/    # Controladores REST
│           │   ├── 🏗️ entity/        # Entidades JPA
│           │   ├── 📦 dto/           # Data Transfer Objects
│           │   ├── 🔄 mapper/        # MapStruct mappers
│           │   ├── 🗃️ repository/    # Repositorios JPA
│           │   ├── 🧩 service/       # Lógica de negocio
│           │   ├── 🔐 security/      # Configuración de seguridad
│           │   └── ⚙️ config/        # Configuraciones
│           ├── main/resources/
│           │   └── application.properties
│           └── test/                  # Tests unitarios e integración
└── 🗂️ Frontend/
    └── project-front/                 # Aplicación Next.js
        ├── 🐳 Dockerfile             # Imagen del frontend
        ├── 📋 package.json           # Dependencias NPM
        ├── ⚙️ next.config.ts         # Configuración Next.js
        ├── 🎨 tailwind.config.ts     # Configuración Tailwind
        └── 📁 src/
            ├── app/                   # App Router (Next.js 13+)
            │   ├── login/            # Página de autenticación
            │   └── dashboard/        # Panel principal
            │       ├── tasks/        # Gestión de tareas
            │       └── users/        # Gestión de usuarios (ADMIN)
            ├── components/           # Componentes reutilizables
            │   ├── atoms/           # Componentes básicos
            │   ├── molecules/       # Componentes compuestos
            │   └── template/        # Plantillas
            └── shared/              # Código compartido
                ├── services/        # Servicios API
                ├── hooks/          # Custom hooks
                ├── store/          # Estado global (Zustand)
                └── form/           # Utilidades de formularios
```

## 🎯 Funcionalidades por Módulo

### 🔐 Autenticación
- **Login/Logout** con JWT
- **Registro de usuarios** (solo ADMIN puede crear usuarios)
- **Protección de rutas** basada en autenticación
- **Persistencia de sesión** en localStorage
- **Interceptores HTTP** para inyección automática de tokens

### 👥 Gestión de Usuarios (Solo ADMIN)
- **Listar todos los usuarios** del sistema
- **Visualizar roles** y información de contacto
- **Control de acceso** restringido por rol

### 📋 Gestión de Proyectos
- **Crear proyectos** personales
- **Listar proyectos** propios
- **Editar/Eliminar** proyectos (solo el propietario)
- **Asociación** automática con el usuario creador

### ✅ Gestión de Tareas
- **Crear tareas** asignadas a usuarios y proyectos
- **Estados**: PENDING, IN_PROGRESS, DONE
- **Fechas límite** con validación
- **Visualización** en tabla interactiva
- **Edición/Eliminación** con permisos
- **Filtros** por usuario asignado

## 🔑 Roles y Permisos

| Funcionalidad | USER | ADMIN |
|---------------|------|-------|
| Login/Logout | ✅ | ✅ |
| Ver usuarios | ❌ | ✅ |
| Crear usuarios | ❌ | ✅ |
| Gestionar proyectos propios | ✅ | ✅ |
| Crear tareas | ❌ | ✅ |
| Ver tareas asignadas | ✅ | ✅ |
| Editar/Eliminar tareas | ❌ | ✅ |

## 🚀 Instalación y Ejecución

### Prerrequisitos
- Docker & Docker Compose
- Puerto 3000 (Frontend) y 8080 (Backend) disponibles
- Puerto 5432 (PostgreSQL) disponible

### 🐳 Ejecución con Docker Compose (Recomendado)

1. **Clonar el repositorio**
```bash
git clone https://github.com/JeremyTierra/kevaluacion.git
cd kevaluacion1
```

2. **Ejecutar todos los servicios**
```bash
docker-compose up --build
```

3. **Acceder a la aplicación**
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/kevaluacion
- Swagger UI: http://localhost:8080/kevaluacion/swagger-ui.html

### 🛠️ Ejecución en Desarrollo

#### Backend
```bash
cd Backend/kevaluacion
./mvnw spring-boot:run
```

#### Frontend
```bash
cd Frontend/project-front
npm install
npm run dev
```

#### Base de Datos (PostgreSQL)
```bash
docker run -d \
  --name postgres-dev \
  -e POSTGRES_DB=kevaluaciondb \
  -e POSTGRES_USER=keval_user \
  -e POSTGRES_PASSWORD=keval_pass \
  -p 5432:5432 \
  postgres:15
```

## 🔐 Credenciales por Defecto

El sistema crea automáticamente usuarios por defecto:

| Usuario | Contraseña | Rol | Email |
|---------|------------|-----|-------|
| `admin` | `admin123` | ADMIN | admin@kruger.com |
| `user` | `user123` | USER | user@kruger.com |

## 🌐 API Endpoints

### Autenticación
- `POST /kevaluacion/auth/login` - Iniciar sesión
- `POST /kevaluacion/auth/register` - Registrar usuario (solo ADMIN)

### Usuarios
- `GET /kevaluacion/users` - Listar usuarios (solo ADMIN)

### Proyectos
- `GET /kevaluacion/projects` - Listar proyectos del usuario
- `POST /kevaluacion/projects` - Crear proyecto
- `PUT /kevaluacion/projects/{id}` - Actualizar proyecto
- `DELETE /kevaluacion/projects/{id}` - Eliminar proyecto

### Tareas
- `GET /kevaluacion/tasks` - Listar tareas del usuario
- `POST /kevaluacion/tasks` - Crear tarea (solo ADMIN)
- `PUT /kevaluacion/tasks/{id}` - Actualizar tarea
- `DELETE /kevaluacion/tasks/{id}` - Eliminar tarea
- `GET /kevaluacion/tasks/project/{projectId}` - Tareas por proyecto

## ⚙️ Variables de Entorno

### Backend (`application.properties`)
```properties
# Servidor
server.port=8080
server.servlet.context-path=/kevaluacion

# Base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/kevaluaciondb
spring.datasource.username=keval_user
spring.datasource.password=keval_pass

# JWT
app.jwt.secret=KevaluacionSecretKeyJeremyTierra3G+@v*h>as@l^&5|@FH7_|lqN;Iu/P
```

### Frontend (`.env`)
```bash
NEXT_PUBLIC_API_URL=http://localhost:8080/kevaluacion
```

### Docker Compose
```yaml
# Variables de entorno configuradas automáticamente
SPRING_DATASOURCE_URL=jdbc:postgresql://db:5432/kevaluaciondb
NEXT_PUBLIC_API_URL=http://localhost:8080/kevaluacion
```

## 🧪 Testing

### Backend
```bash
cd Backend/kevaluacion
./mvnw test
```

### Frontend
```bash
cd Frontend/project-front
npm run lint
```

## 📊 Características Técnicas

### Seguridad
- **Autenticación JWT** con refresh token
- **Hash de contraseñas** con BCrypt
- **CORS configurado** para desarrollo
- **Validación de datos** en frontend y backend
- **Protección CSRF** deshabilitada para APIs

### Performance
- **Lazy loading** de componentes React
- **Optimización de builds** con Next.js
- **Conexiones persistentes** HTTP con Axios
- **Caché de queries** con TanStack Query

### UX/UI
- **Diseño responsive** con Tailwind CSS
- **Componentes accesibles** con Radix UI
- **Estados de carga** y feedback visual
- **Validación en tiempo real** de formularios
- **Navegación intuitiva** con breadcrumbs

## 🛑 Detener y Limpiar

```bash
# Detener servicios
docker-compose down

# Limpiar volúmenes y red
docker-compose down -v

# Limpiar todo (imágenes incluidas)
docker-compose down -v --rmi all
```

## 🔧 Troubleshooting

### Error de conexión Backend-Frontend
- Verificar que `NEXT_PUBLIC_API_URL` apunte al backend correcto
- Confirmar que el backend esté ejecutándose en el puerto 8080

### Error de base de datos
- Verificar que PostgreSQL esté ejecutándose
- Comprobar credenciales en `application.properties`
- Revisar logs con `docker-compose logs db`

### Error de build
- Limpiar caché: `docker system prune -a`
- Reconstruir: `docker-compose build --no-cache`

## 📝 Próximas Mejoras

- [ ] Implementar refresh tokens
- [ ] Agregar notificaciones en tiempo real
- [ ] Filtros avanzados en tablas
- [ ] Exportación de datos (CSV/PDF)
- [ ] Dashboard con métricas y gráficos
- [ ] Sistema de comentarios en tareas
- [ ] Historial de cambios (audit log)
- [ ] API para mobile (React Native)

## 👨‍💻 Autor

**Jeremy Tierra**  
Desarrollador Full Stack  
🔗 LinkedIn: [Jeremy Tierra](https://linkedin.com/in/jeremy-tierra)

---

## 📄 Licencia

Este proyecto fue desarrollado como parte de una evaluación técnica para Kruger.

---

⭐ **¡Gracias por revisar el proyecto!** ⭐
