🧑‍💼 Food Finder – Admin Panel (Spring Boot + Java)
🚀 Overview

The Admin Panel of the Food Finder system is a Spring Boot–based backend designed for high-performance recipe management, analytics, and intelligent automation using Gemini AI.
It empowers administrators to manage users, recipes, and system statistics seamlessly — all while supporting AI-driven natural commands for database operations.

⚙️ Tech Stack
Layer	Technology
Backend Framework	Spring Boot (Java)
API Testing	RestAssured + TestNG
Reporting	Allure Reports
Database	MySQL
AI Integration	Gemini AI
Caching	Redis
Others	Maven, JUnit
🧩 Core Features
👥 User Management

Secure authentication and password protection

Admin-only access control

User registration, update, and deletion

Forgot/Reset password modules

Role-based access handling

🍳 Recipe Operations

Complete CRUD (Create, Read, Update, Delete) support

Category-based filtering (Veg, Non-Veg, Tiffins, etc.)

Image metadata support

Bulk add/edit/delete options for admin efficiency

📊 Analytics Dashboard

Displays total users, top categories, and most active users

Detects duplicate users in database

Tracks and visualizes system usage metrics

Provides real-time stats on user engagement and category trends

🤖 AI Assistant (Gemini Integration)

The AI module allows admins to execute natural language commands to control the backend directly.

🧠 Example Commands
"create recipe pasta at category 1"
→ AI generates full recipe with ingredients → Saves to DB automatically

"delete recipe Veg Fried Rice category 1"
→ AI interprets → Validates → Deletes → Confirms


Other capabilities:

Generate multi-language code (Java, Python, JS)

Auto-create Excel reports of recipes and users

Explain database schema and system architecture

Understand conversational queries (e.g., “show top 5 non-veg recipes”)
