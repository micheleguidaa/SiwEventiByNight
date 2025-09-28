# SIW Eventi By Night

SIW Eventi By Night è un gestionale web per eventi serali basato su Spring Boot. L'applicazione permette agli utenti di scoprire locali ed eventi, prenotare posti e gestire l'offerta tramite un pannello dedicato a proprietari e amministratori.

## Funzionalità principali

### Navigazione pubblica
- Visualizzazione dell'elenco completo degli eventi (`/events`). 【F:src/main/java/it/uniroma3/siw/controller/EventController.java†L38-L45】
- Consultazione dei locali che ospitano gli eventi (`/locals`). 【F:src/main/java/it/uniroma3/siw/controller/LocalController.java†L34-L41】
- Ricerca simultanea per nome di eventi e locali, con pagina riepilogativa dei risultati (`/search`, `/founds`). 【F:src/main/java/it/uniroma3/siw/controller/SearchController.java†L25-L52】

### Utenti registrati
- Registrazione con caricamento di immagine profilo e assegnazione automatica del ruolo `DEFAULT`. 【F:src/main/java/it/uniroma3/siw/controller/AuthenticationController.java†L66-L106】【F:src/main/java/it/uniroma3/siw/service/UserService.java†L69-L88】
- Visualizzazione e cancellazione delle proprie prenotazioni (`/reservations`). 【F:src/main/java/it/uniroma3/siw/controller/ReservationController.java†L24-L39】
- Possibilità di prenotare un evento disponibile (`/addReservation`). 【F:src/main/java/it/uniroma3/siw/controller/ReservationController.java†L45-L52】

### Proprietari (ruolo `BUSINESS`)
- Registrazione dedicata con caricamento di immagine (`/registerBusiness`). 【F:src/main/java/it/uniroma3/siw/controller/AuthenticationController.java†L110-L126】
- Gestione dei propri locali (creazione, modifica, eliminazione) con supporto all'upload immagini. 【F:src/main/java/it/uniroma3/siw/controller/LocalController.java†L82-L142】
- Gestione del catalogo eventi associati ai locali, inclusi caricamento e sostituzione delle immagini. 【F:src/main/java/it/uniroma3/siw/controller/EventController.java†L85-L139】
- Verifica dell'esistenza di prenotazioni per i propri locali tramite endpoint REST dedicato (`/api/reservations/existsByOwner/{ownerId}`). 【F:src/main/java/it/uniroma3/siw/controller/ReservationController.java†L55-L61】

### Amministratori (ruolo `ADMIN`)
- Accesso al back-office con dashboard dedicata (`/admin/**`). 【F:src/main/java/it/uniroma3/siw/authentication/AuthConfiguration.java†L52-L71】
- Gestione completa di eventi, locali, utenti e prenotazioni, con form di creazione/modifica basati su Thymeleaf e validazione lato server. 【F:src/main/java/it/uniroma3/siw/controller/EventController.java†L53-L112】【F:src/main/java/it/uniroma3/siw/controller/LocalController.java†L43-L99】【F:src/main/java/it/uniroma3/siw/controller/UserController.java†L17-L26】【F:src/main/java/it/uniroma3/siw/controller/ReservationController.java†L30-L34】

## Architettura

L'applicazione è sviluppata secondo un'architettura MVC classica:

