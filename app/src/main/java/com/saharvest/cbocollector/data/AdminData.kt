package com.saharvest.cbocollector.data

data class Submission(
    val org: String,
    val meta: String,
    val state: String,
    val tone: Tone,
    val flags: List<String>,
    val facts: List<Pair<String, String>>,
)

/** Mirrors the SUBS constant — the admin's approvals queue. */
val SUBMISSIONS: List<Submission> = listOf(
    Submission(
        "Ikhaya Lethu Community Kitchen",
        "Thandi Mokoena · submitted 2 hours ago · Gauteng",
        "New",
        Tone.WARN,
        listOf("NPO certificate missing", "Pest control not confirmed"),
        listOf(
            "Province" to "Gauteng",
            "People served" to "320 / day",
            "Staff · volunteers" to "4 · 18",
            "Food storage" to "Dry, chilled",
            "Reliance on SA Harvest" to "High",
            "What3Words" to "///apple.orange.banana",
        ),
    ),
    Submission(
        "Bokamoso Soup Kitchen",
        "Thandi Mokoena · submitted yesterday · Free State",
        "New",
        Tone.WARN,
        listOf("Served totals do not add up to 410"),
        listOf(
            "Province" to "Free State",
            "People served" to "410 / day",
            "Staff · volunteers" to "2 · 26",
            "Food storage" to "Dry",
            "Reliance on SA Harvest" to "Total",
            "Transport capacity" to "None — delivery required",
        ),
    ),
    Submission(
        "Masibambane Care Centre",
        "Lerato Dube · re-visit requested 3 days ago · KZN",
        "Returned",
        Tone.NEW,
        listOf("Kitchen cleanliness marked no", "Awaiting re-visit photos"),
        listOf(
            "Province" to "KwaZulu-Natal",
            "People served" to "240 / day",
            "Staff · volunteers" to "3 · 11",
            "Food storage" to "Dry, chilled, frozen",
            "Reliance on SA Harvest" to "Moderate",
        ),
    ),
    Submission(
        "Ubuntu Table Trust",
        "Sibusiso Mabaso · submitted 4 days ago · Eastern Cape",
        "Ready",
        Tone.OK,
        emptyList(),
        listOf(
            "Province" to "Eastern Cape",
            "People served" to "150 / day",
            "Staff · volunteers" to "5 · 9",
            "Food storage" to "Dry, chilled",
            "Reliance on SA Harvest" to "Low",
        ),
    ),
)

data class RegisterEntry(
    val name: String,
    val status: String,
    val meta: String,
    val kg: String,
    val people: String,
    val docs: String,
    val tone: Tone,
)

/** Mirrors the `register` constant — first six entries of the national CBO register. */
val CBO_REGISTER: List<RegisterEntry> = listOf(
    RegisterEntry("Ikhaya Lethu Community Kitchen", "Pending", "Gauteng · Diepsloot Ext 4", "0 kg", "320", "2 of 4", Tone.WARN),
    RegisterEntry("Siyakhana Feeding Project", "Active", "Gauteng · Alexandra", "1.9 t", "180", "4 of 4", Tone.OK),
    RegisterEntry("Masibambane Care Centre", "Returned", "KwaZulu-Natal · Umlazi H", "0.4 t", "240", "3 of 4", Tone.NEW),
    RegisterEntry("Thandanani Youth Hub", "Active", "Western Cape · Khayelitsha", "0.8 t", "95", "4 of 4", Tone.OK),
    RegisterEntry("Bokamoso Soup Kitchen", "Pending", "Free State · Botshabelo", "0 kg", "410", "1 of 4", Tone.WARN),
    RegisterEntry("Ubuntu Table Trust", "Active", "Eastern Cape · Motherwell", "1.1 t", "150", "4 of 4", Tone.OK),
)

data class TeamMember(
    val name: String,
    val role: String,
    val count: String,
    val initials: String,
    val loadPct: Int,
)

/** Mirrors the `team` constant — field officer workload for the week. */
val FIELD_TEAM: List<TeamMember> = listOf(
    TeamMember("Thandi Mokoena", "Vetting officer · Gauteng North", "6", "TM", 66),
    TeamMember("Lerato Dube", "Vetting officer · KwaZulu-Natal", "9", "LD", 100),
    TeamMember("Sibusiso Mabaso", "Vetting officer · Eastern Cape", "4", "SM", 44),
    TeamMember("Sipho Ndlovu", "CBO collector · Ikhaya Lethu", "3", "SN", 33),
)

data class ProvinceBar(val label: String, val tonnes: Float, val value: String)

/** Mirrors the `bars` constant — August tonnage by province, scaled against the highest bar. */
val PROVINCE_BARS: List<ProvinceBar> = listOf(
    ProvinceBar("Gauteng", 7.4f, "7.4 t"),
    ProvinceBar("KwaZulu-Natal", 4.1f, "4.1 t"),
    ProvinceBar("Western Cape", 3.2f, "3.2 t"),
    ProvinceBar("Free State", 2.3f, "2.3 t"),
    ProvinceBar("Eastern Cape", 1.4f, "1.4 t"),
)

val ADMIN_BOTTOM_NAV: List<NavItem> = listOf(
    NavItem(Screen.AdminHome, "Overview", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavHome),
    NavItem(Screen.AdminApprovals, "Approvals", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavApprove),
    NavItem(Screen.AdminRegister, "Register", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavPeople),
    NavItem(Screen.AdminReports, "Reports", com.saharvest.cbocollector.ui.theme.GlyphPaths.NavBars),
)

val ADMIN_SCREENS_WITH_BOTTOM_NAV: Set<Screen> = setOf(
    Screen.AdminHome, Screen.AdminApprovals, Screen.AdminRegister, Screen.AdminReports,
)
