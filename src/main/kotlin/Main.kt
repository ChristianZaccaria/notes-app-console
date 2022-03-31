import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import persistence.YAMLSerializer
import utils.CategoryUtility.isValidCategory
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import utils.Utilities.isValidText
import utils.Utilities.validRange
import java.io.File
import java.lang.System.exit


private val logger = KotlinLogging.logger{}
//private val noteAPI = NoteAPI(XMLSerializer(File("notes.xml")))
//private val noteAPI = NoteAPI(JSONSerializer(File("notes.json")))
private val noteAPI = NoteAPI(YAMLSerializer(File("notes.yml")))


fun main(args: Array<String>) {

    runMenu()



}


fun mainMenu() : Int {
    return ScannerInput.readNextInt("""
         > --------------------------------
         > |        NOTE KEEPER APP       |
         > --------------------------------
         > | NOTE MENU                    |
         > |   1) Add a note              |
         > |   2) List notes              |
         > |   3) Update a note           |
         > |   4) Delete a note           |
         > |   5) Archive a note          |
         > |   6) Search notes            |
         > |  20) Save Notes              |
         > |  21) Load Notes              |
         > --------------------------------
         > |   0) Exit                    |
         > --------------------------------
         > ==>>""".trimMargin(">"))
}


fun runMenu(){
    do {
        val option = mainMenu()
        when (option) {
            1 -> addNote()
            2 -> listNotes()
            3 -> updateNote()
            4 -> deleteNote()
            5 -> archiveNote()
            6 -> searchNotes()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}


fun addNote(){
    //logger.info { "addNote() function invoked" }
    var noteTitle = readNextLine("Enter a title for the note: ")
    while(!isValidText(noteTitle))
        noteTitle = readNextLine("Please enter a title for the note: ")


    var noteContents = readNextLine("Enter contents of the note: ")
    while(!isValidText(noteContents))
        noteContents = readNextLine("Please enter contents of the note: ")

    var notePriority = readNextInt("Enter priority (1-low, 2, 3, 4, 5-high): ")
    while (!validRange(notePriority,1,5))
        notePriority = readNextInt("Please enter a priority (1-low, 2, 3, 4, 5-high): ")


    var noteCategory = readNextLine("Enter one of the categories: (Hobby, College, Holiday, Work, Misc): ").lowercase()
    while(!isValidCategory(noteCategory.lowercase()))
        noteCategory = readNextLine("Enter one of the categories: (Hobby, College, Holiday, Work, Misc): ").lowercase()

    var noteStatus = readNextInt("Enter a status: (1-todo, 2-doing, 3-done): ")
    while (!validRange(noteStatus,1,3))
        noteStatus = readNextInt("Enter a status: (1-todo, 2-doing, 3-done): ")

    val isAdded = noteAPI.add(Note(noteTitle, noteContents, notePriority, noteCategory, noteStatus, false))

    if (isAdded) {
        println("Added Successfully")
    }
    else {
        println("Add Failed")
    }

}

fun listNotes(){
    //logger.info { "listNotes() function invoked" }
    if (noteAPI.numberOfNotes() > 0) {
        val option = readNextInt("""
                  > -----------------------------------------------
                  > |  1) View ALL notes                          |
                  > |  2) View ACTIVE notes                       |
                  > |  3) View ARCHIVED notes                     |
                  > |  4) View Most Important Notes (4-5)         |
                  > |  5) View Least Important Notes (1-3)        |
                  > |  6) View Notes about Work or College        |
                  > |  7) View Notes about Hobby, Holiday or Misc |
                  > -----------------------------------------------
          > ==>>""".trimMargin(">"))


        when (option) {
            1 -> listAllNotes()
            2 -> listActiveNotes()
            3 -> listArchivedNotes()
            4 -> listPriority4and5()
            5 -> listPriority1and2and3()
            6 -> listWorkNotes()
            7 -> listFunNotes()
            else -> println("Invalid option entered $option")
        }


        }
    else {
        println("Option Invalid - No Notes Stored")
    }
}

fun listAllNotes() {
    println(noteAPI.listAllNotes())
}

fun listActiveNotes() {
    println(noteAPI.listActiveNotes())
}

fun listArchivedNotes() {
    println(noteAPI.listArchivedNotes())
}

fun listPriority4and5() {
    println(noteAPI.listNotesBySelectedPriority(5))
    println(noteAPI.listNotesBySelectedPriority(4))
}

fun listPriority1and2and3() {
    println(noteAPI.listNotesBySelectedPriority(3))
    println(noteAPI.listNotesBySelectedPriority(2))
    println(noteAPI.listNotesBySelectedPriority(1))
}

fun listWorkNotes() {
    println(noteAPI.listNotesByCategory("Work"))
    println(noteAPI.listNotesByCategory("College"))
}

fun listFunNotes() {
    println(noteAPI.listNotesByCategory("Hobby"))
    println(noteAPI.listNotesByCategory("Holiday"))
    println(noteAPI.listNotesByCategory("Misc"))
}

fun updateNote(){
    //logger.info { "updateNote() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the note to update: ")
        if (noteAPI.isValidIndex(indexToUpdate)) {
            var noteTitle = readNextLine("Enter a title for the note: ")
            while(!isValidText(noteTitle))
                noteTitle = readNextLine("Please enter a title for the note: ")


            var noteContents = readNextLine("Enter contents of the note: ")
            while(!isValidText(noteContents))
                noteContents = readNextLine("Please enter contents of the note: ")

            var notePriority = readNextInt("Enter priority (1-low, 2, 3, 4, 5-high): ")
            while (!validRange(notePriority,1,5))
                notePriority = readNextInt("Please enter a priority (1-low, 2, 3, 4, 5-high): ")


            var noteCategory = readNextLine("Enter one of the categories: (Hobby, College, Holiday, Work, Misc): ").lowercase()
            while(!isValidCategory(noteCategory.lowercase()))
                noteCategory = readNextLine("Enter one of the categories: (Hobby, College, Holiday, Work, Misc): ").lowercase()

            var noteStatus = readNextInt("Enter a status: (1-todo, 2-doing, 3-done): ")
            while (!validRange(noteStatus,1,3))
                noteStatus = readNextInt("Enter a status: (1-todo, 2-doing, 3-done): ")

            //pass the index of the note and the new note details to NoteAPI for updating and check for success.
            if (noteAPI.updateNote(indexToUpdate, Note(noteTitle, noteContents, notePriority, noteCategory, noteStatus,false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        }
        else {
            println("There are no notes for this index number")
        }
    }
}


fun deleteNote(){
    //logger.info { "deleteNote() function invoked" }
    listNotes()
    if (noteAPI.numberOfNotes() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the note to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val noteToDelete = noteAPI.deleteNote(indexToDelete)
        if (noteToDelete != null) {
            println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
        }
        else {
            println("Delete NOT Successful")
        }
    }
}


fun archiveNote(){
    println(noteAPI.listActiveNotes())
    if (noteAPI.numberOfActiveNotes() > 0) {
        //only ask the user to choose the note to archive if active notes exist
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (noteAPI.archiveNote(indexToArchive)) {
            println("Archive Successful")
        }
        else{
            println("Archive NOT Successful")
        }
    }
}


fun searchNotes(){
    val searchTitle = readNextLine("Enter the description you wish to search: ")
    val searchResults = noteAPI.searchByTitle(searchTitle)
    if (searchResults.isEmpty()) println("No notes found!")
    else println(searchResults)
}


fun save() {
    try {
        noteAPI.store()
        println("Successfully Saved to File")
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        noteAPI.load()
        println("Successfully Loaded from File")
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}


fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

