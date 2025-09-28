# SIW Eventi By Night

[![Build](https://img.shields.io/badge/build-passing-brightgreen.svg)](#)
[![Java](https://img.shields.io/badge/Java-17-blue.svg)](#)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg)](#)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-💾-blue.svg)](#)
[![License](https://img.shields.io/badge/license-MIT-lightgrey.svg)](#)

**SIW Eventi By Night** è un gestionale web in **Spring Boot** pensato per vivere e organizzare la nightlife.  
Con pochi click puoi scoprire eventi, prenotare un posto, oppure – se sei proprietario – gestire locali e offerte dal tuo pannello dedicato.  

---

## ✨ Funzionalità principali

- 🎉 **Pubblico**
  - Lista completa di eventi e locali
  - Ricerca veloce per nome (eventi + locali)

- 👤 **Utenti registrati**
  - Registrazione con immagine profilo e ruolo `DEFAULT`
  - Gestione prenotazioni personali (crea, visualizza, cancella)

- 🏪 **Proprietari (`BUSINESS`)**
  - Registrazione business con upload immagine
  - Creazione, modifica ed eliminazione locali
  - Gestione eventi con immagini e prenotazioni in tempo reale

- 🛠️ **Amministratori (`ADMIN`)**
  - Dashboard back-office dedicata (`/admin/**`)
  - Controllo completo su utenti, eventi, locali e prenotazioni

---

## 🧩 Architettura

- **MVC classico**:
  - Controller → logica delle pagine pubbliche e riservate
  - Service → regole di business & gestione upload
  - Repository → CRUD via Spring Data JPA
  - Model → entità JPA con relazioni e validazione
  - View → template **Thymeleaf** responsivi e modulari

- **Sicurezza**
  - Login personalizzato con redirect per ruolo
  - Password sicure con **BCrypt**
  - Autorizzazioni granulari per pubblico, utenti, business e admin
  - Logout con invalidazione sessione e cookie

- **Gestione file**
  - Upload immagini in `uploads/` con percorsi serviti come risorse statiche

---

## ⚙️ Requisiti

- ☕ Java 17  
- 🛠️ Maven 3+  
- 🐘 PostgreSQL (db `siweventi`, user `postgres/postgres`)

---

## 🚀 Avvio rapido

1. Clona il repo
2. Configura PostgreSQL con le credenziali in `application.properties`
3. (Opzionale) Popola il db con dati demo (`import.sql`)
4. Avvia il progetto:
   ```bash
   ./mvnw spring-boot:run
``

5. Vai su `http://localhost:8080` e inizia a navigare ✨

---

## 📂 Struttura del progetto

```
SiwEventiByNight/
├── authentication/   # Config sicurezza
├── controller/       # Controller MVC
├── model/            # Entità JPA
├── repository/       # DAO con Spring Data
├── service/          # Business logic
├── templates/        # Thymeleaf views
└── uploads/          # File caricati
```

---

## 🛠️ Tech Stack

![Java](https://img.shields.io/badge/Java-17-orange?logo=java\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-enabled-darkgreen?logo=springsecurity)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-templates-brightgreen?logo=thymeleaf)
![Maven](https://img.shields.io/badge/Maven-3+-C71A36?logo=apachemaven)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14+-blue?logo=postgresql)

---

## 📌 Note

* Dipendenze test già incluse: `spring-boot-starter-test`, `spring-security-test`
* Test automatizzati ancora da implementare → cartella `src/test/java`

---

## 👥 Autori

* [micheleguidaa](https://github.com/micheleguidaa)
* [AlessandroSchmitt](https://github.com/AlessandroSchmitt)

---

Un gestionale **leggero, potente e intuitivo** per far brillare la tua notte 🌙🎶
