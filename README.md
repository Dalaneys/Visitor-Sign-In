# Sign-In v4.3 – User & Technical Guide

This guide explains how to use and maintain the Sign-In application.

---

## Main Screen (Sign-In Interface)

- **[Version & Branding]**
  - **Minimal UI:** The main screen is designed to be as clean as possible. Top corners are intentionally left empty.
  - **Real-Time Event Export:** Sign-in and sign-out records are exported instantly in real-time as events occur. `MediaScannerConnection` notifications update file metadata on disk instantly so cloud sync tools (like AutoSync / DriveSync) capture uploads immediately before ScreenSaver / Doze mode.
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
    - **Cast:** Worksheets `Location #1`, `Location #2 - A`, `Location #2 - B`. Row 1 = Title, Row 2 = Last Performance Date (dd/mm/yyyy format), Row 3+ = Names.
    - **Members:** Column A (First Name) & Column B (Last Name).
    - **In App Lists (Single Combined Workbook with 4 Worksheets):**
      - **Locations Worksheet:** Cell A1 = Inputbox Label, Cells A2:A20 = Theatre Locations, Cells A21:A30 = Warehouse Locations.
      - **Roles Worksheet:** Cell A1 = Inputbox Label, Cells A2:A20 = Theatre Roles, Cells A21:A30 = Warehouse Roles.
      - **Questions Worksheet (No header row):** Column B (Cells B1–B14: Last Person Sign-Out Checklist Text, Cells B15–B16: Reminder Pop-Up Message, Cell B17: Theatre Meeting Duration Hours `1-24`, Cell B18: Warehouse Meeting Duration Hours `1-24`).
      - **Titles Worksheet:** Column B (Cell B2: Default Play Title / Main Header, Cell B3: Left Panel Heading, Cell B4: Right Panel Heading, Cell B5: Screen Saver Message).
  - **Modes (Loc'n 2 Enable & Meeting Mode):** In Admin under "Modes", toggle **Loc'n 2 Enable** or **Meeting Mode** (mutually exclusive).
    - In Loc'n 2 mode, active Locations and Roles are loaded automatically from Cells A21:A30 of the Locations/Roles worksheets. The main screen push button cycles through 3 steps: **🎭 #1** (0) ➔ **🎭 #2** (1) ➔ **👥 Meeting** (2, with groups icon and bright blue background).
    - In Normal mode, active Locations and Roles are loaded from Cells A2:A20. Toggling Meeting mode displays the meeting push button (Off state is 2 shades lighter than On state).
    - **Remote Meeting Durations (Cells B17 & B18):** Loc'n 1 meeting duration (`1-24` hours) is controlled remotely by cell **B17**, and Loc'n 2 meeting duration (`1-24` hours) is controlled remotely by cell **B18** in the Questions sheet of "In App Lists". Both values are displayed as `Loc'n 1:` and `Loc'n 2:` indicators on the Admin screen (`Duration: Questions B17 & B18`). Meeting mode automatically deactivates after the respective configured hours have elapsed since activation.
    - **Admin Logging & Change Detection:** When "In App Lists" is updated or changed, background observers re-read all 4 worksheets and record an Admin Log entry detailing the worksheet change.
    - **"Signed in Cast member is King"**: If any cast member is checked in (`stageActive`), the Meeting button is automatically disabled in both modes.
  - **Automated Reset:** If Row 2 of the Cast file has a date that has passed (evaluated at the scheduled Auto Signout Time, default 04:00), the app automatically resets the Cast list and title.

- **[Automation, Logs & Retention]**
  - **Daily Maintenance & Auto Signout:** Scheduled at your choice (default **04:00**) to sign out everyone and refresh databases.
  - **Log Save Folder Subfolders:** All external files are organized into dedicated subfolders:
    - **`Records/Records_YYYY-MM-DD.txt`**: Daily attendance records for the operational 24-hour day (04:01 AM to 04:00 AM next day).
    - **`Admin Logs/Admin_logs_YYYY-MM-DD.txt`**: System and administrative event logs.
  - **Log File Retention & Storage:** 
    - **Retention**: Admin logs and sign-in records are retained for **30 days** (older files are pruned).
    - **Storage Consumption:** Extremely lightweight, consuming **less than 20 MB total**.
  - **Unselected Log Folder Behavior:** If no log save folder is selected, core sign-in, database loading/saving, and local in-app history operate normally; external text log files and daily export files are simply skipped until a folder is designated.
  - **Display Over Other Apps (Auto-Start):** Required for automatic startup over the lock screen. If grayed out on Android 11+, tap the top-right three dots (`⋮`) and select **"Allow restricted settings"** first.
  - **Tablet Auto-Restart & Battery Optimization Note:** On certain tablets (Samsung, Lenovo, etc.), OEM battery management may terminate apps launched automatically via boot receivers. To ensure the app stays running after reboot, go to **Settings > Apps > Sign-In v4.3 > Battery**, set it to **"Unrestricted"**, and enable **Auto-start / Allow background activity** in device care.

---

## Technical Information for Developers

- **Framework:** Jetpack Compose (Modern Android UI).
- **Architecture:** `Repository.kt` as Single Source of Truth.
- **Persistence:** All databases are persisted locally in JSON format to ensure offline availability.
- **Excel Processing:** Powered by **Apache POI**. All data files are expected in Documents.
- **Footer Branding:** Created by Stu Sorrell, v4.3.2026.10