- **Controller**: orchestrano la logica delle pagine pubbliche e delle aree riservate (eventi, locali, autenticazione, prenotazioni). 【F:src/main/java/it/uniroma3/siw/controller/EventController.java†L24-L143】【F:src/main/java/it/uniroma3/siw/controller/LocalController.java†L23-L143】
- **Service**: incapsulano la logica di business e la gestione degli upload, delegando persistenza ai repository. 【F:src/main/java/it/uniroma3/siw/service/EventService.java†L15-L71】【F:src/main/java/it/uniroma3/siw/service/LocalService.java†L15-L97】
- **Repository**: interfacce Spring Data JPA per CRUD e query derivate su eventi, locali, prenotazioni, utenti e credenziali. 【F:src/main/java/it/uniroma3/siw/repository/EventRepository.java†L1-L16】【F:src/main/java/it/uniroma3/siw/repository/ReservationRepository.java†L1-L16】
- **Model**: entità JPA annotate con vincoli di validazione e relazioni (es. Evento ↔ Locale ↔ Proprietario, Utente ↔ Prenotazioni). 【F:src/main/java/it/uniroma3/siw/model/Event.java†L20-L97】【F:src/main/java/it/uniroma3/siw/model/Local.java†L14-L65】【F:src/main/java/it/uniroma3/siw/model/Reservation.java†L8-L38】【F:src/main/java/it/uniroma3/siw/model/User.java†L18-L80】
- **View**: template Thymeleaf in `src/main/resources/templates` con frammenti riutilizzabili e layout per utenti, admin e proprietari.

### Sicurezza

L'autenticazione e l'autorizzazione sono gestite da Spring Security con password codificate in BCrypt. Il file `AuthConfiguration` definisce:
- Login form personalizzato (`/login`) e redirect post-login su base ruolo. 【F:src/main/java/it/uniroma3/siw/authentication/AuthConfiguration.java†L52-L86】
- Autorizzazioni granulari per rotte pubbliche, utenti autenticati, proprietari e amministratori. 【F:src/main/java/it/uniroma3/siw/authentication/AuthConfiguration.java†L57-L75】
- Logout con invalidazione sessione e cancellazione cookie. 【F:src/main/java/it/uniroma3/siw/authentication/AuthConfiguration.java†L76-L85】

Il `GlobalController` espone attributi di sessione condivisi (utente corrente, ruolo, proprietario) per i template. 【F:src/main/java/it/uniroma3/siw/controller/GlobalController.java†L20-L67】

### Gestione file

Le immagini caricate per eventi, locali, utenti e proprietari vengono salvate nella directory `uploads/` tramite `FileService`, che crea le cartelle se assenti e restituisce il percorso relativo per l'esposizione tramite Spring static resources. 【F:src/main/java/it/uniroma3/siw/service/FileService.java†L13-L36】

## Requisiti

- Java 17 【F:pom.xml†L18-L19】
- Maven 3+
- PostgreSQL (database `siweventi`, utente `postgres`/`postgres`) 【F:src/main/resources/application.properties†L6-L17】

## Avvio del progetto

1. Clonare il repository e portarsi nella cartella principale.
2. Configurare un database PostgreSQL con nome, utente e password coerenti con `application.properties`, oppure aggiornare le proprietà a seconda dell'ambiente. 【F:src/main/resources/application.properties†L6-L17】
3. (Opzionale) Popolare il database con dati di esempio tramite `import.sql`, che crea tre locali e relativi eventi. 【F:src/main/resources/import.sql†L1-L15】
4. Avviare l'applicazione con Maven:
   ```bash
   ./mvnw spring-boot:run
   ```
5. Aprire `http://localhost:8080` e accedere alle funzionalità pubbliche o procedere con la registrazione/login.

## Struttura del progetto

```
SiwEventiByNight/
├── src/main/java/it/uniroma3/siw/
│   ├── authentication/        # Configurazione Spring Security
│   ├── controller/            # Controller MVC e validatori
│   ├── model/                 # Entità JPA
│   ├── repository/            # Interfacce Spring Data JPA
│   └── service/               # Logica di business e gestione file
├── src/main/resources/
│   ├── static/                # Risorse statiche (CSS, immagini)
│   ├── templates/             # Template Thymeleaf per le varie aree
│   ├── application.properties # Configurazione applicazione e datasource
│   └── import.sql             # Dati iniziali facoltativi
└── uploads/                   # Directory runtime per i file caricati
```

## Test

Il progetto include le dipendenze per i test (`spring-boot-starter-test`, `spring-security-test`) ma non fornisce ancora suite di test automatizzate. È possibile estendere `src/test/java` per coprire i casi d'uso principali.

## Licenza

Inserire qui i dettagli della licenza del progetto, se disponibili.

