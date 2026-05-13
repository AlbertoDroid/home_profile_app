# AI Home Profile | MVP Technical Spec | Phase 1

## AI HOME PROFILE

**Android App — MVP Technical Specification**

*Phase 1: Home Profile, Maintenance Calendar & Seasonal Intelligence*

| | |
|---|---|
| **Version** | 1.0 — MVP |
| **Platform** | Android 8.0+ (API 26+) |
| **Stack** | Kotlin + Jetpack Compose + Anthropic API |
| **Scope** | Home Profile + Calendar + Seasonal Nudges + Task Log |
| **Market** | United States |

*April 2026*

---

## 1. Overview

AI Home Profile is an Android application that helps homeowners stay on top of maintenance by building a personalized profile of their home and delivering timely, season-aware task reminders. Unlike generic checklist apps, every task and nudge is generated from the specific characteristics of the user's property — its age, construction type, heating system, roof, and more.

This document covers Phase 1: the home profile setup flow, AI-generated maintenance calendar, seasonal push notifications, and a completed task log. Tradesperson connection and photo-based issue diagnosis are deferred to Phase 2.

---

## 2. MVP Goals

### 2.1 User Goals

- Describe their home once in a guided setup flow
- Receive a personalized maintenance calendar generated specifically for their property
- Get timely seasonal nudges that feel relevant — not generic reminders
- Log completed tasks and build a running record of their home's maintenance history

### 2.2 Business Goals

- Demonstrate clear AI value from first use — the calendar must feel meaningfully personalized
- Establish a habit loop: seasonal nudge → user opens app → completes task → logs it
- Capture rich home profile data that supports tradesperson lead-gen in Phase 2
- Build a completed task log that becomes a sticky, proprietary asset users don't want to lose

---

## 3. Core Concepts

### 3.1 The Home Profile

The home profile is the foundation of the entire product. It is collected once during onboarding and can be edited at any time. The richer the profile, the more accurate and useful the generated calendar becomes. The AI uses this data to determine which maintenance tasks apply, how urgently, and at what time of year.

> **Key Principle:** The home profile is what separates this app from a generic checklist. A 1960s brick ranch house with a gas furnace and aluminum gutters has almost nothing in common, maintenance-wise, with a 2010 new-build with a heat pump and vinyl siding. The profile makes this distinction real.

### 3.2 AI Calendar Generation

When the user completes their home profile, a single AI API call generates a full 12-month maintenance calendar tailored to their property. This call uses the home profile as context and returns a structured list of tasks with timing, urgency, estimated effort, and a plain-language explanation of why each task matters for their specific home.

The calendar is stored locally and does not need to be regenerated unless the user significantly updates their home profile. This keeps ongoing API costs low.

> **Regeneration Trigger:** Calendar is regenerated only when the user edits a core profile field (e.g. changes heating system, adds a deck). Minor edits (e.g. updates contact details) do not trigger regeneration.

### 3.3 Seasonal Intelligence

The seasonal nudge system is a background agent that runs weekly, compares the current date against the task calendar, and determines whether any tasks are approaching their optimal window. Nudges are batched — a maximum of one notification per week — to avoid fatigue.

Nudge timing is guided by a seasonal window system: each task has an ideal month range and an urgency escalation schedule (reminder at window open, follow-up if incomplete after 3 weeks, final nudge at window close).

---

## 4. Home Profile — Data Fields

The home profile is collected across the onboarding flow in digestible sections. All fields use simple selectors — no free text required except for optional notes.

### 4.1 Property Basics

| Field | Options / Format |
|---|---|
| Property type | Single-family detached \| Townhouse \| Condo / Apartment \| Duplex \| Mobile home |
| Ownership | Own \| Renting |
| Year built (approx.) | Pre-1940 \| 1940–1959 \| 1960–1979 \| 1980–1999 \| 2000–2015 \| 2016+ |
| Number of bedrooms | 1 \| 2 \| 3 \| 4 \| 5+ |
| Number of floors | 1 \| 2 \| 3+ |
| ZIP code | Used for climate zone and frost risk — not stored as PII |

### 4.2 Construction & Structure

