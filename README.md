# SA Harvest — Beneficiary Direct Collection & Vetting Mobile App

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Android SDK](https://img.shields.io/badge/Android%20SDK-API%2035%20(Android%2015)-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Gradle](https://img.shields.io/badge/Gradle-8.9%20%7C%20AGP%208.7.3-02303A?style=for-the-badge&logo=gradle&logoColor=white)](https://gradle.org/)
[![Java](https://img.shields.io/badge/JDK-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://adoptium.net/)

> **A native Android mobile application demo designed for SA Harvest to streamline on-site Beneficiary Direct Food Collection, Community-Based Organisation (CBO) Compliance & Infrastructure Vetting, and Administrative Oversight.**

---

## 📌 Project Overview

This mobile application provides an end-to-end digital workflow for **SA Harvest**'s food rescue and redistribution ecosystem. It replaces error-prone paper workflows with an offline-capable, role-based mobile interface tailored for field operations across South Africa.

### Key Roles & Capabilities:
- **🚚 CBO Collector (Food Rescue Operations)**:
  - Log food collections with category breakdowns, weight (kg), and notes.
  - Capture digital recipient and donor signatures with timestamping.
  - Capture photographic proof of collection and delivery notes.
  - Offline collection logging with background and batch cloud synchronisation.
- **📋 Vetting Officer (VO — Compliance & Audit)**:
  - Perform structured multi-stage CBO on-site inspections (Storage, Food Safety, Logistics, Governance).
  - Score compliance criteria and capture mandatory visual evidence.
  - Submit audit reports directly for administrative evaluation.
- **🛡️ Admin Overview**:
  - Review and approve/reject pending CBO vetting submissions.
  - View real-time field team activities and collection reports.
  - Register new beneficiaries and manage team roles.



## 🚀 How to Run the Project Locally in Android Studio

Follow this step-by-step setup guide to clone, configure, build, and run the application on your development machine.

```
┌─────────────────┐     ┌─────────────────────┐     ┌────────────────────┐     ┌─────────────────┐
│ 1. Clone Repo   │ ──> │ 2. Open in Studio   │ ──> │ 3. Set JDK 17 & SDK│ ──> │ 4. Run on Device │
└─────────────────┘     └─────────────────────┘     └────────────────────┘     └─────────────────┘
```

---

### Step 0: Prerequisites Checklist

Before you begin, ensure you have the following installed on your workstation:
1. **Android Studio**: Android Studio Ladybug (2024.2+), Koala (2024.1+), or Hedgehog (2023.3+).  
   👉 [Download Android Studio](https://developer.android.com/studio)
2. **Java Development Kit (JDK)**: **JDK 17** (Android Studio includes bundled JetBrains Runtime 17).
3. **Git**: Installed and configured on your system terminal.
4. **Android SDK Platform 35**: Installed via Android Studio SDK Manager.

---

### Step 1: Clone the Repository

Open your terminal or command prompt and clone the repository using either **HTTPS** or **SSH**:

#### Option A: Clone via HTTPS
```bash
git clone https://github.com/ST10443409/INSY7315-Stein-Solutions---Mobile-App-UI-Demo.git
```

#### Option B: Clone via SSH
```bash
git clone git@github.com:ST10443409/INSY7315-Stein-Solutions---Mobile-App-UI-Demo.git
```

Navigate into the cloned project directory:
```bash
cd INSY7315-Stein-Solutions---Mobile-App-UI-Demo
```

---

### Step 2: Open the Project in Android Studio

1. Launch **Android Studio**.
2. From the Welcome Screen, click **Open** (or in the top menu bar, select `File` ➔ `Open...`).
3. Browse to the directory where you cloned the repository (`INSY7315-Stein-Solutions---Mobile-App-UI-Demo`).
4. Select the root folder and click **OK** / **Open**.
5. Wait for Android Studio to initialize the project and index the files.

---

### Step 3: Verify SDK & Gradle JDK 17 Configuration

Ensure Android Studio is using **JDK 17** and **Android SDK 35**:

#### 1. Set Gradle JDK to Java 17:
- On **Windows / Linux**: Go to `File` ➔ `Settings` (or `Ctrl + Alt + S`).
- On **macOS**: Go to `Android Studio` ➔ `Settings...` (or `Cmd + ,`).
- In the left sidebar, navigate to:  
  `Build, Execution, Deployment` ➔ `Build Tools` ➔ `Gradle`
- Under **Gradle JVM**, ensure **Java 17** (e.g. `Embedded JDK 17` or `corretto-17`) is selected.
- Click **Apply** and **OK**.

#### 2. Verify Android SDK 35 is Installed:
- Open `Tools` ➔ `SDK Manager` (or click the SDK Manager icon in the top-right toolbar).
- Under the **SDK Platforms** tab, ensure **Android 15.0 ("VanillaIceCream") / API Level 35** is checked.
- If not checked, check it and click **Apply** to download.

---

### Step 4: Sync Gradle Project

1. Click the **Sync Project with Gradle Files** button (the Elephant icon 🐘 in the top-right toolbar), or go to:  
   `File` ➔ `Sync Project with Gradle Files`.
2. Wait for Gradle sync to finish. You should see `BUILD SUCCESSFUL` in the bottom **Build** / **Sync** tab.

---

### Step 5: Set Up an Emulator or Physical Device

#### Option A: Use an Android Virtual Device (AVD Emulator)
1. Go to `Tools` ➔ `Device Manager`.
2. Click **Create Device** (`+`).
3. Select a Phone hardware profile (e.g., **Pixel 8** or **Pixel 7**).
4. Select a System Image with **API Level 35** (or minimum **API 26+**).
5. Click **Next** ➔ **Finish**, then click the **Play (▶)** button to start the emulator.

#### Option B: Use a Physical Android Device
1. On your phone, enable **Developer Options**:
   - Go to `Settings` ➔ `About Phone` ➔ Tap **Build Number** 7 times.
2. Go to `Settings` ➔ `Developer Options` ➔ Enable **USB Debugging**.
3. Connect your phone to your computer via a USB cable.
4. If prompted on your phone screen, tap **Allow USB debugging**.

---

### Step 6: Build & Run the Application

1. In the top toolbar, verify that:
   - Configuration is set to: **`app`**
   - Target device is set to your running **Emulator** or connected **Physical Device**.
2. Click the green **Run (▶)** button (or press `Shift + F10` on Windows/Linux, `Ctrl + R` on macOS).
3. Android Studio will compile the Kotlin Compose code, install the APK, and launch the application on the selected device.

---
### Step 7: How to navigate between roles 

1. On the launch of the app, there will be a landing page followed by a role selection page, each selection shows the prototypes and workflows for each role
2. How to get back to this page after going to a role workflow ? A: On the CBO Collection & Vetting Role navigate to the sync tab on the bottom nabber and press the 'return to role selection' button, on the Admin workflow navigate to the reports tab on the bottom navbar and press the 'return to role selection' button
---





## ❓ Troubleshooting & Common Issues

<details>
<summary><b>1. Gradle Sync Error: Incompatible Java version / JVM target</b></summary>

- **Cause**: Android Studio is pointing to JDK 8, 11, or 21 instead of JDK 17.
- **Solution**: Go to `Settings` ➔ `Build, Execution, Deployment` ➔ `Build Tools` ➔ `Gradle` ➔ Change **Gradle JDK** to **Java 17**.
</details>

<details>
<summary><b>2. Target SDK 35 / Build Tools Not Found</b></summary>

- **Cause**: Missing Android 15 (API 35) platform SDK on your local machine.
- **Solution**: Open `Tools` ➔ `SDK Manager` ➔ `SDK Platforms` ➔ Check **Android 15.0 ("VanillaIceCream")** ➔ Click **Apply** to install.
</details>

<details>
<summary><b>3. Unresolved Gradle Dependency or Build Cache Issue</b></summary>

- **Cause**: Corrupted Gradle cache or interrupted download.
- **Solution**:
  1. Click `File` ➔ `Invalidate Caches...` ➔ Check all boxes ➔ Click **Invalidate and Restart**.
  2. Run `./gradlew clean` (or `.\gradlew.bat clean`) in the terminal.
  3. Re-sync Gradle files.
</details>

<details>
<summary><b>4. Device Not Detected in Android Studio</b></summary>

- **Cause**: USB Debugging is not enabled, or ADB drivers are missing.
- **Solution**:
  - Re-plug the USB cable and select **Transfer files / MTP** mode on your device.
  - Ensure **USB Debugging** is toggled ON under Developer Options.
  - Restart the ADB server:
    ```bash
    adb kill-server
    adb start-server
    ```
</details>

---

## 👥 Contributors & Acknowledgements

Developed as part of the **INSY7315 Work Integrated Learning (WIL)** project for **SA Harvest** by Stein Solutions.
