# AI Home Profile — MVP Technical Specification
## Phase 1: Home Profile, Maintenance Calendar & Seasonal Intelligence

| Field | Value |
|---|---|
| Version | 1.0 — MVP |
| Platform | Android 8.0+ (API 26+) |
| Stack | Kotlin + Jetpack Compose + Anthropic API |
| Scope | Home Profile + Calendar + Seasonal Nudges + Task Log |

---

## 1. Overview

AI Home Profile is an Android application that helps homeowners stay on top of maintenance by building a personalised profile of their home and delivering timely, season-aware task reminders. Unlike generic checklist apps, every task and nudge is generated from the specific characteristics of the user's property — its age, construction type, heating system, roof, and more.

Phase 1 covers: home profile setup flow, AI-generated maintenance calendar, seasonal push notifications, and a completed task log. Tradesperson connection and photo-based issue diagnosis are deferred to Phase 2.

---

## 2. MVP Goals

### 2.1 User Goals
- Describe their home once in a guided setup flow
- Receive a personalised maintenance calendar generated specifically for their property
- Get timely seasonal nudges that feel relevant — not generic reminders
- Log completed tasks and build a running record of their home's maintenance history

### 2.2 Business Goals
- Demonstrate clear AI value from first use — the calendar must feel meaningfully personalised
- Establish a habit loop: seasonal nudge → user opens app → completes task → logs it
- Capture rich home profile data that supports tradesperson lead-gen in Phase 2
- Build a completed task log that becomes a sticky, proprietary asset users don't want to lose

---

## 3. Core Concepts

### 3.1 The Home Profile
The home profile is the foundation of the entire product. It is collected once during onboarding and can be edited at any time. The richer the profile, the more accurate and useful the generated calendar becomes. The AI uses this data to determine which maintenance tasks apply, how urgently, and at what time of year.

> **Key Principle:** The home profile is what separates this app from a generic checklist. A 1960s solid-wall terraced house with a gas combi boiler has almost nothing in common, maintenance-wise, with a 2010 new-build detached with underfloor heating. The profile makes this distinction real.

### 3.2 AI Calendar Generation
When the user completes their home profile, a single AI API call generates a full 12-month maintenance calendar tailored to their property. This call uses the home profile as context and returns a structured list of tasks with timing, urgency, estimated effort, and a plain-language explanation of why each task matters for their specific home.

The calendar is stored locally and does not need to be regenerated unless the user significantly updates their home profile.

> **Regeneration Trigger:** Calendar is regenerated only when the user edits a core profile field (e.g. changes heating system, adds a conservatory). Minor edits (e.g. updates contact details) do not trigger regeneration.

### 3.3 Seasonal Intelligence
The seasonal nudge system is a background agent that runs weekly, compares the current date against the task calendar, and determines whether any tasks are approaching their optimal window. Nudges are batched — a maximum of one notification per week — to avoid fatigue.

Nudge timing is guided by a seasonal window system: each task has an ideal month range and an urgency escalation schedule (reminder at window open, follow-up if incomplete after 3 weeks, final nudge at window close).

---

## 4. Home Profile — Data Fields

### 4.1 Property Basics

| Field | Options |
|---|---|
| Property type | Detached \| Semi-detached \| Terraced \| Flat / Apartment \| Bungalow |
| Ownership | Own (freehold) \| Own (leasehold) \| Renting |
| Year built (approx.) | Pre-1919 \| 1919–1944 \| 1945–1979 \| 1980–1999 \| 2000–2015 \| 2016+ |
| Number of bedrooms | 1 \| 2 \| 3 \| 4 \| 5+ |
| Number of floors | 1 \| 2 \| 3+ |
| Location / postcode | Used for climate zone and frost risk — not stored as PII |

### 4.2 Construction & Structure

| Field | Options |
|---|---|
| Wall type | Cavity wall \| Solid wall \| Timber frame \| Unknown |
| Wall insulation | Yes \| No \| Unknown |
| Roof type | Pitched (tiles) \| Pitched (slate) \| Flat \| Mixed |
| Loft insulation | Yes \| No \| Unknown |
| Double glazing | Full \| Partial \| None |
| Basement / cellar | Yes \| No |
| Conservatory / extension | Yes \| No |

### 4.3 Heating & Hot Water

