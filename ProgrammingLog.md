# 📜 Barry2026 Programming Log

This document tracks the software updates per commit of each day worked on the Barry2026 robot

\* The ⭐ next to a commit means that a major change was pushed there
\* Commits updating this ProgrammingLog.md are not recorded

---

## May 24, 2026

**Day Notes:** Just created the project today, I can't wait to see how far this goes in the future!

### 🛠️ "Initial commit" (`60bd6ea6538e3b1dcbd25c46f91b1941eb89246f`) ⭐
* **Goal:** Start the project!
* **Process:** Went to the release AdvantageKit folders on the Mechanical-Advantage Github and extracted the zip into this repository
* **Etc:** Here is the link to download the advantage kit package: https://github.com/Mechanical-Advantage/AdvantageKit/releases

---

## May 25, 2026

**Day Notes:** Mostly worked on creating documentation files (ReadMes)

### 🛠️ "added a file for build logging and started the readme" (`e837ba7886294051d24e539ade23f12996c71d27`)
* **Goal:** Well this one is a bit self explanatory, but I made 2 ReadMe files, one for the ReadMe of the project as a general tab and the other to log the CAD process of the supplementary CAD I'm making in the background
* **Process:** Simply made 2 ReadMe files

### 🛠️ "added images to build log and read me files" (`7c68983596f9cebfb0dd0e94aae93f63ac564fe8`)
* **Goal:** I wanted to show pictures of the CAD in the newly created CAD log and ReadMe.
* **Process:** Added an image folder in the cad folder to store screenshots of the CAD with the one named current simply being a copy of the latest CAD screenshot.

### 🛠️ "uncommented lines 39-42 on build.gradle for optimization for roborio2" (`2579c71fd1fd16c138358bfaa3dfe2d1558a784a`)
* **Goal:** Follow the steps in setting up this project from AdvantageKit (as this is from AdvantageKit)
* **Process:** In the setup tutorial the only real change (besides setting the team number) before needing an actual robot was uncommenting lines to boost the performance on a Roborio 2 that were commented since some teams do still use the Roborio 1

### 🛠️ "fixed image not showing in readme" (`2bffacf157bccd4eb7551c6bb27ed17d175657c3`)
* **Goal:** Make the images actually show in github
* **Process:** There was just a small error in making the images show on the ReadMes in implementation

---

## May 26, 2026

**Day Notes:** Mostly worked on adding CAD files into the AdvantageScope

### 🛠️ "added the starting cad files in the code as well as the code to add components smoothly" (`74c4ea2fa9c79f0d359cc6cb0dd42e32fb8012f0`)
* **Goal:** Make it so I could see the CAD files in Advantage Scope
* **Process:** Downloaded the main Assembly as a glTF file and sent it to the CAD Assistant app to be transformed into a glb file to be used in AdvantageScope. Moving parts have to be different glb files, so I split the intake here and in the CAD for now to test movement. I also made the folder for the robot CAD with a config.json file which still needs to be tuned. This was still the first version of the robot CAD so it is very subject to change.

### 🛠️ "put offsets so that the intake is in the right place on the robot base in advantage scope" (`69db6094bb237a067d8937cbf193d9fbc4fd2fd3`)
* **Goal:** Make the intake attached to the robot in AdvantageScope
* **Process:** I adjusted the angle and position of the intake component in the model config of the robot CAD folder so that it was in the correct position seen in the original CAD

### 🛠️ "redid the config cad constants to work now" (`8db4e24dda56cbb4d3a45f3dc335f357107a5b1d`)
* **Goal:** I have to redo the offsets for the placement of the intake component after the one from the last commit was off by a bit (it changed after reloading)
* **Process:** Same as the last commits

---

## May 28, 2026

**Day Notes:** Worked on the intake code today and implementing it in Advantage Scope

### 🛠️ "Added intial intake code" (`a39adf890fa6d12aec6a211354d433507560fff7`) ⭐
* **Goal:** Add code for the intake and make it compatible with AdvantageScope
* **Process:** Started with the constants of Larry26 and worked off of there using team 5000's Falcon FX code as reference for the difference from REV.
* **Etc:** For programming reference with the Falcon FX and the general structure I looked at repos from the past robot, 1257 (https://github.com/FRC1257/2026-Robot/tree/master), & 5000 (https://github.com/hammerheads5000/2026Rebuilt/blob/main/src/main/java/frc/robot/subsystems/intake/IntakeIOTalonFX.java)

### 🛠️ "added simple PIDs (not fully tuned) for the intake and now it works in simulation" (`667e5301fbde0b47432aa0cee86b41e827b5c09b`) ⭐
* **Goal:** Make it so that pressing the pov left and right buttons on the x-box controller moves the intake pivot up and down with limits at 95 degrees up from the chassis horizontal and at the horizontal which is similar to the bounds of the intake pivot in real life
* **Process:** Implemented some PIDs which aren't fully tuned, but work for making sure the intake is in the right position. For stopping the arm I simply made it set the position to its own current position since pressing the pov left or right buttons sets a speed on the pivot not a position which locks the motors.