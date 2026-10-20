# Sign-In v4.2 – User & Technical Guide

This guide explains how to use and maintain the Sign-In application.

---

## Main Screen (Sign-In Interface)

- **[Version & Branding]**
  - **Minimal UI:** The main screen is designed to be as clean as possible. Top corners are intentionally left empty.
  - **Next Sync Indicator:** A discreet timestamp in the top-right corner displays **"Next Sync: [HH:mm]"** (11.sp font size), indicating when the next scheduled 4-hour background data sync will occur.
  - **Admin Access:** To access settings, type your 4-digit PIN (default **1925**) into the **Name** input box. This will immediately navigate to the Admin area.

- **[Signing In & Safeguards]**
  - **Name Entry & Prediction:** Start typing your name. A dropdown will appear with matching names from the database.
  - **Duplicate Prevention:** If a person attempts to sign in while already checked in, an **"Already Signed In"** pop-up warning appears and the name box resets automatically.
  - **Guest Validation:** If you are a Guest (name not in database), the name must be at least **"AA AAA"** (2 letters for the first part, 3 for the second) to enable sign-in.
  - **Role/Location:** Select either a **Role** or a **Location** (only one is required).
  - **Sign-In Button:** Turns bright green when all required fields are valid.

- **[Vehicle Registration]**
  - **Memory:** The app automatically remembers your last used registration number for your name.
  - **Confirmation:** If enabled, a confirmation dialog appears before sign-in.
  - **Display:** On your sign-in tile, the registration number is stacked neatly below your sign-in time.

- **[Lists & Sorting]**
  - **Guest Sorting:** Guest visitors are always pinned to the **top** of both Role and Location sections.
  - **Live Mode (With Cast):** Visitors are listed on the left (with a separating bar between Role and Youth/Location entries), and Cast is on the right.
  - **Dark Mode / Meeting Mode:** When no cast list is loaded, or when Meeting mode is active, visitors are dynamically split across Left and Right panels based on the median alphabetical letter of the uploaded Members database (e.g., A-M and N-Z), displayed as `Members/Visitors (A-M)` and `Members/Visitors (N-Z)`. When Meeting mode is active, the header defaults to the default title (Titles cell B2).

---

## Admin Screen (Configuration)

- **[Design & Appearance]**
  - **Theme & Color Customization:** Go to Admin > Appearance, tap **Click to change** on the right of the tile, and use the built-in RGB color sliders to choose any custom theme color. Text contrast automatically adapts (white on dark backgrounds, black on light backgrounds).
  - **Screen Saver:** Test the screen saver directly from the Appearance tile.
  - **Admin Input Fields:** Input boxes feature clean, minimalist borderless inline styling.

- **[Data Management, Warehouse & Meeting Modes]**
  - **Upload Buttons:** The Admin screen features 3 large, evenly-sized, wrapped-text upload buttons: **"Cast"**, **"Members"**, and **"In App Lists"**.
  - **Database & Workbook Files:**
    - **Cast:** Row 1 = Title, Row 2 = Last Performance Date (dd/mm/yyyy format), Row 3+ = Names.
    - **Members:** Column A (First Name) & Column B (Last Name).
    - **In App Lists (Single Combined Workbook with 4 Worksheets):**
      - **Locations Worksheet:** Cell A1 = Inputbox Label, Cells A2:A20 = Theatre Locations, Cells A21:A30 = Warehouse Locations.
      - **Roles Worksheet:** Cell A1 = Inputbox Label, Cells A2:A20 = Theatre Roles, Cells A21:A30 = Warehouse Roles.
      - **Questions Worksheet (No header row):** Column B (Cells B1–B14: Last Person Sign-Out Checklist Text, Cells B15–B16: Reminder Pop-Up Message, Cell B17: Theatre Meeting Duration Hours `1-24`, Cell B18: Warehouse Meeting Duration Hours `1-24`).
      - **Titles Worksheet:** Column B (Cell B2: Default Play Title / Main Header, Cell B3: Left Panel Heading, Cell B4: Right Panel Heading, Cell B5: Screen Saver Message).
  - **Modes (Warehouse & Meeting):** In Admin under "Modes", toggle **Warehouse Enable** or **Meeting Mode** (mutually exclusive).
    - In Warehouse mode, active Locations and Roles are loaded automatically from Cells A21:A30 of the Locations/Roles worksheets. The main screen push button cycles through 3 steps: **GPT** (0) ➔ **YT** (1) ➔ **Meeting** (2, with meeting icon and bright blue background).
    - In Normal mode, active Locations and Roles are loaded from Cells A2:A20. Toggling Meeting mode displays the meeting push button (Off state is 2 shades lighter than On state).
    - **Remote Meeting Durations (Cells B17 & B18):** Theatre meeting duration (`1-24` hours) is controlled remotely by cell **B17**, and Warehouse meeting duration (`1-24` hours) is controlled remotely by cell **B18** in the Questions sheet of "In App Lists". Both values are visibly displayed as unchangeable read-only status indicators on the Admin screen. Meeting mode automatically deactivates after the respective configured hours have elapsed since activation.
    - **Admin Logging & Change Detection:** When "In App Lists" is updated or changed, background observers re-read all 4 worksheets and record an Admin Log entry detailing the worksheet change.
    - **"Signed in Cast member is King"**: If any cast member is checked in (`stageActive`), the Meeting button is automatically disabled in both modes.
  - **Automated Reset:** If Row 2 of the Cast file has a date that has passed (evaluated at the scheduled Auto Signout Time, default 04:00), the app automatically resets the Cast list and title.

- **[Automation, Logs & Retention]**
  - **Daily Maintenance & Auto Signout:** Scheduled at your choice (default **04:00**) to sign out everyone and refresh databases.
  - **Log Save Folder & Single Daily Record Updates:** Designated under **Log Save Folder (Saved every 4 hours)**. Rather than creating multiple files per day, the app creates exactly **one file per day** (`Records_YYYY-MM-DD.txt`) in the Records subfolder and updates it in place every 4 hours with all sign-ins and sign-outs from the day (previous 24 hours), complete with an explicit UK datestamp (`dd-MM-yyyy`) on every row.
  - **Log File Retention & Storage:** 
    - **Admin Logs:** Retained for **30 days** (older files are pruned). Admin logs record **only** PIN changes and actual database/file updates.
    - **Sign-In History / Records:** Retained for **30 days**.
    - **Storage Consumption:** Extremely lightweight, consuming **less than 20 MB total**.
  - **Unselected Log Folder Behavior:** If no log save folder is selected, core sign-in, database loading/saving, and local in-app history operate normally; external text log files and daily export files are simply skipped until a folder is designated.
  - **Display Over Other Apps (Auto-Start):** Required for automatic startup over the lock screen. If grayed out on Android 11+, tap the top-right three dots (`⋮`) and select **"Allow restricted settings"** first.
  - **Tablet Auto-Restart & Battery Optimization Note:** On certain tablets (Samsung, Lenovo, etc.), OEM battery management may terminate apps launched automatically via boot receivers. To ensure the app stays running after reboot, go to **Settings > Apps > Sign-In v4.2 > Battery**, set it to **"Unrestricted"**, and enable **Auto-start / Allow background activity** in device care.

---

## Technical Information for Developers

- **Framework:** Jetpack Compose (Modern Android UI).
- **Architecture:** `Repository.kt` as Single Source of Truth.
- **Persistence:** All databases are persisted locally in JSON format to ensure offline availability.
- **Excel Processing:** Powered by **Apache POI**. All data files are expected in Documents.
- **Footer Branding:** Created by Stu Sorrell, v4.2.2026.10
