# 🚀 Multi-Tenant SaaS Project Management System

> A production-ready backend built with **Spring Boot** implementing **multi-tenancy, JWT authentication, RBAC, and scalable architecture**.

---

## ✨ Features

* 🏢 Multi-Tenant Architecture (Strict Isolation)
* 🔐 JWT Authentication (Tenant-aware)
* 📝 User Registration & Invite System
* 🛡️ Role-Based Access Control (OWNER / MEMBER)
* 📁 Project Management
* ✅ Task Management (Full CRUD + PATCH updates)
* 📦 DTO-Based Clean API Design

---

## 🧠 Architecture Overview

```
Controller → Service → Repository → Database
```

### Cross-Cutting Layers:

* 🔐 Security (JWT + RBAC)
* 🏢 Tenant Context (ThreadLocal)
* 🧾 Activity Logging

---

## 🏗️ Project Structure

```
src/main/java/com/saas/projectmanager/

├── controller/
│   └── auth/
├── service/
│   └── impl/
├── repository/
├── dto/
├── exception/
├── domain/
│   ├── model/
│   └── valueobject/
├── security/
│   └── jwt/
├── tenant/
```

---

## 🔐 Authentication System

### ✅ Features

* Login with JWT
* Register new user
* Invite-based onboarding
* Tenant automatically resolved from JWT

---

### 🔄 Flow

1. User registers or gets invited

2. Login → JWT issued

3. JWT contains:

    * userId
    * tenantId
    * role

4. Each request:

    * Extract tenant
    * Set in `TenantContext`
    * Apply tenant filtering

---

## 🏢 Multi-Tenancy

* Implemented using **ThreadLocal TenantContext**
* Every query scoped by `tenantId`
* Ensures **complete data isolation**

---

## 🛡️ Role-Based Access Control

| Role   | Access                                              |
| ------ | --------------------------------------------------- |
| OWNER  | Full control                                        |
| MEMBER | Limited (cannot create project, restricted actions) |

---

## 📁 Project Module

* ✅ Create Project
* ✅ Get All Projects
* ✅ Get Project by ID
* ✅ Delete Project

---

## ✅ Task Module

### Features:

* ✅ Create Task
* ✅ Get Tasks by Project
* ✅ Update Task (PUT - full update)
* ✅ Patch Task (PATCH - partial update)
* ✅ Delete Task

---

### 🔥 PATCH Support (Important Feature)

Allows partial updates like:

```json
{
  "status": "COMPLETED"
}
```

No need to send full object → **efficient & modern API design**

---

## 🧾 Activity Logging

Tracks:

* Project actions
* Task updates
* User activities

Useful for:

* Debugging
* Auditing
* Monitoring

---

## 📦 DTO-Based API

### Benefits:

* No entity exposure
* Clean API contracts
* Better security

---

## 🔄 API Endpoints

### 🔐 Auth

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | `/api/auth/register` |
| POST   | `/api/auth/login`    |
| POST   | `/api/auth/invite`   |

---

### 📁 Projects

| Method | Endpoint             |
| ------ | -------------------- |
| POST   | `/api/projects`      |
| GET    | `/api/projects`      |
| GET    | `/api/projects/{id}` |
| DELETE | `/api/projects/{id}` |

---

### ✅ Tasks

| Method | Endpoint                         |
| ------ | -------------------------------- |
| POST   | `/api/tasks`                     |
| GET    | `/api/tasks/project/{projectId}` |
| PUT    | `/api/tasks/{id}`                |
| PATCH  | `/api/tasks/{id}`                |
| DELETE | `/api/tasks/{id}`                |

---

## 📌 Sample Response

```json
{
  "id": "uuid",
  "name": "Project A",
  "description": "Demo",
  "createdAt": "2026-02-16T18:20:33"
}
```

---

## ⚙️ Tech Stack

* ☕ Java 17+
* 🌱 Spring Boot
* 🔐 Spring Security + JWT
* 🗄️ Hibernate / JPA
* 🐬 MySQL / PostgreSQL

---

## 🚀 Getting Started

### 1️⃣ Clone Repo

```bash
git clone https://github.com/bipinnegi/project-manager-REST-API-SpringBoot.git
```

---

### 2️⃣ Configure Database

Update:

```
application.properties
```

---

### 3️⃣ Run Application

```bash
mvn spring-boot:run
```

---

## 🧪 Testing (Postman)

1. Register / Login
2. Copy JWT
3. Add header:

```
Authorization: Bearer <token>
```

4. Test APIs

---

## 📈 Current Status

| Feature          | Status |
| ---------------- | ------ |
| Authentication   | ✅      |
| Registration API | ✅      |
| Multi-Tenancy    | ✅      |
| Project Module   | ✅      |
| Task Module      | ✅      |
| PATCH Support    | ✅      |
| RBAC             | ✅      |
| Activity Logs    | ✅      |

---

## 🔮 Future Enhancements

* 🧾 Activity Logging
* 📄 Pagination & Sorting
* 📧 Email-based invite acceptance
* 🌐 Angular Frontend Integration
* 📊 Dashboard & Analytics
* 🧩 Microservices architecture

---

## 👨‍💻 Author

**Bipin Negi**
Computer Science Engineer

---

## ⭐ Why This Project Matters

This project demonstrates:

* Real-world SaaS backend architecture
* Clean code & scalability
* Secure multi-tenant system design

👉 Perfect for:

* Backend Developer Roles
* System Design Interviews
* Production-level portfolio

---

⭐ *Give it a star if you found it useful!*
