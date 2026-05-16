# Repaxio - Application Specification Document

## Executive Summary

**Repaxio** (formerly ReviewHub) is a comprehensive **Multi-Platform Review Management SaaS** designed to help businesses manage, analyze, and respond to customer reviews across multiple platforms from a single unified dashboard. The platform includes AI-powered features, SMS campaign automation, competitor tracking, and advanced analytics.

**Current Status:** 🟢 **85% Complete** - Production-ready core functionality with platform integrations pending

---

## 1. Product Overview

### Purpose
Repaxio enables businesses to:
- 📊 Aggregate reviews from multiple platforms (Google, Yelp, Facebook, TripAdvisor, etc.)
- 🤖 Automate response suggestions using AI
- 📈 Track and analyze review sentiment and trends
- 📞 Engage customers via SMS campaigns
- 🏆 Monitor competitor performance
- 💰 Manage subscriptions and billing

### Target Users
- Small to medium-sized businesses (SMBs)
- Multi-location enterprises
- Franchise management companies
- Service providers (restaurants, salons, medical, automotive)

### Key Value Propositions
1. **Single Dashboard** - Manage all reviews from one place
2. **Time Saving** - Reduce manual review checking by 80%+
3. **Data-Driven** - Actionable insights from sentiment analysis
4. **Automation** - AI-powered responses and SMS campaigns
5. **Competitive Edge** - Track and compare against competitors

---

## 2. Core Features

### 2.1 Authentication & User Management

#### Registration & Login
- **Method:** Auth0 OAuth integration (password-less)
- **Multi-tenant:** Each user can manage multiple businesses
- **Role-based Access:** Admin, Manager, Team Member
- **Features:**
  - Email verification
  - Profile completion on signup
  - Session management
  - Logout across all sessions

#### Active Account Verification
- Email-based verification
- Account activation required before platform access
- Account suspension for inactive users (configurable)

### 2.2 Business Management

#### Multi-Business Support
- Create, read, update, delete (CRUD) businesses
- Soft delete with data retention
- Business details:
  - Name, industry type, location
  - Contact information
  - Website URL
  - Business hours
  - Logo/branding

#### Business Settings
- Team member assignment
- Notification preferences
- Integration settings
- Billing settings

### 2.3 Review Management

#### Unified Review Dashboard
- **Source:** Aggregate reviews from 10+ platforms
- **Display:** Chronological feed with filtering
- **Platforms Supported:**
  - Google Business Profile
  - Yelp
  - Facebook
  - TripAdvisor
  - Trustpilot
  - Amazon
  - Apple Maps
  - Zomato
  - Instagram
  - LinkedIn

#### Review Features
- **View:** Platform, rating (1-5 stars), text, reviewer info, date
- **Filter:** Platform, sentiment, rating, read/unread, flagged
- **Actions:**
  - Mark as read/unread
  - Flag as important
  - Reply to review (platform-specific)
  - Delete (if permission allows)
  - Archive

#### Review Metadata
- Sentiment score (-1 to 1, AI-calculated)
- Suggested response from AI
- Response time tracking
- Response count
- Last updated timestamp

### 2.4 Platform Integrations

#### OAuth Integration Framework
- **Flow:** OAuth 2.0 for each platform
- **Token Storage:** Secure encrypted storage
- **Token Refresh:** Automatic before expiration
- **Connection Management:**
  - Connect platform
  - View connection status
  - Disconnect and remove tokens
  - Sync data manually

#### Supported Integration Status
| Platform | Status | Features |
|----------|--------|----------|
| Google Business Profile | ✅ Live | Review sync, replies, ratings |
| Yelp | ✅ Live | Review sync, replies |
| Facebook | ✅ Live | Page reviews, replies |
| TripAdvisor | 🔄 In Progress | Review sync |
| Trustpilot | 🔄 In Progress | Review sync |
| Amazon | 📋 Planned | Review sync |
| Others | 📋 Planned | Expandable framework |

#### Automatic Review Syncing
- Background job fetches reviews every 4 hours
- Real-time notifications for new reviews
- Deduplication of same review from multiple sync
- Error handling and retry logic

### 2.5 Customer & Engagement Management

#### Customer Database
- **CRUD Operations:** Create, list, update, delete customers
- **Customer Data:**
  - Name, phone, email, address
  - Visit history (count, last visit date)
  - Customer notes/tags
  - Lifetime value
  - Preferred communication channel

