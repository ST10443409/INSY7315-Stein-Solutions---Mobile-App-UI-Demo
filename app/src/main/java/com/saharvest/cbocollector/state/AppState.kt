package com.saharvest.cbocollector.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.saharvest.cbocollector.data.CBOS
import com.saharvest.cbocollector.data.Cbo
import com.saharvest.cbocollector.data.ProductLine
import com.saharvest.cbocollector.data.Screen
import com.saharvest.cbocollector.data.VettingField
import com.saharvest.cbocollector.data.VettingSection
import com.saharvest.cbocollector.data.VETTING_SECTIONS

enum class DoneKind { Collect, Vet }
enum class Signatory { Donor, Cbo }

/**
 * Single source of truth for the whole collector flow, mirroring the
 * design spec's one-component state machine. Plain mutableState fields
 * (not StateFlow) are enough here: there is exactly one screen reading
 * this at a time and no background writers.
 */
class AppState : ViewModel() {
    var screen by mutableStateOf(Screen.Splash)

    // Auth
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var passwordVisible by mutableStateOf(false)
    var rememberMe by mutableStateOf(true)
    var role by mutableStateOf<String?>(null)

    // CBO directory
    var cboQuery by mutableStateOf("")
    var selectedCboIndex by mutableStateOf(0)
    val selectedCbo: Cbo get() = CBOS.getOrElse(selectedCboIndex) { CBOS[0] }

    fun filteredCbos(): List<Cbo> {
        val q = cboQuery.trim().lowercase()
        if (q.isEmpty()) return CBOS
        return CBOS.filter { "${it.name} ${it.meta}".lowercase().contains(q) }
    }

    // Vetting form
    val values = mutableStateMapOf<String, Any>(
        "legal" to CBOS[0].name,
        "contact" to "",
        "prov" to "Gauteng",
        "focus" to listOf("Feeding"),
        "meals" to listOf("Lunch"),
    )
    var openSectionIndex by mutableStateOf(0)

    fun valueOf(key: String): Any? = values[key]

