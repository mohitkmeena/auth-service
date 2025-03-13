# 🚀 Authentication Service (Spring Boot + JWT + OTP)

This is an **Authentication Service** built using **Spring Boot** and **JWT**. It provides:
✅ User **Signup & Login**  
✅ JWT-based **Authentication & Authorization**  
✅ **Password Reset** with OTP verification

---

## ⚡ Tech Stack
- **Spring Boot** (REST API)
- **Spring Security** (Authentication & JWT)
- **MySQL** (User Data Storage)
- **BCrypt** (Password Hashing)
- **Java Mail API** (Email OTP Verification)

---

## 🚀 Installation & Setup

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/mohitkmeena/auth-service.git
cd auth-service
```

### 2️⃣  add/update application.properties
```sh
server.port=8080

# Database Config
spring.datasource.url=jdbc:mysql://localhost:3306/authdb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

# JWT Config
app.jwt-secret=your-secret-key
app.jwt-expiration=3600000

# Email Config (for OTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-email-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

```
## 🔑 Api Endpoints
### 1️⃣   Signup and login Endpoints
📌 User Signup


```sh
POST /v1/auth/signup
```
🔹 Request Body (JSON)

```sh
{
  "username": "mohit",
  "email": "mohit@example.com",
  "password": "password123",
  "name":"mohit"
}
```
🔹 Respone
```shell
"Sign up successful"

```
📌 User Login
```sh
POST /v1/auth/login
```
🔹 Request Body (JSON)
```sh
{
  "username": "mohit",
  "password": "password123"
}
```
🔹Response 
```sh
{
  "token": "your-jwt-token"
}

```

📌 Get the current authenticated User

```sh
POST /v1/ping/ping
```
```shell
Authorization: Bearer your-jwt-token
```
🔹Response
```sh
"Authenticated User: mohit"
```

### 2️⃣   Forget Password Endpoints

📌 Verify Email


```sh
POST /v1/password/verify
```

```sh
email: abc@example.com
```
🔹 Respone
```shell
"email sent successfully "

```
📌 Otp verification
```sh
POST /v1/password/verify-otp
```
🔹 Request Body (JSON)
```sh
{
  "email": "abc@example.com",
  "otp":123_123
}
```
🔹Response
```shell
{
  "Otp verified"
}

```
📌 Update password
```sh
POST /v1/password/update-password
```
🔹 Request Body (JSON)
```sh
{
  "email": "abc@example.com",
  "password":"abc@2006",
  "rpassword":"abc@2006"
}
```
🔹Response
```shell
{
  "password updated successfully"
}

```