#### SMS Campaigns
- **Features:**
  - Create campaign templates
  - Schedule bulk SMS sends
  - Segment customers (by visit date, tags, etc.)
  - Track delivery and read status
  - Campaign scheduling and automation

#### Campaign Status
- Draft → Scheduled → Sending → Sent/Failed
- Real-time progress tracking
- Detailed analytics per campaign

### 2.6 Analytics & Insights

#### Dashboard Overview
- Total reviews across all platforms
- Average rating
- Response rate (replies/total reviews)
- Recent reviews feed
- Quick action buttons

#### Analytics Reports
1. **Rating Trends** - Line chart over time
2. **Platform Breakdown** - Distribution across platforms
3. **Sentiment Analysis** - Positive/neutral/negative trends
4. **Top Keywords** - Most mentioned words/phrases
5. **Response Time** - Average time to respond
6. **Reviewer Demographics** - Location, frequency

#### Analytics Export
- CSV/PDF export of reports
- Custom date ranges
- Scheduled email reports (daily/weekly/monthly)

### 2.7 Competitor Tracking

#### Competitor Setup
- Add competitor businesses
- Track competitor:
  - Overall rating
  - Review count
  - Average response time
  - Sentiment score
  - Top keywords

#### Competitor Analysis
- Side-by-side rating comparison
- Performance trends vs competitors
- Industry average benchmarking
- Competitive positioning

### 2.8 Subscription & Billing

#### Subscription Plans
| Plan | Price | Reviews/mo | SMS/mo | Businesses | Features |
|------|-------|-----------|--------|------------|----------|
| Free | $0 | 100 | 10 | 1 | Basic analytics |
| Pro | $49 | Unlimited | 500 | 5 | Advanced analytics, SMS |
| Enterprise | $149 | Unlimited | Unlimited | Unlimited | Priority support, custom integrations |

#### Billing Features
- Stripe integration for payments
- Automatic renewal on monthly/annual basis
- Cancel anytime with prorated refunds
- Invoice generation and email
- Payment method management
- Usage tracking and alerts

#### Webhook Events
- `checkout.session.completed` - New subscription
- `customer.subscription.updated` - Plan change
- `charge.succeeded` - Successful payment
- `charge.failed` - Failed payment
- `customer.subscription.deleted` - Cancellation

### 2.9 Settings & Preferences

#### User Settings
- Profile information (name, email, phone)
- Password changes (via Auth0)
- Notification preferences
- Language/timezone settings
- Data export

#### Business Settings
- Business details
- Team members and roles
- Integration connections
- Billing and subscription
- API keys for developers
- Audit logs

### 2.10 Admin Panel

#### Admin Dashboard
- System-wide statistics
- Organization management
- User management
- Pricing plan configuration
- Billing overview
- Signup funnel monitoring
- Enterprise inquiry tracking

#### Admin Features
- Grant trial periods to organizations
- Override usage limits
- Assign/modify pricing plans
- Monitor signup progress
- Track enterprise leads
- Enterprise onboarding wizard

---

## 3. Technical Requirements

### 3.1 Backend Requirements

#### Framework
- **.NET 9** with ASP.NET Core Web API
- **Entity Framework Core 9** for ORM
- **Async/await** throughout for performance
- **Dependency Injection** for flexibility

#### Database
- **SQL Server** (LocalDB for dev, Azure SQL for production)
- **Database Schema:** 8 entities with relationships
- **Migrations:** EF Core migrations for versioning
- **Indexing:** Strategic indexes on frequently queried columns

#### Authentication
- **JWT Bearer tokens** with Auth0
- **Token validation** on every protected endpoint
- **Cross-tenant authorization** checks
- **Role-based access control (RBAC)**

#### API Design
- **RESTful** architecture
- **Swagger/OpenAPI** documentation
- **CORS** configuration for frontend
- **Error handling** with meaningful error codes
- **Rate limiting** to prevent abuse
- **Request/response** validation

#### External Services
- **Auth0** - Authentication and authorization
- **Stripe** - Payment processing
- **Twilio** - SMS sending
- **Mailgun** - Email sending (planned)
- **Third-party APIs** - Google, Yelp, Facebook, etc.

### 3.2 Frontend Requirements

#### Framework
- **React 18** with TypeScript
- **Vite** for build tooling (fast compilation)
- **React Router v6** for navigation
- **Axios** with interceptors for API calls

#### Styling
- **Tailwind CSS v4** for utility-first styling
- **shadcn/ui** for pre-built components
- **Lucide React** for icons
- **Responsive design** (mobile-first)
- **Dark mode** support

