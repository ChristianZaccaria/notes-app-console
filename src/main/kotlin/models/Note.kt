package models

data class Note (var noteTitle: String, var noteContents: String, var notePriority: Int,
                    var noteCategory: String, var noteStatus: Int, var isNoteArchived: Boolean) {
}