| Field | Options |
|---|---|
| Wall / exterior type | Wood frame with siding \| Brick \| Stucco \| Concrete block \| Unknown |
| Wall insulation | Yes \| No \| Unknown |
| Roof type | Asphalt shingle \| Metal \| Tile \| Slate \| Flat / Low-slope |
| Attic insulation | Yes \| No \| Unknown |
| Windows | Double-pane (full) \| Double-pane (partial) \| Single-pane |
| Basement / crawl space | Full basement \| Crawl space \| Slab \| None |
| Deck / patio | Yes \| No |
| Attached garage | Yes \| No |

### 4.3 Heating & Cooling

| Field | Options |
|---|---|
| Heating type | Gas furnace \| Electric furnace \| Heat pump \| Boiler (radiant) \| Baseboard electric \| None |
| Cooling type | Central AC \| Heat pump \| Window units \| Evaporative cooler \| None |
| HVAC age (approx.) | < 5 years \| 5–10 years \| 10–15 years \| 15+ years \| Unknown |
| Last HVAC service | This year \| 1–2 years ago \| 3+ years ago \| Never \| Unknown |
| Water heater type | Tank (gas) \| Tank (electric) \| Tankless (gas) \| Tankless (electric) \| Heat pump water heater |
| Water heater age | < 5 years \| 5–10 years \| 10+ years \| Unknown |
| Humidifier / dehumidifier | Yes \| No |

### 4.4 Outdoor & Exterior

| Field | Options |
|---|---|
| Yard / lawn | Front only \| Rear only \| Both \| None |
| Driveway | Yes \| No |
| Gutters | Aluminum \| Vinyl \| Copper \| Steel \| None \| Unknown |
| Siding type | Vinyl \| Wood \| Fiber cement \| Brick \| Stucco \| Other |
| Fence | Wood \| Metal \| Vinyl \| None |
| Sprinkler / irrigation system | Yes \| No |
| Flat roof sections (garage, addition) | Yes \| No |

### 4.5 Systems & Appliances

| Field | Options |
|---|---|
| Smoke / CO alarms | Yes \| No \| Unsure |
| Main water shut-off location known | Yes \| No |
| Sump pump | Yes \| No |
| Septic system | Yes \| No (municipal sewer) |
| Solar panels | Yes \| No |
| EV charger | Yes \| No |
| Home security system | Yes \| No |

---

## 5. Screens & User Flow

### 5.1 Onboarding Flow (First Launch)

| Screen | Purpose | Key Actions |
|---|---|---|
| O1 — Welcome | Value proposition, property focus | Tap 'Set Up My Home' |
| O2 — Property Basics | Core property info | Select options, tap Continue |
| O3 — Construction | Exterior, roof, insulation | Select options, tap Continue |
| O4 — Heating & Cooling | HVAC and water heater details | Select options, tap Continue |
| O5 — Outdoor & Exterior | Yard, gutters, siding | Select options, tap Continue |
| O6 — Systems & Appliances | Safety and utility systems | Select options, tap Continue |
| O7 — Generating Calendar | AI processing screen | Auto-advances when ready |
| O8 — Your Home is Ready | Summary + first peek at calendar | Tap 'See My Calendar' |

**O1 — Welcome Screen**

- Headline: 'Your home, looked after'
- Three benefit bullets: personalized to your property / timely seasonal reminders / a full maintenance history
- Single CTA: 'Set Up My Home' — no sign-up required
- Estimated setup time shown: 'Takes about 4 minutes'

**O2 to O6 — Profile Screens**

- Each screen covers one section of the home profile
- Progress indicator at top (e.g. Step 2 of 6)
- All inputs are tap-to-select — no typing required
- 'Skip this section' available on every screen — unknown answers are handled gracefully by the AI
- Back navigation preserves selections

**O7 — Generating Calendar Screen**

- Animated house illustration with progress indicator
- Rotating context messages while waiting: 'Checking your roof type...', 'Planning your HVAC schedule...', 'Preparing your winter checklist...'
- Target latency: under 8 seconds for calendar generation
- On failure: friendly retry screen — profile data is preserved

**O8 — Home is Ready Screen**

