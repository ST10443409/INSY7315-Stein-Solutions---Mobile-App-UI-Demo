package com.saharvest.cbocollector.data

enum class Tone { OK, WARN, NEW }

data class Cbo(
    val name: String,
    val status: String,
    val meta: String,
    val tone: Tone,
)

/** Mirrors the CBOS constant — the collector's book of organisations. */
val CBOS: List<Cbo> = listOf(
    Cbo("Ikhaya Lethu Community Kitchen", "Vetting due", "Gauteng · Diepsloot Ext 4 · 320 meals/day", Tone.WARN),
    Cbo("Siyakhana Feeding Project", "Vetted", "Gauteng · Alexandra · 180 meals/day", Tone.OK),
    Cbo("Masibambane Care Centre", "New", "KwaZulu-Natal · Umlazi H · 240 meals/day", Tone.NEW),
    Cbo("Thandanani Youth Hub", "Vetted", "Western Cape · Khayelitsha · 95 meals/day", Tone.OK),
    Cbo("Bokamoso Soup Kitchen", "Vetting due", "Free State · Botshabelo · 410 meals/day", Tone.WARN),
    Cbo("Ubuntu Table Trust", "Vetted", "Eastern Cape · Motherwell · 150 meals/day", Tone.OK),
)

data class Task(
    val time: String,
    val org: String,
    val meta: String,
    val kind: String,
    val badgeTone: Tone,
    val tagTone: Tone,
)

val TODAY_TASKS: List<Task> = listOf(
    Task("08:15", "Fresh Fields Wholesale · Bay 3", "Midrand · fruit and veg · bakkie shared", "Ready", Tone.WARN, Tone.WARN),
    Task("10:00", "Bay Harvest Bakery", "Randburg · bread, collect before 11:00", "Booked", Tone.NEW, Tone.OK),
    Task("12:30", "Cold Chain Depot", "Kya Sand · frozen · needs cooler boxes", "Booked", Tone.NEW, Tone.NEW),
)

data class HistoryEntry(
    val org: String,
    val kg: String,
    val meta: String,
    val state: String,
    val tone: Tone,
)

val COLLECTION_HISTORY: List<HistoryEntry> = listOf(
    HistoryEntry("Fresh Fields Wholesale · Bay 3", "60.5 kg", "12 Aug · 2 signatures · 08:20–08:44", "Synced", Tone.OK),
    HistoryEntry("Bay Harvest Bakery", "312.0 kg", "11 Aug · delivery note 4821", "Synced", Tone.OK),
    HistoryEntry("Cold Chain Depot", "148.5 kg", "9 Aug · photos pending upload", "Queued", Tone.WARN),
    HistoryEntry("Fresh Fields Wholesale · Bay 1", "96.0 kg", "7 Aug · donor signature only", "Synced", Tone.OK),
)

data class NavItem(val id: Screen, val label: String, val pathData: String)

enum class Screen {
    Splash, RoleSelection, Login, Home, Collect, Sign, Photos, Sync, Done, History,
    VoLogin, VoHome, VoForm, VoReview, VoPhotos, VoSync, VoDone,
    AdminLogin, AdminHome, AdminApprovals, AdminDetail, AdminDone, AdminRegister, AdminTeam, AdminReports,
}

val BOTTOM_NAV: List<NavItem> = listOf(
    NavItem(Screen.Home, "Runs", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavHome),
    NavItem(Screen.Collect, "Collect", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavCollect),
    NavItem(Screen.History, "History", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavHistory),
    NavItem(Screen.Sync, "Sync", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavSync),
)

val SCREENS_WITH_BOTTOM_NAV: Set<Screen> = setOf(Screen.Home, Screen.Collect, Screen.History, Screen.Sync)

/** Bottom nav for the Vetting Officer flow — Visits / Form / Evidence / Sync. */
val VO_BOTTOM_NAV: List<NavItem> = listOf(
    NavItem(Screen.VoHome, "Visits", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavHome),
    NavItem(Screen.VoForm, "Form", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavClipboard),
    NavItem(Screen.VoPhotos, "Evidence", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavCamera),
    NavItem(Screen.VoSync, "Sync", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavSync),
)

val VO_SCREENS_WITH_BOTTOM_NAV: Set<Screen> = setOf(Screen.VoHome, Screen.VoForm, Screen.VoPhotos, Screen.VoSync)

/** Site visits on the vetting officer's schedule — mirrors the design's `tasks` for App 2 of 3. */
val VO_TODAY_TASKS: List<Task> = listOf(
    Task("09:00", "Diepsloot Ext 4", "Site visit booked · no form started", "To do", Tone.WARN, Tone.WARN),
    Task("11:30", "Botshabelo", "Site visit booked · 14 km", "To do", Tone.NEW, Tone.OK),
    Task("14:00", "Umlazi H", "Site visit booked · re-check hygiene", "To do", Tone.NEW, Tone.NEW),
)

data class SyncItem(val title: String, val meta: String, val state: String, val tone: Tone)

data class ProductLine(val category: String, val kg: String, val notes: String)
