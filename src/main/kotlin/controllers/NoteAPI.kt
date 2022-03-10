package controllers

import models.Note



class NoteAPI {

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
        var counter = 0
        for (note in notes) {
            if (!note.isNoteArchived) {
                counter++
            }
        }
        return counter
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
        var counter = 0
        for (note in notes) {
            if (note.isNoteArchived) {
                counter++
            }
        }
        return counter
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

                "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
            }
        }

    }

    fun numberOfNotesByPriority(priorityToCheck :Int): Int {
        //helper method to determine how many notes there are of a specific priority
        var counter = 0
        for (note in notes) {
            if (note.notePriority == priorityToCheck) {
                counter++
            }
        }
        return counter
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
        var counter = 0
        for (note in notes) {
            if (note.noteCategory.lowercase() == categoryCheck.lowercase()) {
                counter++
            }
        }
        return counter
    }

}