# REPAXIO - ARCHITECTURE DIAGRAMS

## 1. System Architecture Overview

\\\
┌─────────────────────────────────────────────────────────────────────────┐
│                           INTERNET/USERS                                │
└────────────────────┬────────────────────────────────────────────────────┘
                     │ HTTPS
┌─────────────────────────────────────────────────────────────────────────┐
│                   AZURE FRONT DOOR (CDN + SSL)                          │
│              Global routing, DDoS protection, caching                    │
└─────────────────────┬───────────────────────────────────────────────────┘
                      │
         ┌────────────┼────────────┐
         │            │            │
         ↓            ↓            ↓
    ┌─────────┐  ┌─────────┐  ┌─────────┐
    │ React   │  │ App     │  │ App     │
    │ Static  │  │ Service │  │ Service │
    │ Web Apps│  │ Node 1  │  │ Node 2  │
    └─────────┘  └────┬────┘  └────┬────┘
        │              │            │
        └──────────────┼────────────┘
                       │ Shared
                       ↓
         ┌─────────────────────────┐
         │  Azure SQL Database     │
         │  - Primary replica      │
         │  - Read replica (async) │
         │  - Automated backups    │
         └─────────────────────────┘
\\\

## 2. Three-Tier Architecture

\\\
╔════════════════════════════════════════════════════════════════════════╗
║                        PRESENTATION TIER                              ║
║                  React 18 + TypeScript (Vite)                         ║
║  ┌──────────────────────────────────────────────────────────────────┐ ║
║  │ Dashboard │ Reviews │ Businesses │ Subscriptions │ Analytics   │ ║
║  └──────────────────────────────────────────────────────────────────┘ ║
║  Components: Sidebar, Header, Forms, Tables, Charts                 ║
║  State: Auth Context, Dashboard Context, Notifications              ║
║  Styles: Tailwind CSS (Dark Mode Support)                           ║
╚════════════════────════════════────────────────────────────────────════╝
                              │
                    HTTPS REST API
                              │
                              ↓
╔════════════════════════════════════════════════════════════════════════╗
║                      BUSINESS LOGIC TIER                              ║
║             ASP.NET Core 9.0 (Clean Architecture)                     ║
║  ┌──────────────────────────────────────────────────────────────────┐ ║
║  │  Controllers (AuthController, BusinessController, etc)          │ ║
║  │  Middleware (Auth, CORS, Exception Handling)                    │ ║
║  └──────────────────────────────────────────────────────────────────┘ ║
║  ┌──────────────────────────────────────────────────────────────────┐ ║
║  │  Services (Business Logic Layer)                                │ ║
║  │  - BusinessService, ReviewService                              │ ║
║  │  - SubscriptionService, NotificationService                    │ ║
║  │  - IntegrationService                                          │ ║
║  └──────────────────────────────────────────────────────────────────┘ ║
║  ┌──────────────────────────────────────────────────────────────────┐ ║
║  │  Repositories (Data Access Abstraction)                         │ ║
║  │  - Generic Repository Pattern                                  │ ║
║  │  - Specification Pattern                                       │ ║
║  └──────────────────────────────────────────────────────────────────┘ ║
╚════════════════════════════════════════════════════════════════════════╝
                              │
                     Entity Framework Core
                              │
                              ↓
╔════════════════════════════════════════════════════════════════════════╗
║                        DATA ACCESS TIER                               ║
║            Azure SQL Database + Entity Framework Core                  ║
║  ┌──────────────────────────────────────────────────────────────────┐ ║
║  │  Tables: Users, Businesses, Reviews, Subscriptions, etc         │ ║
║  │  Indexes: OptimizedQueryPerformance                             │ ║
║  │  Backups: Automated daily + 30-day retention                    │ ║
║  │  Security: TDE + Azure Key Vault                                │ ║
║  └──────────────────────────────────────────────────────────────────┘ ║
╚════════════════════════════════════════════════════════════════════════╝
\\\

## 3. Microservices Communication Flow

\\\
┌──────────────┐
│   Frontend   │
│   (React)    │
└──────┬───────┘
       │ HTTP Request (with JWT)
       ↓
┌──────────────────────────┐
│   API Gateway            │
│   (CORS + Auth)          │
└──────┬───────────────────┘
       │
       ├─→ [Route to Controller]
       │
       ↓
