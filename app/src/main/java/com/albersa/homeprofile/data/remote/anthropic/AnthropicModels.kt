package com.albersa.homeprofile.data.remote.anthropic

data class AnthropicRequest(
    val model: String = "claude-sonnet-4-20250514",
    val max_tokens: Int = 4000,
    val system: String,
    val messages: List<AnthropicMessage>
)

data class AnthropicMessage(
    val role: String = "user",
    val content: String
)

data class AnthropicResponse(
    val content: List<ContentBlock>,
    val stop_reason: String?
)

data class ContentBlock(
    val type: String,
    val text: String?
)

data class GeneratedTask(
    val task_key: String,
    val title: String,
    val category: String,
    val why_this_home: String,
    val optimal_month_start: Int,
    val optimal_month_end: Int,
    val urgency: String,
    val effort_diy_hours: Double?,
    val difficulty: String,
    val recurrence: String,
    val applies_because: String
)

data class GeneratedTasksWrapper(
    val tasks: List<GeneratedTask>
)
