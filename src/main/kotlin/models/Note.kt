package models

data class Note (val noteTitle: String, val notePriority: Int,
                    val noteCategory: String, val isNotArchived: Boolean) {
}