┌──────────────────────────────────────────────┐
│   Request Processing Pipeline                │
│  ┌────────────────────────────────────────┐  │
│  │ 1. Authorization Middleware            │  │
│  │    - Validate JWT token                │  │
│  │    - Extract claims                    │  │
│  └────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────┐  │
│  │ 2. Route to Service                    │  │
│  │    - Call BusinessService.GetAll()     │  │
│  └────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────┐  │
│  │ 3. Repository Pattern                  │  │
│  │    - Build EF query                    │  │
│  │    - Apply filters/pagination          │  │
│  └────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────┐  │
│  │ 4. Database Query                      │  │
│  │    - Execute T-SQL                     │  │
│  │    - Return results                    │  │
│  └────────────────────────────────────────┘  │
│  ┌────────────────────────────────────────┐  │
│  │ 5. Response Mapping                    │  │
│  │    - Entity → DTO                      │  │
│  │    - Serialize to JSON                 │  │
│  └────────────────────────────────────────┘  │
└────────────────┬─────────────────────────────┘
                 │ HTTP Response (JSON)
                 ↓
           ┌──────────────┐
           │   Frontend   │
           │   (React)    │
           │  Display     │
           └──────────────┘
\\\

## 4. Component Interaction Diagram

\\\
FRONTEND
┌────────────────────────────────────────────────┐
│                React Application               │
│  ┌──────────────────────────────────────────┐  │
│  │  Dashboard Page                          │  │
│  │  ┌─────────────┐  ┌─────────────────┐    │  │
│  │  │ Stats Card  │  │ Review Chart    │    │  │
│  │  └──────┬──────┘  └────────┬────────┘    │  │
│  │         └──────────┬───────┘             │  │
│  │              useAPI Hook                │  │
│  └──────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────┐  │
│  │  Reviews Page                            │  │
│  │  ┌─────────────┐  ┌─────────────────┐    │  │
│  │  │ ReviewList  │  │ ReviewDetail    │    │  │
│  │  └──────┬──────┘  └────────┬────────┘    │  │
│  │         └──────────┬───────┘             │  │
│  │           useReviews Hook                │  │
│  └──────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────┐  │
│  │  Global Context                          │  │
│  │  - AuthContext (user, token)             │  │
│  │  - NotificationContext (toasts)          │  │
│  └──────────────────────────────────────────┘  │
└────────┬───────────────────────────────────────┘
         │ fetch /api/...
         ↓
BACKEND
┌────────────────────────────────────────────────┐
│        ASP.NET Core API                        │
│  ┌──────────────────────────────────────────┐  │
│  │ BusinessController                      │  │
│  │  - GET /api/businesses                   │  │
│  │  - POST /api/businesses                  │  │
│  └───────────┬────────────────────────────┘  │
│  ┌───────────────────────────────────────────┐ │
│  │ BusinessService                         │  │
│  │  - GetAll(), Create(), Update()          │  │
│  └───────────┬────────────────────────────┘  │
│  ┌───────────────────────────────────────────┐ │
│  │ Repository<Business>                    │  │
│  │  - Query(), SaveAsync()                  │  │
│  └───────────┬────────────────────────────┘  │
└──────────────┬───────────────────────────────┘
               │ DbContext.SaveChanges()
               ↓
DATABASE
┌────────────────────────────────────────────────┐
│        Azure SQL Database                      │
│  ┌──────────────────────────────────────────┐  │
│  │ Businesses Table                        │  │
│  │ - Id, Name, Website, UserId, etc.       │  │
│  └──────────────────────────────────────────┘  │
│  ┌──────────────────────────────────────────┐  │
│  │ Reviews Table                           │  │
│  │ - Id, BusinessId, Content, Rating, etc. │  │
│  └──────────────────────────────────────────┘  │
└────────────────────────────────────────────────┘
\\\

## 5. Database Entity Relationship Diagram (ERD)

\\\
┌──────────────┐
│    Users     │
├──────────────┤
│ Id (PK)      │
│ Email        │◄───────┐
│ FullName     │        │ 1:Many
│ AuthProviderId
└──────┬───────┘        │
       │ 1:Many         │
       │                │
       ↓                │
┌──────────────────┐    │
│   Businesses     │    │
├──────────────────┤    │
│ Id (PK)          │    │
│ UserId (FK)      ├────┘
│ Name             │
│ Website          │
└──────┬───────────┘
       │ 1:Many
       ↓
