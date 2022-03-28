package controllers

import models.Note
import persistence.Serializer


class NoteAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        }
        else {
            var listOfNotes = ""
            for (i in notes.indices) {
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }


    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        }
        else null

    }


    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }



    fun listActiveNotes(): String {
        if (numberOfActiveNotes() == 0) {
            return "No active notes stored"
        }
        else {
            var activeNotes = ""
            for (note in notes) {
                if (!note.isNoteArchived) {
                    activeNotes += "${notes.indexOf(note)}: $note \n"
                }
            }
            return activeNotes
        }
    }


    fun numberOfActiveNotes(): Int {
        //helper method to determine how many active notes there are
       return notes.stream()
            .filter{note: Note -> !note.isNoteArchived}
            .count()
            .toInt()
    }


    fun listArchivedNotes(): String {
        if (numberOfArchivedNotes() == 0) {
            return "No archived notes stored"
        }
        else {
            var archivedNotes = ""
            for (note in notes) {
                if (note.isNoteArchived) {
                    archivedNotes += "${notes.indexOf(note)}: $note \n"
                }
            }
            return archivedNotes
        }
    }


    fun numberOfArchivedNotes(): Int {
        //helper method to determine how many active notes there are
        return notes.stream()
            .filter{note: Note -> note.isNoteArchived}
            .count()
            .toInt()
    }


    fun listNotesBySelectedPriority(priority: Int): String {
        if (notes.isEmpty()) {
            return "no notes stored"
        }
        else{
        var listOfNotes = ""
        for (i in notes.indices) {
            if (notes[i].notePriority == priority) {
                listOfNotes += "$i: ${notes[i]}\n"
            }
        }
            return if (listOfNotes.equals("")) {
                "No notes with priority: $priority"
            } else {

                "${numberOfNotesByPriority(priority)} notes with priority $priority:\n $listOfNotes"
            }
        }

    }

    fun numberOfNotesByPriority(priorityToCheck :Int): Int {
        //helper method to determine how many notes there are of a specific priority
        return notes.stream()
            .filter{note: Note -> note.notePriority == priorityToCheck}
            .count()
            .toInt()
    }





    fun listNotesByCategory(category: String): String {
        if (notes.isEmpty()) {
            return "no notes stored"

        }
        else{
            var listOfNotes = ""
            for (i in notes.indices) {
                if (notes[i].noteCategory.lowercase() == category.lowercase()) {
                    listOfNotes += "$i: ${notes[i]}\n"

                }

            }
            return if (listOfNotes.equals("")) {
                "No notes with category: ${category.lowercase()}"
            } else {
               "${numberOfNotesByCategory(category.lowercase())} notes with category ${category.lowercase()}: $listOfNotes"
            }
        }

    }



    fun numberOfNotesByCategory(categoryCheck: String): Int {
        return notes.stream()
            .filter{note: Note -> note.noteCategory.lowercase() == categoryCheck.lowercase()}
            .count()
            .toInt()
    }



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



    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }

}