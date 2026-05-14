package com.albersa.homeprofile.domain.usecase

import com.albersa.homeprofile.data.local.datastore.AppPreferences
import com.albersa.homeprofile.data.local.db.dao.MaintenanceTaskDao
import com.albersa.homeprofile.data.local.db.entity.MaintenanceTaskEntity
import com.albersa.homeprofile.data.remote.anthropic.AnthropicApiService
import com.albersa.homeprofile.data.remote.anthropic.AnthropicMessage
import com.albersa.homeprofile.data.remote.anthropic.AnthropicRequest
import com.albersa.homeprofile.data.remote.anthropic.GeneratedTasksWrapper
import com.albersa.homeprofile.domain.model.HomeProfile
import com.albersa.homeprofile.domain.model.MaintenanceTask
import com.google.gson.Gson
import javax.inject.Inject

class CalendarGenerationUseCase @Inject constructor(
    private val api: AnthropicApiService,
    private val dao: MaintenanceTaskDao,
    private val prefs: AppPreferences,
    private val gson: Gson
) {
    suspend operator fun invoke(profile: HomeProfile): Result<List<MaintenanceTask>> =
        runCatching {
            val response = api.createMessage(
                AnthropicRequest(
                    system = SYSTEM_PROMPT,
                    messages = listOf(AnthropicMessage(content = buildUserMessage(profile)))
                )
            )

            val rawText = response.content.firstOrNull { it.type == "text" }?.text
                ?: error("Empty response from API")

            val wrapper = gson.fromJson(rawText, GeneratedTasksWrapper::class.java)
                ?: error("Failed to parse task list")

            val now = System.currentTimeMillis()
            val entities = wrapper.tasks.map { task ->
                MaintenanceTaskEntity(
                    taskKey = task.task_key,
                    title = task.title,
                    category = task.category,
                    whyThisHome = task.why_this_home,
                    optimalMonthStart = task.optimal_month_start.coerceIn(1, 12),
                    optimalMonthEnd = task.optimal_month_end.coerceIn(1, 12),
                    urgency = task.urgency,
                    effortDiyHours = task.effort_diy_hours,
                    difficulty = task.difficulty,
                    recurrence = task.recurrence,
                    appliesBecause = task.applies_because,
                    generatedAt = now
                )
            }

            dao.deleteAll()
            dao.insertAll(entities)
            prefs.setLastCalendarGeneratedAt(now)

            entities.mapIndexed { index, e ->
                MaintenanceTask(
                    id = index + 1,
                    taskKey = e.taskKey,
                    title = e.title,
                    category = e.category,
                    whyThisHome = e.whyThisHome,
                    optimalMonthStart = e.optimalMonthStart,
                    optimalMonthEnd = e.optimalMonthEnd,
                    urgency = e.urgency,
                    effortDiyHours = e.effortDiyHours,
                    difficulty = e.difficulty,
                    recurrence = e.recurrence,
                    appliesBecause = e.appliesBecause
                )
            }
        }

    private fun buildUserMessage(profile: HomeProfile): String = buildString {
        appendLine("Home Profile:")
        appendLine("- Property Type: ${profile.propertyType ?: "Unknown"}")
        appendLine("- Ownership: ${profile.ownership ?: "Unknown"}")
        appendLine("- Year Built Range: ${profile.yearBuiltRange ?: "Unknown"}")
        appendLine("- Bedrooms: ${profile.bedrooms ?: "Unknown"}")
        appendLine("- Floors: ${profile.floors ?: "Unknown"}")
        appendLine("- Climate Zone: ${profile.locationClimateZone ?: "Unknown"}")
        appendLine("- Wall Type: ${profile.wallType ?: "Unknown"}")
        appendLine("- Wall Insulation: ${profile.wallInsulation ?: "Unknown"}")
        appendLine("- Roof Type: ${profile.roofType ?: "Unknown"}")
        appendLine("- Attic Insulation: ${profile.atticInsulation ?: "Unknown"}")
        appendLine("- Window Type: ${profile.windowType ?: "Unknown"}")
        appendLine("- Basement/Crawlspace: ${profile.basementType ?: "Unknown"}")
        appendLine("- Has Deck: ${profile.hasDeck ?: "Unknown"}")
        appendLine("- Attached Garage: ${profile.hasAttachedGarage ?: "Unknown"}")
        appendLine("- Heating Type: ${profile.heatingType ?: "Unknown"}")
        appendLine("- Cooling Type: ${profile.coolingType ?: "Unknown"}")
        appendLine("- HVAC Age: ${profile.hvacAge ?: "Unknown"}")
        appendLine("- Last HVAC Service: ${profile.lastHvacService ?: "Unknown"}")
        appendLine("- Water Heater Type: ${profile.waterHeaterType ?: "Unknown"}")
        appendLine("- Water Heater Age: ${profile.waterHeaterAge ?: "Unknown"}")
        appendLine("- Has Humidifier: ${profile.hasHumidifier ?: "Unknown"}")
        appendLine("- Has Yard: ${profile.hasYard ?: "Unknown"}")
        appendLine("- Has Driveway: ${profile.driveway ?: "Unknown"}")
        appendLine("- Gutter Type: ${profile.gutterType ?: "Unknown"}")
        appendLine("- Siding Type: ${profile.sidingType ?: "Unknown"}")
        appendLine("- Fence Type: ${profile.fenceType ?: "Unknown"}")
        appendLine("- Sprinkler System: ${profile.hasSprinklerSystem ?: "Unknown"}")
        appendLine("- Flat Roof Sections: ${profile.hasFlatRoofSections ?: "Unknown"}")
        appendLine("- Smoke/CO Alarms: ${profile.hasSmokeAlarms ?: "Unknown"}")
        appendLine("- Water Shutoff Known: ${profile.waterShutoffKnown ?: "Unknown"}")
        appendLine("- Sump Pump: ${profile.hasSumpPump ?: "Unknown"}")
        appendLine("- Septic Tank: ${profile.hasSepticTank ?: "Unknown"}")
        appendLine("- Solar Panels: ${profile.hasSolar}")
        appendLine("- EV Charger: ${profile.hasEvCharger ?: "Unknown"}")
        appendLine("- Security System: ${profile.hasSecuritySystem ?: "Unknown"}")
        appendLine()
        append(TASK_SCHEMA_PROMPT)
    }

    companion object {
        private const val SYSTEM_PROMPT = """You are a home maintenance expert. Given a home profile, generate a personalised annual maintenance calendar for a US homeowner. Return ONLY a valid JSON object with a single key "tasks" containing an array of task objects. Match the schema exactly. Only generate tasks that apply to features explicitly present in this home — skip tasks for any field marked Unknown or false. Aim to generate 18–24 tasks."""

        private const val TASK_SCHEMA_PROMPT = """Return your response as a JSON object matching this schema exactly:

{
  "tasks": [
    {
      "task_key": "unique_snake_case_identifier",
      "title": "Short task name",
      "category": "hvac|roofing|plumbing|yard|safety|structure|electrical|general",
      "why_this_home": "2-3 sentences specific to this property explaining why this task matters",
      "optimal_month_start": 1,
      "optimal_month_end": 12,
      "urgency": "low|medium|high|critical",
      "effort_diy_hours": 2.0,
      "difficulty": "diy_easy|diy_moderate|call_a_pro",
      "recurrence": "annual|biannual|quarterly|one_off|as_needed",
      "applies_because": "short_audit_tag"
    }
  ]
}

Rules:
- effort_diy_hours must be null if the task requires a professional
- optimal_month_start and optimal_month_end are integers 1–12
- Do not include any text, markdown, or explanation outside the JSON object"""
    }
}
