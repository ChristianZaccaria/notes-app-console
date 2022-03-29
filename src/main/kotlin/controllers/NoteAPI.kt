package controllers

import models.Note
import persistence.Serializer
import utils.Utilities.isValidListIndex


class NoteAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }


    fun listAllNotes(): String =
        if (notes.isEmpty()) "No notes stored"
        else formatListString(notes)





    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        }
        else null

    }

    fun searchByTitle(title: String) =
            formatListString(
                notes.filter {note -> note.noteTitle.lowercase().contains(title.lowercase())} )






    fun listActiveNotes(): String =
        if (numberOfActiveNotes() == 0) "No active notes stored"
        else formatListString( notes.filter { note -> !note.isNoteArchived } )




    //helper method to determine how many active notes there are
    fun numberOfActiveNotes(): Int = notes.count{note: Note -> !note.isNoteArchived}





    fun listArchivedNotes(): String =
        if (numberOfArchivedNotes() == 0) "No archived notes stored"
        else formatListString( notes.filter { note -> note.isNoteArchived } )


    //helper method to determine how many active notes there are
    fun numberOfArchivedNotes(): Int =  notes.count {note: Note -> note.isNoteArchived}






    fun listNotesBySelectedPriority(priority: Int): String =
        if (notes.isEmpty()) "no notes stored"

        else if (numberOfNotesByPriority(priority) == 0) "\nNo notes with priority: $priority"

        else "\n${numberOfNotesByPriority(priority)} notes with priority $priority:\n" +
            notes.filter { note -> note.notePriority == priority }
                .joinToString(separator = "\n") { note -> notes.indexOf(note).toString() + ": " + note.toString()}



    //helper method to determine how many notes there are of a specific priority
    fun numberOfNotesByPriority(priorityToCheck :Int): Int = notes.count {note: Note -> note.notePriority == priorityToCheck}









    fun listNotesByCategory(category: String): String =
        if (notes.isEmpty()) "no notes stored"

        else if (numberOfNotesByCategory(category) == 0) "\nNo notes with category: ${category.lowercase()}"

        else "\n${numberOfNotesByCategory(category.lowercase())} notes with category ${category.lowercase()}" +
                    notes.filter { note -> note.noteCategory.lowercase() == category.lowercase() }
                        .joinToString(separator = "\n") { note -> notes.indexOf(note).toString() + ": " + note.toString()}






    fun numberOfNotesByCategory(categoryCheck: String): Int = notes.count{note: Note -> note.noteCategory.lowercase() == categoryCheck.lowercase()}




    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        }
        else null
    }



    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        //find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }
        //if the note was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveNote(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val noteToArchive = notes[indexToArchive]
            if (!noteToArchive.isNoteArchived) {
                noteToArchive.isNoteArchived = true
                return true
            }
        }
        return false
    }


    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, notes)
    }

    //Helper Method
    fun formatListString(notesToFormat : List<Note>) : String =
        notesToFormat
            .joinToString (separator = "\n") { note ->
                notes.indexOf(note).toString() + ": " + note.toString() }



    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }

}