┌─────────────────────┐      ┌─────────────────────┐
│     Reviews         │      │ PlatformConnections │
├─────────────────────┤      ├─────────────────────┤
│ Id (PK)             │      │ Id (PK)             │
│ BusinessId (FK)     │◄─────│ BusinessId (FK)     │
│ Rating              │ 1:Many
│ Content             │      │ Platform            │
│ Platform            │      │ AccessToken         │
│ CreatedAt           │      │ LastSyncedAt        │
└─────────────────────┘      └─────────────────────┘

┌──────────────────────┐
│  Subscriptions       │
├──────────────────────┤
│ Id (PK)              │
│ BusinessId (FK)      │◄─ 1:1 (Business → Subscription)
│ StripeCustomerId     │
│ Status (active/etc)  │
│ StartDate, EndDate   │
└──────────────────────┘

┌──────────────────────┐
│ ReviewResponses      │
├──────────────────────┤
│ Id (PK)              │
│ ReviewId (FK)        │◄─ 1:1 (Review → Response)
│ ResponseText         │
│ CreatedAt            │
└──────────────────────┘
\\\

## 6. Authentication & Authorization Flow

\\\
LOGIN FLOW
┌──────────────┐
│  User Login  │
└────────┬─────┘
         │ Clicks "Sign In"
         ↓
    ┌─────────────────────────┐
    │  Redirected to Auth0    │
    │  https://auth.repaxio   │
    └────────┬────────────────┘
             │ User enters email/password
             ↓
    ┌─────────────────────────┐
    │  Auth0 Validates        │
    │  Credentials            │
    └────────┬────────────────┘
             │ Valid?
             ├─ No → Redirect with error
             │
             ├─ Yes ↓
         ┌──────────────────────┐
         │ Auth0 Returns Code   │
         └────────┬─────────────┘
                  │
                  ↓
         ┌──────────────────────────────┐
         │ Backend Token Exchange       │
         │ Code → Access Token + JWT    │
         └────────┬─────────────────────┘
                  │
                  ↓
         ┌──────────────────────────────┐
         │ Store JWT in HttpOnly Cookie │
         └────────┬─────────────────────┘
                  │
                  ↓
         ┌──────────────────────────────┐
         │ Redirect to Dashboard        │
         └──────────────────────────────┘

API REQUEST FLOW
┌──────────────────┐
│ API Request      │
│ + JWT in header  │
└────────┬─────────┘
         │
         ↓
┌──────────────────────────────┐
│ JWT Middleware               │
│ - Validate signature         │
│ - Check expiry               │
│ - Extract claims             │
└────────┬─────────────────────┘
         │ Valid?
         ├─ No → 401 Unauthorized
         │
         ├─ Yes ↓
    ┌────────────────────┐
    │ Create Principal   │
    │ With claims        │
    └────────┬───────────┘
             │
             ↓
    ┌────────────────────────────┐
    │ Authorization Middleware   │
    │ Check [Authorize] attr     │
    │ Verify roles               │
    └────────┬───────────────────┘
             │ Authorized?
             ├─ No → 403 Forbidden
             │
             ├─ Yes ↓
        ┌──────────────────┐
        │ Execute Action   │
        │ Process request  │
        └──────────────────┘
\\\

## 7. Data Flow Diagram

\\\
REVIEW SYNCHRONIZATION FLOW
┌────────────────┐
│ Background Job │ (Scheduled hourly)
│ ReviewSync Task│
└────────┬───────┘
         │
         ├─→ ┌─────────────────────────┐
         │   │ Get all active business │
         │   │ with platform tokens    │
         │   └────────┬────────────────┘
         │            │
         │            ↓
         │   ┌──────────────────────┐
         │   │ For each business:   │
         │   │ Connect to platform  │
         │   │ (Google/Yelp/FB)     │
         │   └────────┬─────────────┘
         │            │
         │            ↓
         │   ┌──────────────────────┐
         │   │ Fetch new reviews    │
         │   │ from platform API    │
         │   └────────┬─────────────┘
         │            │
         │            ↓
         │   ┌──────────────────────┐
         │   │ Transform review data│
         │   │ to local format      │
         │   └────────┬─────────────┘
         │            │
         │            ↓
         │   ┌──────────────────────┐
         │   │ Check if review      │
         │   │ already exists       │
         │   └────────┬─────────────┘
         │            │
         │         ┌──┴────┐
         │         │ New?  │
         │         └──┬────┘
         │            │
         │    ┌───────┴───────┐
         │    ↓               ↓
         │  [Yes]         [No]
         │    │             │
         │    ↓             ↓
         │  Insert       Skip
         │  Record
         │    │
         │    ↓
         └─→ ┌──────────────────────┐
             │ Store in Database    │
             │ with timestamp       │
             └────────┬─────────────┘
                      │
                      ↓
             ┌──────────────────────┐
             │ Send notification    │
             │ to business owner    │
             │ (new review alert)   │
             └──────────────────────┘

