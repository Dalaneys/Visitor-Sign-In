package stu.gpt.signing

import org.apache.poi.xwpf.usermodel.XWPFDocument
import org.junit.Test
import stu.gpt.signing.data.HistoryEntry
import java.io.FileOutputStream
import java.io.File
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class DocumentGeneratorTest {

    @Test
    fun generateDocx() {
        val docxFile = File("Sign-In_v4.3_User_Guide.docx")
        XWPFDocument().use { doc ->
            val p1 = doc.createParagraph()
            val r1 = p1.createRun()
            r1.setText("Sign-In v4.3 – Professional User & Technical Guide")
            r1.isBold = true
            r1.fontSize = 20
            r1.color = "1F4E78"

            val pIntro = doc.createParagraph()
            pIntro.createRun().setText("This comprehensive guide covers installation, configuration, themes, data management, tablet auto-start troubleshooting, and sign-in safeguards for Sign-In v4.3.")

            addSection(doc, "1. Main Screen & Sign-In Safeguards", listOf(
                "Name entry with predictive dropdown from the Members database.",
                "Duplicate prevention safeguard: pops up an 'Already Signed In' warning and resets the name box if someone tries to check in twice.",
                "Guest validation rule (AA AAA format).",
                "Role and Location selection."
            ))

            addSection(doc, "2. Theme Modes & RGB Color Engine", listOf(
                "Four mode-driven themes from left to right: Theatre (#2BBCF2), Rehearsals (#75FDB4), Youth (#85EFC8), and Dark (#71A0F1).",
                "Built-in RGB color selection engine with real-time sliders and live color preview.",
                "Automatic high-contrast text coloring based on perceptual luminance (white on dark backgrounds, black on light backgrounds)."
            ))

            addSection(doc, "3. Tablet Auto-Start & Battery Optimization", listOf(
                "App automatically starts up and shows over the lock screen on device reboot.",
                "Tablet Note: On certain tablets (Samsung, Lenovo, etc.), OEM background process managers may terminate apps launched automatically via boot receivers. To ensure reliability, go to Settings > Apps > Sign-In v4.3 > Battery, set it to 'Unrestricted', and enable Auto-start / Allow background activity in device care."
            ))

            addSection(doc, "4. Data Management & Warehouse Mode", listOf(
                "Excel (.xlsx) integration stored in Documents/Sign-In Data/.",
                "Members (Cols A & B), Cast (Row 1 Title, Row 2 Date, Row 3+ Names), Roles, Locations, Titles, and Questions.",
                "Warehouse mode switchable in Admin for dedicated Warehouse worksheets (GPT/YT toggle)."
            ))

            FileOutputStream(docxFile).use { out ->
                doc.write(out)
            }
        }
    }

    @Test
    fun generate3DaysRecords() {
        val now = LocalDateTime.now()
        val zone = ZoneId.systemDefault()
        
        val sampleEntries = listOf(
            HistoryEntry("Alice Smith", "Director", now.minusDays(0).withHour(9).toInstant(zone.rules.getOffset(now)).toEpochMilli(), now.minusDays(0).withHour(12).toInstant(zone.rules.getOffset(now)).toEpochMilli(), "AB12 CDE"),
            HistoryEntry("Bob Jones", "Cast", now.minusDays(1).withHour(10).toInstant(zone.rules.getOffset(now)).toEpochMilli(), now.minusDays(1).withHour(14).toInstant(zone.rules.getOffset(now)).toEpochMilli(), "XY65 ZW"),
            HistoryEntry("Charlie Brown", "Stage Crew", now.minusDays(2).withHour(11).toInstant(zone.rules.getOffset(now)).toEpochMilli(), now.minusDays(2).withHour(15).toInstant(zone.rules.getOffset(now)).toEpochMilli(), null)
        )

        for (i in 0..2) {
            val targetDate = now.minusDays(i.toLong())
            val targetDateStr = targetDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            val file = File("Records_${targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}.txt")
            
            val dayEntries = sampleEntries.take(i + 1)
            val content = buildString {
                append("Sign-In Records for $targetDateStr (Previous 24hrs)\n")
                append("Generated at: ${targetDate.format(DateTimeFormatter.ofPattern("HH:mm:ss"))} (Single daily file updated in place)\n")
                append("-".repeat(65) + "\n")
                append(String.format("%-25s %-20s %-12s %-8s %-8s %-12s\n", "Name", "Role/Loc", "Date", "In", "Out", "Reg"))
                
                dayEntries.forEach { entry ->
                    val arrivalDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.arrivalEpochMillis), zone)
                    val departureDt = LocalDateTime.ofInstant(Instant.ofEpochMilli(entry.departureEpochMillis), zone)
                    append(String.format("%-25s %-20s %-12s %-8s %-8s %-12s\n",
                        entry.name.take(24),
                        entry.roleOrLocation.take(19),
                        arrivalDt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                        arrivalDt.format(DateTimeFormatter.ofPattern("HH:mm")),
                        departureDt.format(DateTimeFormatter.ofPattern("HH:mm")),
                        entry.vehicleReg ?: ""
                    ))
                }
            }
            file.writeText(content)
        }
    }

    private fun addSection(doc: XWPFDocument, title: String, bullets: List<String>) {
        val pTitle = doc.createParagraph()
        pTitle.setSpacingBefore(200)
        val rTitle = pTitle.createRun()
        rTitle.setText(title)
        rTitle.isBold = true
        rTitle.fontSize = 14
        rTitle.color = "2F5597"

        for (bullet in bullets) {
            val pB = doc.createParagraph()
            pB.style = "ListBullet"
            val rB = pB.createRun()
            rB.setText(bullet)
            rB.fontSize = 11
        }
    }
}
