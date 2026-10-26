package com.uoi.pratikumwithbunafla.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class WasteFlowTest {
    @Test
    fun nextStatusFollowsOperationalSequence() {
        assertEquals(WasteFlow.ASSIGNED, WasteFlow.next(WasteFlow.WAITING))
        assertEquals(WasteFlow.TRANSPORTED, WasteFlow.next(WasteFlow.ASSIGNED))
        assertEquals(WasteFlow.DONE, WasteFlow.next(WasteFlow.TRANSPORTED))
        assertNull(WasteFlow.next(WasteFlow.DONE))
    }

    @Test
    fun previousStatusFollowsOperationalSequence() {
        assertNull(WasteFlow.previous(WasteFlow.WAITING))
        assertEquals(WasteFlow.ASSIGNED, WasteFlow.previous(WasteFlow.TRANSPORTED))
        assertEquals(WasteFlow.TRANSPORTED, WasteFlow.previous(WasteFlow.DONE))
    }

    @Test
    fun unknownStatusesDoNotEnterTheOperationalSequence() {
        assertNull(WasteFlow.next("status_tidak_dikenal"))
        assertNull(WasteFlow.previous("status_tidak_dikenal"))
        assertFalse(WasteFlow.isInProgress("status_tidak_dikenal"))
    }

    @Test
    fun legacyProcessingStatusIsStillRecognized() {
        assertTrue(WasteFlow.isInProgress(WasteFlow.PROCESSING))
        assertEquals("Diproses", WasteFlow.label(WasteFlow.PROCESSING))
    }
}
