# Sign-In Application v4.3 - Professional Documentation

## Table of Contents
1. Overview
2. System Requirements
3. Installation and Setup
4. User Interface Overview
5. Core Functionality & Safeguards
6. Administrative Features & Customization
7. Visual Color Coding & High-Contrast Text
8. Data Management & File Layouts (Cast, Roles, Locations, Warehouse, Meeting)
9. Special Input Codes
10. Log Retention & Maintenance
11. Battery Monitoring System & Email Test
12. Technical Specifications

---

## 1. Overview

Sign-In v4.3 is a robust digital attendance system designed for professional environments like theatre companies and offices. It features real-time tracking, full offline persistence, automated data management via synced Excel files, custom theme color engine, Loc'n 2 3-step cycle mode, separate remote-controlled Meeting timers for Loc'n 1 (cell B17) and Loc'n 2 (cell B18), operational 24-hour day record saving (04:01 AM to 04:00 AM next day), subfolder log organization (`Records/` and `Admin Logs/`), 30-day strict retention, real-time MediaScanner cloud-sync notifications, robust sign-in safeguards, an advanced automated battery monitoring system, and an integrated SMTP **EMAIL TEST** utility.

---

## 2. System Requirements
- **Device**: Android tablet (API 29+ / Android 10+).
- **Storage**: Minimum 500MB available.
- **Sync**: A helper app (e.g., DriveSync) is recommended to keep local files synchronized with Google Drive.

---

## 3. Installation and Setup

### Administrative Access
- **Default PIN**: 1925
- **Admin Access Method**: Type your 4-digit PIN (default **1925**) into the **Name** input box on the main screen to open the Admin area.

### Auto-Start & Display Over Other Apps Permission
- To allow the app to automatically launch and display over the lock screen upon device startup, the **"Display over other apps"** permission is required.
- **Restricted Settings Override**: On Android 11+ tablets where the permission switch appears grayed out or disabled:
  1. Tap the **three dots (`⋮`)** at the top right corner of the Android settings screen.
  2. Select **"Allow restricted settings"**.
  3. Return to the settings page and enable the permission toggle for Sign-In v4.3.

### Tablet Auto-Restart & Battery Optimization Note
- On certain tablets (Samsung, Lenovo, etc.), OEM background process managers may terminate apps launched automatically via boot receivers. 
- **Action**: Go to **Settings > Apps > Sign-In v4.3 > Battery**, set it to **"Unrestricted"**, and enable **Auto-start / Allow background activity** in device settings.

---

## 4. User Interface Overview
- **Header**: Minimalist design with top corners left empty. 
- **Dual Panels**: Visitors/Members on the Left, Cast list on the Right.
- **Instruction Block**: Simplified sign-in and sign-out guidance.
- **Next Sync Indicator**: Discreet timestamp positioned safely below the system status bar (`padding(top = 45.dp, end = 12.dp)`), displaying **"Next Sync: [HH:mm]"** at 11.sp font size.
- **Admin Indicators**: Clean remote-status indicators (Theatre duration loaded from cell B17 and Warehouse duration from cell B18).

---

## 5. Core Functionality & Safeguards

### 5.1 Sign-In, Out & Duplicate Prevention
- **Predictions**: Start typing a name to see matches from your "Members" database.
- **Duplicate Safeguard**: If a person attempts to sign in while already checked in, an **"Already Signed In"** alert dialog pops up and the name box automatically resets.
- **Guest Rules**: New guests must enter a first and last name (AA AAA format).
- **Vehicle Reg**: Remembers your car registration and shows it on your attendance tile.
- **Last Person Out**: Automatically shows a checklist to the final person signing out.

---

## 6. Administrative Features & Customization

### 6.1 Theme Color Customization
- Go to **Admin > Appearance**, tap **Click to change** on the right of the tile, and use the built-in **RGB Color Sliders** engine to choose any custom theme color.
- **Automatic High-Contrast Text**: Text elements (headers, titles, instructions) automatically switch to crisp white on dark backgrounds and black on light backgrounds based on perceptual luminance.

### 6.2 Modes (Loc'n 2 Enable & Meeting Mode)
- In Admin under **Modes**, toggle **Loc'n 2 Enable** or **Meeting Mode** (mutually exclusive).
- **Loc'n 2 3-Step Button Cycle**: Cycles through **🎭 #1** (0) ➔ **🎭 #2** (1) ➔ **👥 Meeting** (2, displaying meeting icon and bright blue background).
- **Meeting Mode & Remote Durations (Cells B17 & B18)**: Loc'n 1 meeting duration (`1-24` hours) is controlled remotely by cell **B17**, and Loc'n 2 meeting duration (`1-24` hours) is controlled remotely by cell **B18** in the Questions sheet of the Settings workbook. Both values are displayed as `Loc'n 1:` and `Loc'n 2:` indicators on the Admin screen (`Duration: Questions B17 & B18`). Meeting mode automatically deactivates after the respective configured hours have elapsed since activation.
- **"Signed in Cast Member is King"**: If any cast member is checked in (`stageActive`), the Meeting button is automatically disabled in both modes.