#### Key Libraries
- **@auth0/auth0-react** - Auth0 integration
- **Recharts** - Data visualization
- **React Query** - API data management (optional)
- **Zod/Yup** - Form validation

#### Pages (7 Total)
1. **Dashboard** - Overview, metrics, recent reviews
2. **Reviews** - Unified review feed with filters
3. **Integrations** - Platform connection management
4. **Analytics** - Charts, trends, insights
5. **POS Automation** - Customers, campaigns, templates
6. **Competitors** - Competitor tracking and comparison
7. **Settings** - Profile, billing, security

#### Performance
- Code splitting for faster initial load
- Image optimization
- CSS minification
- Bundle size < 800KB gzipped
- Lazy loading of routes

### 3.3 Database Schema

#### Entities

1. **User**
   - Id (PK), Auth0Id (unique), Email (unique)
   - FirstName, LastName, PhoneNumber
   - SubscriptionTier, ActiveBusinessCount
   - CreatedAt, UpdatedAt, IsActive

2. **Business**
   - Id (PK), UserId (FK), Name, Industry
   - Website, PhoneNumber, Address, City, State, ZipCode
   - LogoUrl, CoverImageUrl
   - IsActive (soft delete), CreatedAt, UpdatedAt

3. **PlatformConnection**
   - Id (PK), BusinessId (FK), Platform (Google, Yelp, etc.)
   - AccessToken (encrypted), RefreshToken (encrypted)
   - TokenExpiresAt, SyncedAt
   - IsConnected, CreatedAt, UpdatedAt

4. **Review**
   - Id (PK), BusinessId (FK), Platform, ExternalReviewId (unique)
   - ReviewerName, ReviewerEmail, ReviewText
   - Rating (1-5), SentimentScore (-1 to 1), ReviewDate
   - IsRead, IsFlagged, AIResponse
   - CreatedAt, UpdatedAt

5. **Response**
   - Id (PK), ReviewId (FK), ResponseText
   - RespondedAt, CreatedAt, UpdatedAt

6. **Customer**
   - Id (PK), BusinessId (FK), Name, PhoneNumber, Email
   - Address, City, State, ZipCode
   - LastVisitDate, TotalVisits, LifetimeValue
   - Notes, Tags, CreatedAt, UpdatedAt

7. **Campaign**
   - Id (PK), BusinessId (FK), Name, Template, Status (Draft/Scheduled/Sending/Sent/Failed)
   - ScheduledAt, SentAt, MessageCount
   - SuccessCount, FailureCount, CreatedAt

8. **SmsMessage**
   - Id (PK), CampaignId (FK), CustomerId (FK)
   - MessageText, Status (Sent/Failed/Pending)
   - TwilioMessageSid, SentAt, FailureReason

9. **Competitor**
   - Id (PK), BusinessId (FK), CompetitorName
   - Website, Platform, Rating, ReviewCount
   - SentimentScore, LastSyncedAt, CreatedAt

10. **Subscription**
    - Id (PK), UserId (FK), StripeCustomerId, StripeSubscriptionId
    - Plan (Free/Pro/Enterprise), Status
    - CurrentPeriodStart, CurrentPeriodEnd
    - CanceledAt, CreatedAt, UpdatedAt

---

## 4. Feature Specifications by Priority

### Priority 1: Core Platform (COMPLETE ✅)
- User authentication and management
- Business CRUD operations
- Review aggregation from platforms
- Unified review dashboard with filters
- Basic analytics
- Settings management

### Priority 2: Engagement (COMPLETE ✅)
- Customer database
- SMS campaigns
- Campaign scheduling and tracking
- SMS usage tracking

### Priority 3: Advanced Analytics (COMPLETE ✅)
- Sentiment analysis
- Rating trends
- Platform performance breakdown
- Keyword extraction
- Competitor tracking

### Priority 4: Billing & Monetization (COMPLETE ✅)
- Stripe integration
- Subscription management
- Plan enforcement
- Usage-based limits

### Priority 5: AI Features (IN PROGRESS 🔄)
- AI-powered response suggestions
- Automated sentiment analysis
- Review categorization

### Priority 6: Platform Integrations (IN PROGRESS 🔄)
- Real OAuth implementations
- Automatic review syncing
- Real-time notifications

### Priority 7: Production Ready (PENDING ⏳)
- Comprehensive testing (unit, integration, E2E)
- Performance optimization
- Security audit
- Deployment automation

---

## 5. Data Flow & User Workflows