| Field | Options |
|---|---|
| Heating type | Gas central heating \| Oil boiler \| Heat pump \| Electric storage heaters \| Underfloor \| None |
| Boiler type (if applicable) | Combi \| System \| Regular (conventional) |
| Boiler age (approx.) | < 5 years \| 5–10 years \| 10–15 years \| 15+ years \| Unknown |
| Last boiler service | This year \| 1–2 years ago \| 3+ years ago \| Never \| Unknown |
| Hot water tank | Yes \| No |
| Radiators | Yes \| No |
| Underfloor heating zones | None \| Ground floor \| Whole house |

### 4.4 Outdoor & Garden

| Field | Options |
|---|---|
| Garden type | Front only \| Rear only \| Both \| None |
| Driveway / parking | Yes \| No |
| Gutters & downpipes | Plastic \| Cast iron \| Unknown |
| External woodwork (fascias, soffits) | Wood \| UPVC \| Unknown |
| Boundary type | Fence \| Wall \| Hedge \| Mixed \| None |
| Flat roof sections (garage, bay) | Yes \| No |

### 4.5 Systems & Appliances

| Field | Options |
|---|---|
| Smoke / CO alarms | Yes \| No \| Unsure |
| Mains water stop valve location known | Yes \| No |
| Septic tank | Yes \| No |
| Solar panels | Yes \| No |
| Electric vehicle charger | Yes \| No |
| Home security system | Yes \| No |

---

## 5. Screens & User Flow

### 5.1 Onboarding Flow (First Launch)

| Screen | Purpose | Key Actions |
|---|---|---|
| O1 — Welcome | Value proposition, property focus | Tap 'Set Up My Home' |
| O2 — Property Basics | Core property info | Select options, tap Continue |
| O3 — Construction | Wall, roof, insulation | Select options, tap Continue |
| O4 — Heating & Hot Water | Heating system details | Select options, tap Continue |
| O5 — Outdoor & Garden | External features | Select options, tap Continue |
| O6 — Systems & Appliances | Safety and utility systems | Select options, tap Continue |
| O7 — Generating Calendar | AI processing screen | Auto-advances when ready |
| O8 — Your Home is Ready | Summary + first peek at calendar | Tap 'See My Calendar' |

**O1 — Welcome Screen**
- Headline: 'Your home, looked after'
- Three benefit bullets: personalised to your property / timely seasonal reminders / a full maintenance history
- Single CTA: 'Set Up My Home' — no sign-up required
- Estimated setup time shown: 'Takes about 4 minutes'

**O2 to O6 — Profile Screens**
- Each screen covers one section of the home profile
- Progress indicator at top (e.g. Step 2 of 6)
- All inputs are tap-to-select — no typing required
- 'Skip this section' available on every screen
- Back navigation preserves selections

**O7 — Generating Calendar Screen**
- Animated house illustration with progress indicator
- Rotating context messages: 'Checking your roof type...', 'Planning your heating schedule...', 'Preparing your winter checklist...'
- Target latency: under 8 seconds for calendar generation
- On failure: friendly retry screen — profile data is preserved

**O8 — Home is Ready Screen**
- Show count of tasks generated (e.g. 'We found 24 maintenance tasks for your home')
- Preview the next 3 upcoming tasks as a teaser
- CTA: 'See My Calendar'

### 5.2 Main App Flow

| Screen | Purpose | Key Actions |
|---|---|---|
| M1 — Home Dashboard | Overview of upcoming tasks and recent activity | Navigate to calendar, task log, home profile |
| M2 — Maintenance Calendar | Full 12-month task list, filterable by season | View task detail, mark complete, snooze |
| M3 — Task Detail | Full info on a single task | Mark complete, snooze, view explanation |
| M4 — Task Log | History of all completed tasks | Browse, filter by year or category |
| M5 — Home Profile | View and edit home details | Edit any field, re-generate calendar if needed |

**M1 — Home Dashboard**
- 'Upcoming this month' section — top 3 tasks due soon
- 'Coming up next season' teaser — 1–2 tasks on the horizon
- 'Recently completed' strip — last 3 logged tasks
- Quick-access buttons: Calendar / Task Log / Home Profile
- Seasonal banner — changes each quarter with a relevant illustration and headline