### 6.3 Email Test Utility in Admin
- Located in the bottom action bar of the Admin screen (yellow **EMAIL TEST** button).
- Tapping this button reads the target email address from the **Battery** worksheet (Cell B2) of the uploaded **Settings** workbook (with fallback to question line index 9) and triggers an immediate background SMTP test email (`"Sign-In App Test Email"`) to verify SMTP credentials and server connectivity.

---

## 7. Visual Color Coding
- **#D7EFF9 (Light Blue)**: Guest sign-ins.
- **#BEE9AD (Light Green)**: Roles and Active Cast members.
- **#DAF2D0 (Light Sage Green)**: Locations.
- **#FD958D (Red)**: Inactive/Absent Cast members (pinned to the top).

---

## 8. Data Management & File Layouts

All files must be in Excel (`.xlsx`) format. In Admin, files are uploaded via 3 large, equal-width buttons: **"Cast"**, **"Members"**, and **"Settings"**.

### Cast File (RH Side)
- **Worksheet Names**: `Location #1` (Loc'n 1 / Theatre), `Location #2 - A` (Loc'n 2 Play A / Rehearsals), `Location #2 - B` (Loc'n 2 Play B / Youth).
- **Row 1**: Play Title.
- **Row 2**: **Last Performance Date** (dd/mm/yyyy format). If today is after this date, the list resets automatically during maintenance (default 04:00 AM).
- **Row 3+**: Names.

### Members File
- **Column A**: First Name.
- **Column B**: Last Name.
*(The app automatically sorts members alphabetically and computes the dynamic midpoint split letter for Dark mode panels).*

### Settings File (Single Combined Workbook with Worksheets)
- **Locations Worksheet**:
  - **Cell A1**: Inputbox Label.
  - **Cells A2:A20**: Theatre Locations.
  - **Cells A21:A30**: Warehouse Locations.
- **Roles Worksheet**:
  - **Cell A1**: Inputbox Label.
  - **Cells A2:A20**: Theatre Roles.
  - **Cells A21:A30**: Warehouse Roles.
- **Questions Worksheet** *(No header row)*:
  - **Cells B1–B14**: Last Person Sign-Out Checklist Text (up to 14 checklist questions).
  - **Cells B15–B16**: Reminder Pop-Up Message (Line 1 = B15, Line 2 = B16).
  - **Cell B17**: Theatre Meeting Duration hours (`1–24`).
  - **Cell B18**: Warehouse Meeting Duration hours (`1–24`).
- **Titles Worksheet**:
  - **Cell B2**: Default Play Title / Main Screen Header.
  - **Cell B3**: Left Panel Heading.
  - **Cell B4**: Right Panel Heading.
  - **Cell B5**: Screen Saver Message.
- **Battery Worksheet**:
  - **Cell B2**: Email Address Override (`batteryEmailAddressOverride`).
  - **Cell B3**: Admin Log Level (`batteryAdminLogLevel`, default `30`%).
  - **Cell B4**: Critical Email Level (`batteryEmailLevel`, default `12`%).
  - **Cell B5**: SMTP Host (`smtpHost`).
  - **Cell B6**: SMTP Port (`smtpPort`).
  - **Cell B7**: SMTP Username (`smtpUser`).
  - **Cell B8**: SMTP Password (`smtpPass`).

*When "Settings" is updated, change detection reloads all worksheets simultaneously and logs an Admin Event reporting the worksheet changes.*

---

## 9. Special Input Codes
Entered into the **Name** input box:
- **`##`**: Performs a full reset of the Cast list and restores the Default Title.
- **`@@`**: Activates the Screen Saver immediately.
- **`PIN`**: Opens the Administrative area directly when entered into the Name box.

---

## 10. Log Retention & Maintenance
- **Subfolder File Organization**: All external files are cleanly organized into dedicated subfolders inside your designated **Log Save Folder**:
  - **`[Log Save Folder]/Records/Records_YYYY-MM-DD.txt`**: Sign-in/out records for each operational 24-hour day (04:01 AM to 04:00 AM next day).
  - **`[Log Save Folder]/Admin Logs/Admin_logs_YYYY-MM-DD.txt`**: Administrative event logs (PIN changes, settings saves, file updates).
- **Log Retention**: Both attendance records and admin logs are retained for **30 days** (older text files are automatically pruned).
- **Storage Consumption**: Extremely lightweight, consuming **less than 20 MB total**.
- **Unselected Log Folder Behavior**: If no log save folder is selected in Admin, core sign-in, database loading/saving, and local history operate normally; external text log files and daily export files are simply skipped until a folder is designated.

---

## 11. Battery Monitoring System & Email Test

Sign-In v4.3 features an advanced, real-time battery monitoring system engineered specifically for continuous-operation sign-in tablets. It continuously tracks battery percentage and charging state via Android's `BroadcastReceiver` (`Intent.ACTION_BATTERY_CHANGED`), supports dynamic configuration via the **'Battery' worksheet in the Settings Excel workbook**, logs administrative warnings, displays prominent color-coded UI warning banners, and automatically dispatches critical SMTP warning emails when power levels drop below safe operating thresholds.

### 11.1 Configuration & Admin Control
- **Admin Settings Toggle**: In the Admin area under the **Battery Monitoring** section, battery monitoring can be enabled or disabled instantly via a toggle switch (`batteryMonitoringEnabled`).
- **Settings Excel Workbook ('Battery' Worksheet Integration)**: When uploading or updating the **Settings** Excel workbook, the app automatically processes the **'Battery' worksheet** (or any worksheet matching the keyword "battery") to configure thresholds and SMTP parameters:
  - **Cell B2**: Email Address Override (`batteryEmailAddressOverride`). If blank, the app falls back to question line index 9 (Questions sheet).
  - **Cell B3**: Admin Log Level (`batteryAdminLogLevel`, default `30`%).
  - **Cell B4**: Critical Email Level (`batteryEmailLevel`, default `12`%).
  - **Cell B5**: SMTP Host (`smtpHost`).
  - **Cell B6**: SMTP Port (`smtpPort`, e.g., 587 or 465).
  - **Cell B7**: SMTP Username (`smtpUser`).
  - **Cell B8**: SMTP Password (`smtpPass`).

### 11.2 Alert Levels & Operational Logic
When battery monitoring is enabled and the tablet is running on battery (`!isPluggedIn`), the app evaluates the battery level against three distinct severity tiers:

1. **Level 1 (Admin Log Warning / Battery ≤ Admin Level, e.g., 30%)**:
   - When the battery drops to or below `batteryAdminLogLevel`, the app logs a warning message to Logcat (`SignInBattery`) and records an administrative event log (`Repository.logAdminEvent`) noting the hourly status.
   - **Throttling**: Log entries are throttled to at least 1-hour intervals (`lastBatteryLogTime`) to prevent log flooding.

2. **Level 2 (Critical Threshold / Battery ≤ Admin Level - 10, e.g., 20%)**:
   - Displays a prominent yellow warning banner (`BatteryStatusBanner`) near the top of the main sign-in screen reading: `"Battery Level at [X]% - Check Connections"`.

3. **Level 3 (Critical Email Level / Battery ≤ Email Level, e.g., 12%)**:
   - Displays a prominent yellow banner with a red border and bold red text: `"Battery Level at [X]% - Check Connections, Tech Contacted"`.
   - Automatically dispatches a background SMTP email (`Repository.sendBackgroundEmail`) to the designated recipient with the subject `"[Mode Name] Sign-In Tablet Battery Critical [X]%"` and a body notifying that the tablet battery is critically low and may shut down soon.

### 11.3 Email Test Button in Admin
- The Admin screen includes a dedicated yellow **EMAIL TEST** button in the bottom action bar.
- Tapping this button reads the target recipient from Cell B2 of the **Battery** worksheet in the uploaded **Settings** workbook and sends an immediate background test email (`"Sign-In App Test Email"`) to verify SMTP host, port, authentication credentials, and network connectivity.

### 11.4 Power Restoration & Charging Behavior
- When the tablet is plugged into external power (`isPluggedIn == true`), all active battery warning banners are hidden, and the internal warning triggers (`adminLogSentForLevel1`, `emailSentForLevel3`) are automatically reset so future discharge cycles will trigger notifications correctly.

### 11.5 OEM Battery Optimization Note
- On certain hardware tablets (Samsung, Lenovo, etc.), OEM background process managers may aggressively terminate background tasks or prevent boot receivers from launching.
- **Mandatory Configuration**: Go to **Settings > Apps > Sign-In v4.3 > Battery**, set the battery optimization mode to **"Unrestricted"**, and enable **Auto-start / Allow background activity** in device care settings.

---

## 12. Technical Specifications
- **Framework**: Kotlin / Jetpack Compose.
- **Versioning**: v4.3 - October 2026 Release.
- **Storage**: Full JSON local persistence for all databases.
- **Excel Library**: Apache POI.
- **Background Work**: Android WorkManager for 4-hour refreshes/saves and daily maintenance at 04:00 AM.
- **Admin Footer Branding**: Created by Stu Sorrell, v4.3.2026.10