- Celebration moment — this is the payoff for completing onboarding
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
- Seasonal banner — changes each quarter with a relevant illustration and headline (e.g. 'Winter prep season — 4 tasks to do before December')

**M2 — Maintenance Calendar**

- Default view: current month highlighted, scrollable through all 12 months
- Each task shown as a card: task name, category icon, urgency chip, estimated time
- Filter bar: All | Spring | Summer | Fall | Winter | Overdue
- Completed tasks shown with strikethrough and green check — remain visible for reference
- Overdue tasks (window has passed, not completed) shown in amber

**M3 — Task Detail**

This screen is where the AI personalization shines — every task explanation is written specifically for the user's home, not copied from a generic template.

- Task name and category
- Why this matters for your home — 2–3 sentence AI-generated explanation referencing their specific property details
- Optimal timing window — e.g. 'Best done October–November, before first frosts'
- Estimated effort — e.g. 'DIY: 1–2 hours' or 'Hire a contractor: half day'
- Difficulty level — DIY-friendly | Requires basic skills | Call a pro
- Mark as Complete button — triggers log entry and confetti micro-animation
- Snooze options — 1 week / 2 weeks / 'Remind me next season'

**M4 — Task Log**

- Chronological list of all completed tasks, most recent first
- Each entry shows: task name, date completed, category
- Filter by year and by category (HVAC / Roofing / Plumbing / Yard / Safety etc.)
- Summary stats at top: total tasks completed, current year count
- Export option (Phase 2) — for property sale documentation

**M5 — Home Profile**

- View-only by default, with Edit button per section
- Editing a core field (heating, construction, roof) shows a warning: 'This may update your maintenance calendar'
- On save of a core field change: prompt to regenerate calendar
- Calendar regeneration uses same O7 loading screen

---

## 6. AI Calendar Generation

### 6.1 Generation Approach

Calendar generation is a single structured API call made on completion of the home profile setup. The response is a JSON array of maintenance tasks that is stored locally and rendered into the calendar view.

| | |
|---|---|
| **Model** | claude-sonnet-4-20250514 via Anthropic API. The prompt is large (full home profile) but the call is infrequent — once at setup and on significant profile changes only. |

### 6.2 Prompt Structure

- **System prompt:** You are a home maintenance expert for US homeowners. Given a home profile, generate a personalized annual maintenance calendar. Return only valid JSON matching the task schema. Be specific to the property — do not generate tasks that don't apply to this home type. Use US terminology for all materials, systems, and trade references.
- **Home profile block:** Full structured home profile as key-value pairs
- **Location context:** US climate zone derived from ZIP code (e.g. 'Upper Midwest — cold winters, hot summers, freeze-thaw cycles')
- **Task schema definition:** Provided in the prompt to enforce structured output

### 6.3 Task Schema

Each generated task conforms to the following schema, which maps directly to the local Task table:

| Field | Description |
|---|---|
| task_key | Unique string identifier e.g. 'furnace_service_annual' |
| title | Short task name e.g. 'Annual furnace tune-up' |
| category | Enum: hvac \| roofing \| plumbing \| yard \| safety \| structure \| electrical \| general |
| why_this_home | 2–3 sentence explanation personalized to the user's property |
| optimal_month_start | Integer 1–12 — start of ideal window |
| optimal_month_end | Integer 1–12 — end of ideal window |
| urgency | Enum: low \| medium \| high \| critical |
| effort_diy_hours | Estimated DIY hours (null if not DIY-appropriate) |
| difficulty | Enum: diy_easy \| diy_moderate \| call_a_pro |
| recurrence | Enum: annual \| biannual \| quarterly \| one_off \| as_needed |
| applies_because | Short tag e.g. 'gas_furnace' or 'pre_1980_construction' — for audit |

### 6.4 Example Generated Tasks

The following illustrates the kind of specificity the AI should achieve for a 1960s brick ranch with a gas furnace and aluminum gutters:

