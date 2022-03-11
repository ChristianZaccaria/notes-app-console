package controllers

import models.Note
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertFalse


class NoteAPITest {

    private var learnKotlin: Note? = null
    private var summerHoliday: Note? = null
    private var codeApp: Note? = null
    private var testApp: Note? = null
    private var swim: Note? = null
    private var populatedNotes: NoteAPI? = NoteAPI()
    private var emptyNotes: NoteAPI? = NoteAPI()

    @BeforeEach
    fun setup() {
        learnKotlin = Note("Learning Kotlin", 5, "College", false)
        summerHoliday = Note("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Note("Code App", 4, "Work", false)
        testApp = Note("Test App", 4, "Work", true)
        swim = Note("Swim - Pool", 3, "Hobby", false)




        //adding 5 Note to the notes api
        populatedNotes!!.add(learnKotlin!!)
        populatedNotes!!.add(summerHoliday!!)
        populatedNotes!!.add(codeApp!!)
        populatedNotes!!.add(testApp!!)
        populatedNotes!!.add(swim!!)

    }

    @AfterEach
    fun tearDown() {
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        populatedNotes = null
        emptyNotes = null

    }

    @Nested
    inner class AddNotes {
        @Test
        fun `adding a Note to a populated list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(5, populatedNotes!!.numberOfNotes())
            assertTrue(populatedNotes!!.add(newNote))
            assertEquals(6, populatedNotes!!.numberOfNotes())
            assertEquals(newNote, populatedNotes!!.findNote(populatedNotes!!.numberOfNotes() - 1))
        }

        @Test
        fun `adding a Note to an empty list adds to ArrayList`() {
            val newNote = Note("Study Lambdas", 1, "College", false)
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.add(newNote))
            assertEquals(1, emptyNotes!!.numberOfNotes())
            assertEquals(newNote, emptyNotes!!.findNote(emptyNotes!!.numberOfNotes() - 1))
        }
    }

    @Nested
    inner class ListNotes {
        @Test
        fun `listAllNotes returns No Notes Stored message when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.listAllNotes().lowercase().contains("no notes"))
        }