    fun setValue(key: String, value: Any) {
        values[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun toggleChip(key: String, option: String) {
        val current = values[key] as? List<String> ?: emptyList()
        values[key] = if (option in current) current - option else current + option
    }

    fun isFilled(field: VettingField): Boolean {
        val v = values[field.key]
        return when (v) {
            null -> false
            is List<*> -> v.isNotEmpty()
            else -> v.toString().isNotBlank()
        }
    }

    fun requiredTotal(): Int = VETTING_SECTIONS.sumOf { s -> s.fields.count { it.required } }
    fun requiredDone(): Int = VETTING_SECTIONS.sumOf { s -> s.fields.count { it.required && isFilled(it) } }
    fun vettingProgressPct(): Int {
        val total = requiredTotal()
        return if (total == 0) 0 else (requiredDone() * 100) / total
    }

    fun sectionRequiredTotal(section: VettingSection): Int = section.fields.count { it.required }
    fun sectionRequiredDone(section: VettingSection): Int = section.fields.count { it.required && isFilled(it) }
    fun isSectionComplete(section: VettingSection): Boolean {
        val total = sectionRequiredTotal(section)
        return total > 0 && sectionRequiredDone(section) == total
    }

    fun toggleSection(index: Int) {
        openSectionIndex = if (openSectionIndex == index) -1 else index
    }

    fun jumpToSection(index: Int) {
        openSectionIndex = index
        screen = Screen.Vetting
    }

    fun openCbo(index: Int) {
        selectedCboIndex = index
        setValue("legal", CBOS[index].name)
        screen = Screen.Vetting
    }

    fun submitVetting() {
        screen = Screen.Done
        doneKind = DoneKind.Vet
    }

    // Collection
    val lines = mutableStateListOf(
        ProductLine("Vegtables", "42.5", "Mixed crates, good condition"),
        ProductLine("Baker", "18.0", "Bread, best before tomorrow"),
    )
    var addOpen by mutableStateOf(false)
    var draftCategory by mutableStateOf("Fruit")
    var draftKg by mutableStateOf("")
    var draftNotes by mutableStateOf("")

    fun openAddProduct() {
        addOpen = true
    }

    fun closeAddProduct() {
        addOpen = false
    }

    fun addProductLine() {
        lines.add(ProductLine(draftCategory, draftKg.ifBlank { "0" }, draftNotes))
        addOpen = false
        draftCategory = "Fruit"
        draftKg = ""
        draftNotes = ""
    }

    fun removeProductLine(index: Int) {
        lines.removeAt(index)
    }

    fun totalKg(): Double = lines.sumOf { it.kg.toDoubleOrNull() ?: 0.0 }

    val arrivalTime = "09:42"
    var departureTime by mutableStateOf<String?>(null)

    fun stampDeparture() {
        departureTime = "10:26"
    }

    var donorName by mutableStateOf("")
    var deliveryNote by mutableStateOf("")
    var collectNotes by mutableStateOf("")
    var noteAttached by mutableStateOf(false)

    fun attachNote() {
        noteAttached = true
    }

    // Signatures
    var donorSigned by mutableStateOf(false)
    var cboSigned by mutableStateOf(false)
    var signingWho by mutableStateOf(Signatory.Donor)
    var padHasInk by mutableStateOf(false)

    fun beginSigning(who: Signatory) {
        signingWho = who
        padHasInk = false
        screen = Screen.Sign
    }

    fun clearPad() {
        padHasInk = false
    }

    fun acceptSignature() {
        when (signingWho) {
            Signatory.Donor -> donorSigned = true
            Signatory.Cbo -> cboSigned = true
        }
        screen = Screen.Collect
    }

    fun submitCollection() {
        screen = Screen.Done
        doneKind = DoneKind.Collect
    }

    // Photos
    val shots = mutableStateListOf(false, false, false, false)

    fun toggleShot(index: Int) {
        shots[index] = !shots[index]
    }

    fun captureAllShots() {
        for (i in shots.indices) shots[i] = true
    }

    // Sync
    var synced by mutableStateOf(false)

    fun sync() {
        synced = true
    }

    // Done receipt
    var doneKind by mutableStateOf(DoneKind.Collect)

    fun go(target: Screen) {
        screen = target
        addOpen = false
    }

    // --- Vetting Officer flow ---
    // Kept as a separate values/index/shots trio (not shared with the CBO collector's
    // vetting form above) so the two roles never cross-contaminate a draft mid-session.
    val officerValues = mutableStateMapOf<String, Any>()
    var officerOpenSectionIndex by mutableStateOf(0)
    val officerShots = mutableStateListOf(false, false, false, false)
    var officerFormRef by mutableStateOf("VET-2026-0842 · draft on this device")

    fun officerValueOf(key: String): Any? = officerValues[key]

    fun officerSetValue(key: String, value: Any) {
        officerValues[key] = value
    }

    @Suppress("UNCHECKED_CAST")
    fun officerToggleChip(key: String, option: String) {
        val current = officerValues[key] as? List<String> ?: emptyList()
        officerValues[key] = if (option in current) current - option else current + option
    }

    fun officerIsFilled(field: VettingField): Boolean {
        val v = officerValues[field.key]
        return when (v) {
            null -> false
            is List<*> -> v.isNotEmpty()
            else -> v.toString().isNotBlank()
        }
    }

    fun officerRequiredDone(): Int = VETTING_SECTIONS.sumOf { s -> s.fields.count { it.required && officerIsFilled(it) } }
    fun officerProgressPct(): Int {
        val total = requiredTotal()
        return if (total == 0) 0 else (officerRequiredDone() * 100) / total
    }

    fun officerSectionRequiredDone(section: VettingSection): Int = section.fields.count { it.required && officerIsFilled(it) }
    fun officerIsSectionComplete(section: VettingSection): Boolean {
        val total = sectionRequiredTotal(section)
        return total > 0 && officerSectionRequiredDone(section) == total
    }

    fun officerToggleSection(index: Int) {
        officerOpenSectionIndex = if (officerOpenSectionIndex == index) -1 else index
    }

    fun officerJumpToSection(index: Int) {
        officerOpenSectionIndex = index
        screen = Screen.VoForm
    }

    fun startNewVettingForm() {
        officerValues.clear()
        officerOpenSectionIndex = 0
        for (i in officerShots.indices) officerShots[i] = false
        screen = Screen.VoForm
    }

    fun officerToggleShot(index: Int) {
        officerShots[index] = !officerShots[index]
    }

    fun officerCaptureAllShots() {
        for (i in officerShots.indices) officerShots[i] = true
    }

    fun submitOfficerVetting() {
        screen = Screen.VoDone
    }
}