**M2 — Maintenance Calendar**
- Default view: current month highlighted, scrollable through all 12 months
- Each task shown as a card: task name, category icon, urgency chip, estimated time
- Filter bar: All | Spring | Summer | Autumn | Winter | Overdue
- Completed tasks shown with strikethrough and green tick — remain visible for reference
- Overdue tasks (window has passed, not completed) shown in amber

**M3 — Task Detail**
- Task name and category
- Why this matters for your home — 2–3 sentence AI-generated explanation referencing their specific property details
- Optimal timing window — e.g. 'Best done October–November, before first frosts'
- Estimated effort — e.g. 'DIY: 1–2 hours' or 'Hire out: half day'
- Difficulty level — DIY-friendly | Requires basic skills | Professional recommended
- Mark as Complete button — triggers log entry and confetti micro-animation
- Snooze options — 1 week / 2 weeks / 'Remind me next season'

**M4 — Task Log**
- Chronological list of all completed tasks, most recent first
- Each entry shows: task name, date completed, category
- Filter by year and by category
- Summary stats at top: total tasks completed, current year count
- Export option (Phase 2)

**M5 — Home Profile**
- View-only by default, with Edit button per section
- Editing a core field shows a warning: 'This may update your maintenance calendar'
- On save of a core field change: prompt to regenerate calendar

---

## 6. AI Calendar Generation

### 6.1 Generation Approach
Calendar generation is a single structured API call made on completion of the home profile setup. The response is a JSON array of maintenance tasks stored locally and rendered into the calendar view.

- **Model:** `claude-sonnet-4-20250514` via Anthropic API
- **Frequency:** Once at setup; only regenerated on significant profile changes

### 6.2 Prompt Structure
- **System prompt:** "You are a home maintenance expert. Given a home profile, generate a personalised annual maintenance calendar. Return only valid JSON matching the task schema. Be specific to the property — do not generate tasks that don't apply to this home type."
- **Home profile block:** Full structured home profile as key-value pairs
- **Location context:** Climate zone derived from postcode (e.g. 'Northern UK — cold winters, moderate summers, high rainfall')
- **Task schema definition:** Provided in the prompt to enforce structured output

### 6.3 Task Schema

Each generated task conforms to the following schema, which maps directly to the local Task table:

| Field | Description |
|---|---|
| task_key | Unique string identifier e.g. 'boiler_service_annual' |
| title | Short task name e.g. 'Annual boiler service' |
| category | Enum: heating \| roofing \| plumbing \| garden \| safety \| structure \| electrical \| general |
| why_this_home | 2–3 sentence explanation personalised to the user's property |
| optimal_month_start | Integer 1–12 — start of ideal window |
| optimal_month_end | Integer 1–12 — end of ideal window |
| urgency | Enum: low \| medium \| high \| critical |
| effort_diy_hours | Estimated DIY hours (null if not DIY-appropriate) |
| difficulty | Enum: diy_easy \| diy_moderate \| professional_recommended |
| recurrence | Enum: annual \| biannual \| quarterly \| one_off \| as_needed |
| applies_because | Short tag e.g. 'gas_boiler' or 'pre_1980_construction' — for audit |

### 6.4 Example Generated Tasks

For a 1960s semi-detached with a gas combi boiler and cast iron gutters:

| Task | Window | Why This Home |
|---|---|---|
| Annual boiler service | Sep–Oct | Combi boiler is 10–15 years old — higher failure rates in winter. Servicing now avoids emergency call-out charges. |
| Bleed radiators | Oct | With a combi boiler and older radiators, air locks are common. Bleeding before heating season improves efficiency. |
| Clear and inspect cast iron gutters | Oct–Nov | Cast iron gutters crack when debris causes standing water to freeze. Must clear before first frosts. |
| Check loft insulation depth | Sep | Pre-1980 homes often have degraded loft insulation. Top up to 270mm to meet current standards. |
| Repoint external brickwork (inspect) | Apr–May | Solid wall construction is vulnerable to water ingress through failed mortar. Inspect annually. |
| Test smoke and CO alarms | Jan + Jul | CO risk is elevated in homes with gas appliances. Test every 6 months. |

---

## 7. Seasonal Nudge System

### 7.1 How It Works
A WorkManager background job runs weekly on Wi-Fi. It evaluates the task calendar against the current date and determines whether any tasks are entering their optimal window, mid-window and incomplete, or at window close.