| Task | Window | Why This Home |
|---|---|---|
| Annual furnace tune-up | Sep–Oct | Your gas furnace is 10–15 years old — this age group sees higher failure rates in winter. Servicing now avoids emergency call-out charges. |
| Replace furnace filter | Oct, Jan, Apr | With an older forced-air system, a clogged filter strains the blower motor and reduces heat output. Replace every 3 months during heating season. |
| Clean and inspect aluminum gutters | Oct–Nov | Aluminum gutters can sag and separate at joints when debris causes standing water to freeze. Clear before first frost to prevent ice dam damage. |
| Check attic insulation depth | Sep | Homes from the 1960s often have degraded or insufficient attic insulation. Top up to R-38 to meet current energy standards and reduce heating bills. |
| Inspect and tuck-point brick exterior | Apr–May | Brick mortar deteriorates over time, especially through freeze-thaw cycles. Inspect annually and repair in dry weather to prevent water infiltration. |
| Test smoke and CO alarms | Jan + Jul | CO risk is elevated in homes with gas appliances. Test every 6 months and replace units older than 10 years. |
| Flush water heater | Sep | Sediment buildup in tank water heaters reduces efficiency and shortens lifespan, especially in hard-water areas. Annual flushing extends tank life. |
| Winterize irrigation system | Oct | If your ZIP code experiences freezing temperatures, blow out irrigation lines before first frost to prevent cracked pipes and valve damage. |

---

## 7. Seasonal Nudge System

### 7.1 How It Works

A WorkManager background job runs weekly on Wi-Fi. It evaluates the task calendar against the current date and determines whether any tasks are entering their optimal window, mid-window and incomplete, or at window close.

| Trigger Condition | Notification Type | Example |
|---|---|---|
| Task window opens (within 2 weeks) | Heads-up nudge | 'Fall prep starting: 3 tasks to get ready for winter' |
| Task incomplete after 3 weeks in window | Gentle follow-up | 'Your furnace tune-up window is closing — worth booking this week' |
| Task window closes in 7 days, still incomplete | Urgency nudge | 'Last chance: clear aluminum gutters before first frost' |
| Critical task overdue | Urgent alert | 'Your smoke alarms haven't been tested in 6 months — takes 2 minutes' |

### 7.2 Notification Limits

- Maximum 1 notification per week — multiple triggers are batched into a single summary
- Quiet hours respected — notifications delivered 8am–8pm local time only
- User can set notification frequency preference: Weekly digest | Individual alerts | Muted
- Critical safety tasks (smoke alarms, CO detectors, gas safety) bypass the weekly limit

### 7.3 Notification Content

Notification copy is pre-written per trigger condition — it does not call the AI at notification time. This keeps the nudge system fast, offline-capable, and free of API cost per notification.

---

## 8. Completed Task Log

The task log is a simple but strategically important feature. It is the app's stickiest asset — a growing record of a home's maintenance history that becomes more valuable the longer a user stays.

- Every task marked complete creates a log entry with timestamp and task details
- User can add optional free-text notes to any log entry (e.g. 'Used local HVAC company, cost $180')
- Log entries are permanent — completing then uncompleting a task keeps the log entry with an 'amended' flag
- Yearly summary available: 'In 2025 you completed 18 maintenance tasks on your home'

> **Future Value:** The task log is the foundation for Phase 2 export features — a PDF maintenance history report for property sale, refinancing, or insurance purposes. This is a natural premium upsell.

---

## 9. Local Data Model

All Phase 1 data is stored locally using Room. No user account or backend required. Cloud backup is a Phase 2 feature.

### 9.1 HomeProfile