SUBSCRIPTION PAYMENT FLOW
┌──────────────┐
│ Business     │
│ Selects Plan │
└────────┬─────┘
         │
         ↓
    ┌─────────────────────┐
    │ Frontend calls      │
    │ POST /api/subscriptions
    └────────┬────────────┘
             │
             ↓
    ┌──────────────────────────────┐
    │ Backend:                     │
    │ Create Stripe Customer       │
    │ (if new)                     │
    └────────┬─────────────────────┘
             │
             ↓
    ┌──────────────────────────────┐
    │ Call Stripe API              │
    │ Create Subscription          │
    └────────┬─────────────────────┘
             │ Returns subscription_id
             ↓
    ┌──────────────────────────────┐
    │ Save to Database             │
    │ Store StripeCustomerId       │
    │ Store StripeSubscriptionId   │
    └────────┬─────────────────────┘
             │
             ↓
    ┌──────────────────────────────┐
    │ Stripe Webhook →             │
    │ /api/webhooks/stripe         │
    │ (charge.succeeded)           │
    └────────┬─────────────────────┘
             │
             ↓
    ┌──────────────────────────────┐
    │ Update Subscription Status   │
    │ Mark as active               │
    └────────┬─────────────────────┘
             │
             ↓
    ┌──────────────────────────────┐
    │ Send confirmation email      │
    │ (via Twilio/SendGrid)        │
    └──────────────────────────────┘
\\\

## 8. Deployment Architecture

\\\
┌───────────────────────────────────────────────────────────┐
│                     GIT REPOSITORY                        │
│                    (GitHub - main)                        │
└───────────────────────┬─────────────────────────────────┘
                        │ Git Push
                        ↓
        ┌───────────────────────────────────┐
        │   GitHub Actions CI/CD            │
        ├───────────────────────────────────┤
        │ Trigger: On push to main branch   │
        └───────────┬───────────────────────┘
                    │
     ┌──────────────┼──────────────┬─────────────┐
     ↓              ↓              ↓             ↓
 [Build]       [Test]         [Scan]       [Security]
 dotnet build  xUnit tests    SonarQube    SAST scan
     │              │              │             │
     └──────────────┼──────────────┴─────────────┘
                    ↓
         ┌──────────────────────────┐
         │   Docker Build           │
         │ Build container image    │
         │ Tag: latest, vX.X.X      │
         └──────────┬───────────────┘
                    │
                    ↓
         ┌──────────────────────────────────┐
         │ Push to Azure Container Registry │
         │ (ACR)                            │
         └──────────┬─────────────────────┘
                    │
         ┌──────────┴──────────┐
         │                     │
         ↓                     ↓
    [Staging]           [Production]
    Deploy to           Deploy to
    Staging slot        Production slot
         │                     │
         ↓                     ↓
    Health Check         Health Check
         │                     │
      Passed?              Passed?
         │                     │
    [Yes]│[No]         [Yes]│[No]
         │   ↓             │   ↓
         ↓ Rollback        ↓ Rollback
    Monitor          Available for
                     traffic
\\\

## 9. External Integration Architecture

\\\
┌──────────────────────────────────────────────────────────┐
│              REPAXIO CORE APPLICATION                    │
│  (ASP.NET Core Backend + React Frontend)                │
└────────────────┬─────────────────────────────────────────┘
                 │
     ┌───────────┼───────────┬──────────┬──────────┐
     │           │           │          │          │
     ↓           ↓           ↓          ↓          ↓
 ┌─────────┐ ┌───────┐ ┌─────────┐ ┌────────┐ ┌─────────┐
 │ Auth0   │ │Stripe │ │ Twilio  │ │ Google │ │  Yelp   │
 │ OAuth   │ │Payment│ │   SMS   │ │API &   │ │  API    │
 │ JWT     │ │Webhook│ │ Webhook │ │Places  │ │         │
 └────┬────┘ └───┬───┘ └────┬────┘ └────┬───┘ └────┬────┘
      │          │          │           │          │
      │Flow:     │Flow:     │Flow:      │Flow:    │Flow:
      │          │          │           │          │
      │Validate  │Process   │Send       │Sync    │Sync
      │JWT       │Payment   │SMS        │Reviews │Reviews
      │token     │Manage    │alerts     │        │
      │          │subs      │           │        │
      │          │          │           │        │
      └──────────┼──────────┼───────────┴────────┘
                 │          │
                 └──────┬───┘
                        │
                        ↓
           ┌─────────────────────┐
           │  Notification Hub   │
           │  (Queue system)     │
           └─────────────────────┘
