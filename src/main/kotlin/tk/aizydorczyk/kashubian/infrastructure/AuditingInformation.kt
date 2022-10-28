package tk.aizydorczyk.kashubian.infrastructure

import java.time.LocalDateTime

data class AuditingInformation(val userName: String, val executionTime: LocalDateTime)