| Column | Type / Notes |
|---|---|
| id | INTEGER PRIMARY KEY (single row per app install) |
| property_type | TEXT — enum key |
| year_built_range | TEXT — enum key |
| wall_type | TEXT — enum key |
| roof_type | TEXT — enum key |
| heating_type | TEXT — enum key |
| cooling_type | TEXT NULLABLE |
| hvac_age_range | TEXT NULLABLE |
| last_hvac_service | TEXT NULLABLE |
| water_heater_type | TEXT NULLABLE |
| water_heater_age | TEXT NULLABLE |
| has_yard | TEXT — front \| rear \| both \| none |
| gutter_type | TEXT NULLABLE |
| has_smoke_alarms | BOOLEAN NULLABLE |
| has_solar | BOOLEAN DEFAULT false |
| has_sump_pump | BOOLEAN DEFAULT false |
| has_irrigation | BOOLEAN DEFAULT false |
| location_climate_zone | TEXT — derived from ZIP, not raw ZIP |
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
| Notifications | NotificationManager + WorkManager | Reliable delivery, respects battery optimization |
| AI Calendar Generation | Anthropic API (Claude Sonnet) | Structured JSON output, strong reasoning for home-specific tasks |
| Networking | Retrofit + OkHttp | Type-safe API calls |
| DI | Hilt | Jetpack-native dependency injection |
| Preferences | DataStore | Async key-value store for settings and flags |
| Architecture | MVVM + Repository | Clean separation, testable |

---

## 11. Risks & Mitigations

| Risk | Impact | Mitigation |
|---|---|---|
| AI generates irrelevant tasks (e.g. irrigation winterization for a slab home in Phoenix) | Damages trust immediately | Strict system prompt with 'only generate tasks that apply based on the profile' instruction; applies_because tag enables QA audit |
| User skips too many profile fields — thin profile = generic calendar | Reduced personalization, less differentiation | Incentivize completeness: show task count preview updating live as sections are filled in |
| Notification fatigue causes opt-out | Core loop broken | Hard cap of 1 notification/week; user controls frequency; safety tasks bypass cap only |
| Calendar regeneration on profile edit causes confusion | User loses customized data | Warn before regenerating; preserve completed task log and log entries regardless |
| WorkManager killed on aggressive battery-saver devices | Seasonal nudges stop firing | Detect and guide user to whitelist the app in battery settings on first notification send |
| API cost at scale (calendar generation per new user) | Margin pressure | Calendar generated once per user; average prompt ~1500 tokens — cost is negligible per install at reasonable pricing |
| Climate zone mismatch (wrong tasks for local weather) | Irrelevant nudges damage trust | ZIP → climate zone mapping tested across all major US climate regions before launch |

---

## 12. Out of Scope (Phase 1)

- Photo-based issue diagnosis (AI identifies problems from images)
- Contractor connection or lead generation
- Cost estimator per task
- Cloud backup or user accounts
- Task log PDF export
- Shared home profile (e.g. for couples or landlords with multiple properties)
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
| 7-day retention | >= 40% (user opens app within 7 days of setup) |
| Notification opt-in rate | >= 65% |
| Tasks marked complete in first 30 days | >= 2 per active user |
| Positive feedback on task relevance | >= 80% thumbs up on Task Detail screen |
| Calendar generation latency (p95) | < 8 seconds |

---

## 14. Delivery Milestones

| Milestone | Deliverable | Est. Duration |
|---|---|---|
| M1 — Setup | Project scaffold, Room schema, Hilt, nav graph, DataStore | 2 days |
| M2 — Home Profile Flow | O1–O6 screens in Compose, all selector components, profile persistence | 4 days |
| M3 — AI Calendar Generation | Anthropic API integration, prompt engineering, JSON parsing, task storage | 4 days |
| M4 — Calendar UI | O7–O8 screens, M2 calendar view, filter bar, month grouping | 4 days |
| M5 — Task Detail & Completion | M3 task detail screen, mark complete flow, snooze logic, micro-animation | 3 days |
| M6 — Task Log | M4 log screen, log entry creation, year filter, summary stats | 2 days |
| M7 — Seasonal Nudge System | WorkManager job, trigger logic, notification batching, quiet hours | 3 days |
| M8 — Dashboard & Profile Edit | M1 dashboard, M5 profile edit with regeneration warning | 2 days |
| M9 — Polish & Edge Cases | Empty states, error handling, battery whitelist prompt, low-profile-completion handling | 3 days |
| M10 — Internal Release | APK via Firebase App Distribution | 1 day |

**Total estimated effort: 28 engineering days (~5.5 weeks for a single developer).**

---

*End of Phase 1 MVP Specification*

*Phase 2 spec (Photo Diagnosis + Contractor Connection + Task Log Export) to follow.*

*Confidential — v1.0*