\\\

## 10. Caching Architecture

\\\
REQUEST CACHING FLOW

┌──────────────────────┐
│  Client Request      │
│ GET /api/reviews     │
└──────────┬───────────┘
           │
           ↓
    ┌──────────────────────────┐
    │ Browser Cache Layer      │
    │ (Client-side)            │
    └──────┬───────────────────┘
           │ Cache hit?
           ├─ Yes → Return cached
           │
           ├─ No ↓
    ┌──────────────────────────┐
    │ HTTP Response Cache      │
    │ (Cache-Control headers)  │
    └──────┬───────────────────┘
           │
           ├─ Cache-Control: max-age=3600
           │ (cached 1 hour)
           │
           ↓
    ┌──────────────────────────┐
    │ CDN Cache Layer          │
    │ (Azure Front Door)       │
    └──────┬───────────────────┘
           │ Cache hit?
           ├─ Yes → Return cached
           │
           ├─ No ↓
    ┌──────────────────────────┐
    │ Application Layer        │
    │ (EF Query Results)       │
    └──────┬───────────────────┘
           │ Query built
           │
           ├─ .AsNoTracking()
           ├─ .Include(related)
           ├─ .Select(projection)
           │
           ↓
    ┌──────────────────────────┐
    │ Database Query           │
    │ Optimized indexes used   │
    └──────┬───────────────────┘
           │
           ↓
    ┌──────────────────────────┐
    │ Return Results           │
    │ (JSON serialized)        │
    └──────────────────────────┘
\\\

## 11. Error Handling & Recovery Flow

\\\
ERROR HANDLING PIPELINE

┌──────────────────────────┐
│ Error Occurs             │
└──────────┬───────────────┘
           │
           ↓
┌──────────────────────────────────┐
│ Global Exception Middleware      │
│ Catches all exceptions           │
└──────────┬─────────────────────┘
           │
           ├─ Type of error?
           │
    ┌──────┴──────┬──────────┬──────────┐
    │             │          │          │
    ↓             ↓          ↓          ↓
[Auth]        [Validation] [Business] [System]
401           400         409        500
Unauthorized  Bad Request Conflict   Server Error
    │             │          │          │
    └─────────────┼──────────┼──────────┘
                  │          │
                  ↓          ↓
         ┌─────────────────────────────┐
         │ Log Error                   │
         │ - Serilog                   │
         │ - Application Insights      │
         └──────────┬──────────────────┘
                    │
                    ↓
         ┌─────────────────────────────┐
         │ Format Error Response       │
         │ {                           │
         │   "error": "message",       │
         │   "statusCode": 400,        │
         │   "timestamp": "ISO8601"    │
         │ }                           │
         └──────────┬──────────────────┘
                    │
                    ↓
         ┌─────────────────────────────┐
         │ Send to Client              │
         │ with appropriate HTTP code  │
         └─────────────────────────────┘

RECOVERY STRATEGIES

Circuit Breaker (External API calls)
┌─────────────────────────────────┐
│ Attempt external API call       │
└────────┬────────────────────────┘
         │
         ├─ Failed?
         │
    ┌────┴─────┐
    │           │
 [Yes]        [No]
    │           │
    ↓           ↓
Count      Reset counter
failures   Return success
    │
    ├─ Threshold met?
    │
 [Yes] ↓
┌────────────────────┐
│ Open Circuit       │
│ Reject requests    │
│ Wait before retry  │
└────────────────────┘
\\\

---

## Summary

This diagram set covers:
1. **System Architecture** - Three-tier cloud infrastructure
2. **Component Interaction** - How services communicate
3. **Database Schema** - Entity relationships
4. **Authentication** - JWT-based security flow
5. **Data Flow** - Review sync and payment flows
6. **Deployment** - CI/CD pipeline
7. **Integrations** - Third-party service connections
8. **Caching** - Multi-layer caching strategy
9. **Error Handling** - Recovery mechanisms