        @Test
        fun `listAllNotes returns Notes when ArrayList has notes stored`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val notesString = populatedNotes!!.listAllNotes().lowercase()
            assertTrue(notesString.contains("learning kotlin"))
            assertTrue(notesString.contains("code app"))
            assertTrue(notesString.contains("test app"))
            assertTrue(notesString.contains("swim"))
            assertTrue(notesString.contains("summer holiday"))
        }
    }

    @Nested
    inner class ActiveNotes {
        @Test
        fun `listActiveNotes returns no active notes stored when ArrayList is empty`(){
            assertEquals(0, emptyNotes!!.numberOfActiveNotes())
            assertTrue(emptyNotes!!.listActiveNotes().lowercase().contains("no active notes stored"))
        }

        @Test
        fun `listActiveNotes returns Active Notes stored in ArrayList`(){
            assertEquals(4, populatedNotes!!.numberOfActiveNotes())
            val activeNotesString = populatedNotes!!.listActiveNotes().lowercase()
            assertTrue(activeNotesString.contains("learning kotlin"))
            assertTrue(activeNotesString.contains("code app"))
            assertTrue(activeNotesString.contains("summer holiday"))
            assertFalse(activeNotesString.contains("test app"))
            assertTrue(activeNotesString.contains("swim"))
        }
    }

    @Nested
    inner class ArchivedNotes {
        @Test
        fun `listArchivedNotes returns no archived notes when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfArchivedNotes())
            assertTrue(emptyNotes!!.listArchivedNotes().lowercase().contains("no archived notes"))
        }


        @Test
        fun `listArchivedNotes returns Archived Notes stored in ArrayList`() {
            assertEquals(1, populatedNotes!!.numberOfArchivedNotes())
            val archivedNotesString = populatedNotes!!.listArchivedNotes().lowercase()
            assertFalse(archivedNotesString.contains("learning kotlin"))
            assertFalse(archivedNotesString.contains("code app"))
            assertFalse(archivedNotesString.contains("summer holiday"))
            assertTrue(archivedNotesString.contains("test app"))
            assertFalse(archivedNotesString.contains("swim"))
        }
    }


    @Nested
    inner class PriorityNotes {
        @Test
        fun `listNotesBySelectedPriority returns No Notes when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.listNotesBySelectedPriority(3).lowercase().contains("no notes")
            )
        }

        @Test
        fun `listNotesBySelectedPriority returns no notes when no notes of that priority exist`() {
            //Priority 1 (1 note), 2 (none), 3 (1 note). 4 (2 notes), 5 (1 note)
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val priority2String = populatedNotes!!.listNotesBySelectedPriority(2).lowercase()
            assertTrue(priority2String.contains("no notes"))
            assertTrue(priority2String.contains("2"))
        }

        @Test
        fun `listNotesBySelectedPriority returns all notes that match that priority when notes of that priority exist`() {
            //Priority 1 (1 note), 2 (none), 3 (1 note). 4 (2 notes), 5 (1 note)
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val priority1String = populatedNotes!!.listNotesBySelectedPriority(1).lowercase()
            assertTrue(priority1String.contains("1 note"))
            assertTrue(priority1String.contains("priority 1"))
            assertTrue(priority1String.contains("summer holiday"))
            assertFalse(priority1String.contains("swim"))
            assertFalse(priority1String.contains("learning kotlin"))
            assertFalse(priority1String.contains("code app"))
            assertFalse(priority1String.contains("test app"))


            val priority4String = populatedNotes!!.listNotesBySelectedPriority(4).lowercase()
            assertTrue(priority4String.contains("2 note"))
            assertTrue(priority4String.contains("priority 4"))
            assertFalse(priority4String.contains("swim"))
            assertTrue(priority4String.contains("code app"))
            assertTrue(priority4String.contains("test app"))
            assertFalse(priority4String.contains("learning kotlin"))
            assertFalse(priority4String.contains("summer holiday"))


            val priority3String = populatedNotes!!.listNotesBySelectedPriority(3).lowercase()
            assertTrue(priority3String.contains("1 note"))
            assertTrue(priority3String.contains("priority 3"))
            assertTrue(priority3String.contains("swim"))
            assertTrue(priority3String.contains("pool"))
            assertFalse(priority3String.contains("test app"))
            assertFalse(priority3String.contains("learning kotlin"))
            assertFalse(priority3String.contains("summer holiday"))
        }
    }

    @Nested
    inner class CategoryNotes {
        @Test
        fun `listNotesByCategory returns No Notes when ArrayList is empty`() {
            assertEquals(0, emptyNotes!!.numberOfNotes())
            assertTrue(emptyNotes!!.listNotesByCategory("").lowercase().contains("no notes")
            )
        }


        @Test
        fun `listNotesByCategory returns no notes when no notes of that category exist`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val categoryDummyString = populatedNotes!!.listNotesByCategory("fooling around").lowercase()
            assertTrue(categoryDummyString.contains("no notes"))
            assertTrue(categoryDummyString.contains("fooling around"))
        }

        @Test
        fun `listNotesByCategory returns all notes that match that category when notes of that category exist`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            val categoryWorkString = populatedNotes!!.listNotesByCategory("Work").lowercase()
            assertTrue(categoryWorkString.contains("2 note"))
            assertTrue(categoryWorkString.contains("category work"))
            assertFalse(categoryWorkString.contains("holiday"))
            assertFalse(categoryWorkString.contains("study"))
            assertFalse(categoryWorkString.contains("college"))
            assertFalse(categoryWorkString.contains("Hobby"))
            assertTrue(categoryWorkString.contains("test app"))


            val categoryCollegeString = populatedNotes!!.listNotesByCategory("college").lowercase()
            assertTrue(categoryCollegeString.contains("1 note"))
            assertTrue(categoryCollegeString.contains("category college"))
            assertFalse(categoryCollegeString.contains("holiday"))
            assertFalse(categoryCollegeString.contains("study"))
            assertTrue(categoryCollegeString.contains("college"))
            assertFalse(categoryCollegeString.contains("Hobby"))
            assertTrue(categoryCollegeString.contains("learning kotlin"))
        }
    }

    @Nested
    inner class DeleteNotes {
        @Test
        fun `deleting a Note that does not exist, returns null`() {
            assertNull(emptyNotes!!.deleteNote(0))
            assertNull(populatedNotes!!.deleteNote(-1))
            assertNull(populatedNotes!!.deleteNote(5))
        }

        @Test
        fun `deleting a Note that exists delete and returns deleted object`() {
            assertEquals(5, populatedNotes!!.numberOfNotes())
            assertEquals(swim, populatedNotes!!.deleteNote(4))
            assertEquals(4, populatedNotes!!.numberOfNotes())
            assertEquals(learnKotlin, populatedNotes!!.deleteNote(0))
            assertEquals(3, populatedNotes!!.numberOfNotes())
        }
    }
}