### 5.1 User Signup Flow
```
1. User visits app
2. Click "Sign Up"
3. Redirect to Auth0 signup
4. Auth0 verification email
5. Create user in database
6. Prompt profile completion
7. Create first business
8. Choose subscription plan
9. Redirect to dashboard
```

### 5.2 Review Management Workflow
```
1. User connects platform (Google, Yelp, etc.)
2. OAuth flow redirects to platform
3. Grant permission
4. Store encrypted access token
5. Automatic background sync every 4 hours
6. New reviews appear in dashboard
7. User filters/searches reviews
8. User selects review to read details
9. User replies (platform-specific)
10. System tracks response time
```

### 5.3 SMS Campaign Workflow
```
1. User creates campaign from template
2. Select customers (by tag, visit date, etc.)
3. Schedule sending time
4. System queues messages in Twilio
5. Twilio sends SMS in batches
6. Track delivery status
7. Show analytics dashboard
8. Generate report
```

### 5.4 Analytics Workflow
```
1. User navigates to Analytics
2. Select date range
3. System aggregates review data
4. Calculate sentiment, trends
5. Display charts (rating, platform, sentiment)
6. Show top keywords
7. Compare vs competitors
8. Option to export/email report
```

---

## 6. Integration Specifications

### 6.1 Auth0 Integration
- **Purpose:** User authentication and identity management
- **OAuth Method:** OpenID Connect
- **Scopes:** `openid profile email`
- **Token Type:** JWT Bearer
- **Token Lifetime:** Typically 24 hours
- **Refresh:** Optional refresh tokens for longer sessions
- **User Sync:** Create/update user in database on first login

### 6.2 Stripe Integration
- **Purpose:** Payment processing and subscription management
- **Webhooks:** Subscribe to `checkout.session.completed`, `customer.subscription.updated`, `charge.failed`
- **Currency:** USD (with multi-currency support planned)
- **Retry Logic:** Automatic retry on payment failure
- **Invoice:** Auto-generate and email to customer
- **Usage:** Track SMS usage against subscription limits

### 6.3 Twilio Integration
- **Purpose:** SMS sending for campaigns
- **Methods:** Single SMS, bulk SMS
- **Rate Limiting:** Per subscription tier
- **Delivery Tracking:** Update database with delivery status
- **Error Handling:** Retry failed messages
- **Webhook:** Delivery status updates

### 6.4 Platform APIs (Google, Yelp, Facebook, etc.)
- **OAuth 2.0:** Each platform implements standard OAuth
- **Token Refresh:** Refresh before expiration
- **Scope:** Request minimal scopes needed
- **Rate Limiting:** Respect platform rate limits
- **Data Sync:** Fetch reviews every 4 hours
- **Error Handling:** Graceful degradation if sync fails

### 6.5 Mailgun Integration (Planned)
- **Purpose:** Transactional and marketing emails
- **Use Cases:** Password resets, review notifications, weekly reports
- **Templates:** HTML email templates
- **Delivery Tracking:** Track opens and clicks

---

## 7. Security & Compliance

### 7.1 Data Security
- ✅ Encrypt OAuth tokens at rest
- ✅ HTTPS for all communication
- ✅ JWT token validation on every API call
- ✅ Cross-tenant authorization checks
- ✅ SQL injection prevention (EF Core parameterized queries)
- ✅ XSS protection (React auto-escaping)
- ✅ CSRF protection via secure cookies

### 7.2 Authentication & Authorization
- ✅ Password-less authentication via Auth0
- ✅ Multi-factor authentication (available in Auth0)
- ✅ Session management with token expiration
- ✅ Role-based access control (RBAC)
- ✅ Audit logging of sensitive actions

### 7.3 Compliance
- 🟡 GDPR compliance (data retention, deletion, export)
- 🟡 CCPA compliance (California privacy)
- 🟡 SOC 2 certification (planned)
- 🟡 HIPAA compliance (if handling health data)
- ✅ PCI DSS compliance (via Stripe, not handling cards directly)

### 7.4 Data Backup & Recovery
- ⏳ Daily automated database backups
- ⏳ Point-in-time recovery capability
- ⏳ Disaster recovery plan
- ⏳ 30-day backup retention

---

## 8. Performance & Scalability

### 8.1 Performance Targets
- **API Response Times:**
  - Review list (100 items): < 200ms
  - Dashboard summary: < 500ms
  - Analytics queries: < 1s
  - SMS send: < 3s (Twilio latency)

- **Frontend Performance:**
  - Initial load: < 3s on 3G
  - Page navigation: < 200ms
  - Bundle size: < 800KB gzipped