| Trigger Condition | Notification Type | Example |
|---|---|---|
| Task window opens (within 2 weeks) | Heads up nudge | 'Autumn prep starting: 3 tasks to get ready for winter' |
| Task incomplete after 3 weeks in window | Gentle follow-up | 'Your boiler service window is closing — worth booking this week' |
| Task window closes in 7 days, still incomplete | Urgency nudge | 'Last chance: cast iron gutter clearing before first frosts' |
| Critical task overdue | Urgent alert | 'Your smoke alarms haven't been tested in 6 months — takes 2 minutes' |

### 7.2 Notification Limits
- Maximum 1 notification per week — multiple triggers are batched into a single summary
- Quiet hours respected — notifications delivered 9am–8pm local time only
- User can set notification frequency preference: Weekly digest | Individual alerts | Muted
- Critical safety tasks (smoke alarms, CO detectors, gas safety) bypass the weekly limit

### 7.3 Notification Content
Notification copy is pre-written per trigger condition — it does not call the AI at notification time. This keeps the nudge system fast, offline-capable, and free of API cost per notification.

---

## 8. Completed Task Log

The task log is the app's stickiest asset — a growing record of a home's maintenance history that becomes more valuable the longer a user stays.

- Every task marked complete creates a log entry with timestamp and task details
- User can add optional free-text notes to any log entry (e.g. 'Used local plumber, cost £85')
- Log entries are permanent — completing then uncompleting a task keeps the log entry with an 'amended' flag
- Yearly summary available: 'In 2025 you completed 18 maintenance tasks on your home'

> **Future Value:** The task log is the foundation for Phase 2 export features — a PDF maintenance history report for property sale, remortgage, or insurance purposes.

---

## 9. Local Data Model

All Phase 1 data is stored locally using Room. No user account or backend required.

### 9.1 HomeProfile

| Column | Type / Notes |
|---|---|
| id | INTEGER PRIMARY KEY (single row per app install) |
| property_type | TEXT — enum key |
| year_built_range | TEXT — enum key |
| wall_type | TEXT — enum key |
| roof_type | TEXT — enum key |
| heating_type | TEXT — enum key |
| boiler_type | TEXT NULLABLE |
| boiler_age_range | TEXT NULLABLE |
| last_boiler_service | TEXT NULLABLE |
| has_garden | TEXT — front \| rear \| both \| none |
| gutter_type | TEXT NULLABLE |
| has_smoke_alarms | BOOLEAN NULLABLE |
| has_solar | BOOLEAN DEFAULT false |
| location_climate_zone | TEXT — derived from postcode, not raw postcode |
| profile_complete | BOOLEAN DEFAULT false |
| created_at | INTEGER — Unix timestamp |
| updated_at | INTEGER — Unix timestamp |

### 9.2 MaintenanceTask

| Column | Type / Notes |
|---|---|
| id | INTEGER PRIMARY KEY AUTOINCREMENT |
| task_key | TEXT UNIQUE — from AI generation |
| title | TEXT NOT NULL |
| category | TEXT — enum key |
| why_this_home | TEXT — AI-generated explanation |
| optimal_month_start | INTEGER 1–12 |
| optimal_month_end | INTEGER 1–12 |
| urgency | TEXT — low \| medium \| high \| critical |
| effort_diy_hours | REAL NULLABLE |
| difficulty | TEXT — enum key |
| recurrence | TEXT — enum key |
| applies_because | TEXT — audit tag |
| is_completed_this_cycle | BOOLEAN DEFAULT false |
| snoozed_until | INTEGER NULLABLE — Unix timestamp |
| generated_at | INTEGER — Unix timestamp |

### 9.3 TaskLogEntry

| Column | Type / Notes |
|---|---|
| id | INTEGER PRIMARY KEY AUTOINCREMENT |
| task_id | INTEGER — FK to MaintenanceTask |
| task_title | TEXT — snapshot in case task is later removed |
| task_category | TEXT — snapshot |
| completed_at | INTEGER — Unix timestamp |
| notes | TEXT NULLABLE — user free-text |
| amended | BOOLEAN DEFAULT false |

### 9.4 NotificationLog

