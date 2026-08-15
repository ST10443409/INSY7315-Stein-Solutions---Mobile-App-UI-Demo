package com.saharvest.cbocollector.data

enum class FieldType { TEXT, AREA, NUM, SELECT, CHIPS, YES_NO, UPLOAD, DATE, MAP }

data class VettingField(
    val key: String,
    val label: String,
    val type: FieldType,
    val required: Boolean = false,
    val hint: String? = null,
    val placeholder: String? = null,
    val options: List<String> = emptyList(),
)

data class VettingSection(
    val name: String,
    val fields: List<VettingField>,
)

private val PROVINCES = listOf(
    "Eastern Cape", "Free State", "Gauteng", "KwaZulu-Natal", "Limpopo",
    "Mpumalanga", "North West", "Northern Cape", "Western Cape",
)

val PRODUCT_CATEGORIES = listOf(
    "Baker", "Beverages", "Dairy and eggs", "Dry Goods", "Frozen Foods",
    "Fruit", "Household and personal care", "Snacks and Confectionory", "Vegtables",
)

/** Mirrors the SECTIONS constant from the design spec's vetting form, field for field. */
val VETTING_SECTIONS: List<VettingSection> = listOf(
    VettingSection(
        "Organisation & contact",
        listOf(
            VettingField(
                "legal", "Organisation Legal Name", FieldType.TEXT, required = true,
                hint = "Record the official registered name from legal or registration documents. Cross-check spelling against provided certificates.",
                placeholder = "As it appears on the certificate",
            ),
            VettingField(
                "contact", "Contact’s Name and Surname", FieldType.TEXT, required = true,
                hint = "Capture the first and last name of the main contact person. This should be the individual responsible for communication and decision-making.",
            ),
            VettingField(
                "email", "Contact’s Email Address", FieldType.TEXT, required = true,
                hint = "Record the primary work email address for correspondence.",
            ),
            VettingField(
                "phone", "Contact’s Phone Number", FieldType.TEXT, required = true,
                hint = "Capture a direct mobile or landline number. Prefer a mobile for urgent follow-up.",
                placeholder = "(+27) South Africa",
            ),
            VettingField(
                "web", "Website", FieldType.TEXT,
                hint = "Enter the organisation’s official website address, if available.",
            ),
        ),
    ),
    VettingSection(
        "Location",
        listOf(
            VettingField(
                "addr", "Organisation Address", FieldType.MAP,
                hint = "Record the full physical address. If it isn’t found in the database, leave this blank and fill in the next question rather.",
            ),
            VettingField(
                "addr2", "Organisation Address 2", FieldType.AREA,
                hint = "Please include as much detail as possible, especially if rural.",
            ),
            VettingField("prov", "Organisation Province", FieldType.SELECT, required = true, options = PROVINCES),
            VettingField(
                "w3w", "What3Words", FieldType.TEXT, required = true,
                hint = "If in a rural area or informal settlement, use the What3Words link to verify the exact location. Record it exactly as given.",
                placeholder = "///apple.orange.banana",
            ),
        ),
    ),
    VettingSection(
        "Programmes & services",
        listOf(
            VettingField(
                "focus", "Core Business / Focus Areas", FieldType.CHIPS, required = true,
                hint = "Tick all relevant focus areas. Ensure alignment with stated programmes and target population.",
                options = listOf("Feeding", "ECD", "Education", "Health", "Shelter", "Skills training", "Care & support"),
            ),
            VettingField(
                "target", "Target Population", FieldType.CHIPS, required = true,
                hint = "Tick all relevant beneficiary groups.",
                options = listOf("Children", "Youth", "Women", "Elderly", "Disabled", "Homeless", "Families"),
            ),
            VettingField(
                "progs", "Programmes & Services", FieldType.AREA, required = true,
                hint = "Summarise the main programmes and services offered. Use short, factual descriptions.",
            ),
            VettingField(
                "chan", "Distribution Channel", FieldType.SELECT, required = true,
                hint = "Select whether services are delivered directly to beneficiaries or through other organisations.",
                options = listOf("Direct to beneficiaries", "Through other organisations", "Both"),
            ),
        ),
    ),
    VettingSection(
        "Staffing",
        listOf(
            VettingField("ftf", "Full-Time Females", FieldType.NUM, required = true, hint = "Confirm against payroll or HR data if available."),
            VettingField("ftm", "Full-Time Males", FieldType.NUM, required = true, hint = "Confirm against payroll or HR data if available."),
            VettingField("vol", "Number of Volunteers", FieldType.NUM, required = true, hint = "Clarify if this includes ad hoc helpers."),
        ),
    ),
    VettingSection(
        "Registration",
        listOf(
            VettingField("npo", "Registered NPO?", FieldType.YES_NO, required = true, hint = "Request certificate if not already provided."),
            VettingField("npoDoc", "NPO Certificate Upload", FieldType.UPLOAD, hint = "Upload a clear, legible copy of the NPO certificate."),
            VettingField("dsd", "Registered with DSD?", FieldType.YES_NO, required = true, hint = "State if registered with the Department of Social Development."),
            VettingField("pboDoc", "PBO Certificate Upload", FieldType.UPLOAD, hint = "Upload the Public Benefit Organisation certificate where applicable."),
        ),
    ),
    VettingSection(
        "Beneficiary demographics",
        listOf(
            VettingField(
                "race", "Race", FieldType.CHIPS, required = true,
                hint = "Tick all beneficiary race categories served.",
                options = listOf("African", "Coloured", "Indian/Asian", "White"),
            ),
            VettingField("gender", "Gender", FieldType.CHIPS, required = true, options = listOf("Female", "Male", "Other")),
            VettingField(
                "age", "Age Groups", FieldType.CHIPS, required = true,
                hint = "Confirm that these are consistent with service descriptions.",
                options = listOf("0–5", "6–12", "13–18", "19–35", "36–59", "60+"),
            ),
        ),
    ),
    VettingSection(
        "Feeding operation",
        listOf(
            VettingField("freq", "Feeding Frequency", FieldType.SELECT, required = true, options = listOf("Daily", "Weekdays only", "Weekly", "Twice a week", "Monthly")),
            VettingField("served", "Total Number of People Served", FieldType.NUM, required = true, hint = "Enter the approximate number of people served."),
            VettingField("servedF", "Number of Females Served", FieldType.NUM, required = true),
            VettingField("servedM", "Number of Males Served", FieldType.NUM, required = true),
            VettingField("sAfr", "No. of African Served", FieldType.NUM, required = true),
            VettingField("sCol", "No. of Coloured Served", FieldType.NUM, required = true),
            VettingField("sInd", "No. of Indian/Asian Served", FieldType.NUM, required = true),
            VettingField("sWhi", "No. of White Served", FieldType.NUM, required = true),
            VettingField("rely", "Reliance on SA Harvest", FieldType.SELECT, required = true, hint = "Select the CBO’s level of reliance.", options = listOf("Low", "Moderate", "High", "Total")),
            VettingField("trans", "Transport Capacity", FieldType.SELECT, required = true, hint = "Based on the CBO’s ability to collect food.", options = listOf("Own vehicle", "Hired transport", "None — delivery required")),
            VettingField("meals", "Meals Provided (B/L/D)", FieldType.CHIPS, required = true, options = listOf("Breakfast", "Lunch", "Dinner")),
            VettingField("days", "Days of the Week", FieldType.CHIPS, required = true, hint = "Useful for logistics planning.", options = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")),
            VettingField("lastFed", "Feeding History (Last date fed by SA Harvest)", FieldType.DATE),
        ),
    ),
    VettingSection(
        "Facilities & hygiene",
        listOf(
            VettingField("storage", "Food Storage (dry, chilled, frozen)", FieldType.CHIPS, required = true, options = listOf("Dry", "Chilled", "Frozen")),
            VettingField("kitchenPix", "Kitchen / Food Storage Images", FieldType.UPLOAD, hint = "Upload clear photos of food preparation and storage areas."),
            VettingField("clean", "Kitchen Cleanliness / Organisation", FieldType.YES_NO, required = true, hint = "Indicate whether the kitchen meets cleanliness and organisation standards."),
            VettingField("water", "Access to Water / Cleaning Products", FieldType.YES_NO, required = true),
            VettingField("toilets", "Toilets & Hygiene Facilities", FieldType.YES_NO, required = true),
            VettingField("pest", "Pest/Rodent Free", FieldType.YES_NO, required = true, hint = "Confirm there are no signs of pests or rodents."),
            VettingField(
                "infra", "Infrastructure Checks", FieldType.CHIPS, required = true,
                hint = "Tick all items that are present and in safe working order.",
                options = listOf("Roofing", "Fencing", "Lighting", "Gas safety", "Electrics"),
            ),
        ),
    ),
    VettingSection(
        "Access & security",
        listOf(
            VettingField("access", "Ease of Access for Delivery", FieldType.YES_NO, required = true, hint = "Confirm whether delivery vehicles can access the site without difficulty."),
            VettingField("parking", "Parking & Security", FieldType.YES_NO, required = true),
            VettingField("police", "Proximity to Police Station", FieldType.TEXT, hint = "State whether the site is near a police station (for safety assessment)."),
        ),
    ),
    VettingSection(
        "Capacity notes",
        listOf(
            VettingField("comments", "Additional Comments", FieldType.AREA, hint = "Record any further notes or relevant observations during vetting."),
            VettingField("proposal", "Proposal Writing Capabilities", FieldType.AREA),
            VettingField("digital", "Digital Capabilities", FieldType.AREA),
            VettingField("facPix", "Upload Fridge/Kitchen/Facility Photos", FieldType.UPLOAD),
        ),
    ),
    VettingSection(
        "Documents",
        listOf(
            VettingField("sla", "SLA?", FieldType.YES_NO, required = true),
            VettingField("consent", "Consent Form?", FieldType.YES_NO, required = true),
            VettingField("policy", "Policy Doc?", FieldType.YES_NO, required = true),
            VettingField("certs", "Upload Certificates (NPO/PBO)", FieldType.UPLOAD),
        ),
    ),
)