### 8.2 Scalability Strategy
- ✅ Async/await throughout backend
- ✅ Database connection pooling
- ✅ Pagination for large datasets
- ✅ Strategic database indexing
- ✅ Static asset CDN caching
- ⏳ Horizontal scaling via Azure App Service
- ⏳ Read replicas for analytics queries
- ⏳ Caching layer (Redis) for frequently accessed data

### 8.3 Concurrency
- Database: Optimistic concurrency control
- API: Rate limiting per user/IP
- WebSocket: Connection pooling (if real-time features added)

---

## 9. Error Handling & Logging

### 9.1 Error Responses
- **Format:** JSON with error code and message
- **HTTP Status Codes:** 400, 401, 403, 404, 500, etc.
- **User-Friendly Messages:** Non-technical language
- **Logging:** All errors logged to Application Insights

### 9.2 Logging Strategy
- ✅ Application Insights integration (production)
- ✅ Structured logging with correlation IDs
- ✅ Log levels: Critical, Error, Warning, Information, Verbose
- ✅ Sensitive data masking (passwords, tokens)
- ✅ User action audit trail

---

## 10. Deployment & Environment

### 10.1 Development
- LocalDB for database
- Visual Studio or VS Code
- Mock Auth0 credentials
- Demo mode for frontend testing

### 10.2 Staging
- Azure SQL Database
- Azure App Service (staging slots)
- Real Auth0 credentials
- Real Stripe test keys

### 10.3 Production
- Azure SQL Database (read replicas)
- Azure App Service (multiple instances, auto-scale)
- Azure CDN for static assets
- Azure Key Vault for secrets
- Application Insights for monitoring
- Azure DevOps for CI/CD

---

## 11. Testing Strategy

### 11.1 Unit Tests
- Service layer logic
- Entity validation
- Helper/utility functions
- Target: 70%+ code coverage

### 11.2 Integration Tests
- API endpoint testing
- Database operations
- External service mocking
- Target: All critical endpoints

### 11.3 E2E Tests
- Complete user workflows (signup → create business → add reviews)
- Using Playwright or Cypress
- Cross-browser testing

### 11.4 Performance Tests
- Load testing (1000+ concurrent users)
- Stress testing (scale limits)
- Database query optimization

---

## 12. Success Metrics

### Business Metrics
- User acquisition rate
- Monthly active users (MAU)
- Churn rate
- Monthly recurring revenue (MRR)
- Customer lifetime value (LTV)

### Product Metrics
- Platform connections per user
- Average review response rate
- SMS campaign open rate
- Feature adoption rate

### Technical Metrics
- API uptime: 99.9%+
- Page load time: < 3s
- Error rate: < 0.1%
- Database query time: < 200ms

---

## 13. Roadmap

### Phase 1 (Current - 85% Complete)
- ✅ Core platform complete
- ✅ Basic integrations
- ✅ Analytics dashboard
- ✅ Subscription management

### Phase 2 (Next 4 Weeks)
- 🔄 Real platform OAuth implementations
- 🔄 AI-powered response suggestions
- 🔄 Automated review syncing
- 🔄 Email service integration

### Phase 3 (8-12 Weeks)
- 📋 Mobile app (React Native)
- 📋 Advanced AI features (categorization, auto-tagging)
- 📋 Zapier/IFTTT integrations
- 📋 White-label solution

### Phase 4 (12+ Weeks)
- 📋 Marketplace for third-party apps
- 📋 API for partners
- 📋 Advanced reporting/BI integration
- 📋 Custom integrations for enterprises

---

## Appendix: Glossary

- **SaaS:** Software as a Service - cloud-based software
- **OAuth:** Open Authorization - secure third-party authentication
- **JWT:** JSON Web Token - stateless authentication token
- **CORS:** Cross-Origin Resource Sharing - browser security feature
- **EF Core:** Entity Framework Core - .NET ORM
- **Auth0:** Third-party authentication service
- **Stripe:** Payment processing platform
- **Twilio:** SMS and communication platform
- **Mailgun:** Email delivery platform
- **GDPR:** General Data Protection Regulation - EU privacy law
- **CCPA:** California Consumer Privacy Act - California privacy law
- **RBAC:** Role-Based Access Control - permission system based on roles
- **CDN:** Content Delivery Network - distributed server network
- **CI/CD:** Continuous Integration/Continuous Deployment - automated deployment

---

**Document Version:** 1.0  
**Last Updated:** 2026-05-16  
**Status:** Final

---