| Column | Type / Notes |
|---|---|
| id | INTEGER PRIMARY KEY AUTOINCREMENT |
| notification_type | TEXT — heads_up \| follow_up \| urgency \| critical |
| task_ids_included | TEXT — JSON array of task IDs in this batch |
| sent_at | INTEGER — Unix timestamp |

---

## 10. Technology Stack

| Component | Technology | Rationale |
|---|---|---|
| Language | Kotlin | Modern Android standard |
| UI Framework | Jetpack Compose | Declarative UI, smooth animations for calendar view |
| Local DB | Room (SQLite) | Offline-first, structured task storage |
| Background Agent | WorkManager | Reliable background scheduling, battery-aware |
| Notifications | NotificationManager + WorkManager | Reliable delivery, respects battery optimisation |
| AI Calendar Generation | Anthropic API (Claude Sonnet) | Structured JSON output, strong reasoning for home-specific tasks |
| Networking | Retrofit + OkHttp | Type-safe API calls |
| DI | Hilt | Jetpack-native dependency injection |
| Preferences | DataStore | Async key-value store for settings and flags |
| Architecture | MVVM + Repository | Clean separation, testable |

---

## 11. Risks & Mitigations

| Risk | Impact | Mitigation |
|---|---|---|
| AI generates irrelevant tasks | Damages trust immediately | Strict system prompt; applies_because tag enables QA audit |
| User skips too many profile fields | Reduced personalisation | Show task count preview updating live as sections are filled in |
| Notification fatigue causes opt-out | Core loop broken | Hard cap of 1 notification/week; user controls frequency |
| Calendar regeneration confusion | User loses customised data | Warn before regenerating; preserve completed task log regardless |
| WorkManager killed on aggressive battery-saver devices | Seasonal nudges stop firing | Detect and guide user to whitelist the app in battery settings |
| API cost at scale | Margin pressure | Calendar generated once per user; average prompt ~1500 tokens |

---

## 12. Out of Scope (Phase 1)

- Photo-based issue diagnosis
- Tradesperson connection or lead generation
- Cost estimator per task
- Cloud backup or user accounts
- Task log PDF export
- Shared home profile
- Multiple property support
- In-app purchases or subscription paywall
- Apple iOS version

---

## 13. Success Metrics

| Metric | Target |
|---|---|
| Profile setup completion rate | >= 75% of users who open the app |
| Avg profile fields completed | >= 70% of available fields |
| Calendar tasks generated per user (avg) | >= 18 tasks |
| 7-day retention | >= 40% |
| Notification opt-in rate | >= 65% |
| Tasks marked complete in first 30 days | >= 2 per active user |
| Positive feedback on task relevance | >= 80% thumbs up on Task Detail screen |
| Calendar generation latency (p95) | < 8 seconds |

---

## 14. Delivery Milestones

| Milestone | Deliverable | Est. Duration | Status |
|---|---|---|---|
| M1 — Setup | Project scaffold, Room schema, Hilt, nav graph, DataStore | 2 days | ⬜ Not started |
| M2 — Home Profile Flow | O1–O6 screens in Compose, all selector components, profile persistence | 4 days | ⬜ Not started |
| M3 — AI Calendar Generation | Anthropic API integration, prompt engineering, JSON parsing, task storage | 4 days | ⬜ Not started |
| M4 — Calendar UI | O7–O8 screens, M2 calendar view, filter bar, month grouping | 4 days | ⬜ Not started |
| M5 — Task Detail & Completion | M3 task detail screen, mark complete flow, snooze logic, micro-animation | 3 days | ⬜ Not started |
| M6 — Task Log | M4 log screen, log entry creation, year filter, summary stats | 2 days | ⬜ Not started |
| M7 — Seasonal Nudge System | WorkManager job, trigger logic, notification batching, quiet hours | 3 days | ⬜ Not started |
| M8 — Dashboard & Profile Edit | M1 dashboard, M5 profile edit with regeneration warning | 2 days | ⬜ Not started |
| M9 — Polish & Edge Cases | Empty states, error handling, battery whitelist prompt | 3 days | ⬜ Not started |
| M10 — Internal Release | APK via Firebase App Distribution | 1 day | ⬜ Not started |

**Total estimated effort:** 28 engineering days (~5.5 weeks for a single developer)

---

*End of Phase 1 MVP Specification — v1.0*
