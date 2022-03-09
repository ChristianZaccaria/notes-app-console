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

}