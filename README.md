# Disaster Response & Resource Deployment System

## Overview

The **Disaster Response & Resource Deployment System** is a Java-based application designed to help authorities manage disaster incidents efficiently.
The system collects incident reports, analyzes historical disaster patterns, and suggests rescue team deployment.

## Features

* Report disaster incidents
* View available rescue teams
* Analyze historical disaster patterns
* Generate disaster risk predictions
* Admin panel for managing locations and teams
* Disaster alert generation

## Technologies Used

* Java (Swing for UI)
* MySQL Database
* JDBC
* Eclipse IDE
* Git & GitHub

## Modules

### 1. Report Incident

Users can report disasters with:

* Location
* Disaster type
* Severity level
* Reporter details
* Additional description

The system automatically generates an **alert and assigns the nearest rescue team**.

### 2. Rescue Teams

Displays all available rescue teams with:

* Team name
* Specialty
* Base location
* Contact number

### 3. History & Patterns

Shows historical disaster data and analyzes:

* Disaster frequency
* Seasonal patterns
* Risk levels

Generates predictions such as:

> High chance of flood in low-lying areas during monsoon.

### 4. Admin Panel

Allows administrators to:

* Manage village locations
* Add rescue teams
* Configure alerts
* View system logs

## Database Tables

* VillageLocations
* DisasterReports
* EmergencyTeams
* HistoricalData

## Future Improvements

* Real-time weather API integration
* SMS alert system
* Disaster heatmap visualization
* Mobile app integration

## Author

**Roshitha Bollineni**

B.Tech CSE 

Vignan's Foundation for Science, Technology & Research (VFSTR)
