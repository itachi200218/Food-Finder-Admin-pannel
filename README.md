# Food-Finder-API-Test

## 🛠️ Project Overview
**Food-Finder-API-Test** is an **API automation project** for testing the backend of the Food Finder Recipe Application. It is built using **Java, RestAssured, and TestNG**. The project uses a **reusable API class** to simplify testing multiple endpoints like recipe details, suggestions, categories, and chatbot integration.

---

## 💻 Tech Stack
- **Language:** Java  
- **Testing Framework:** TestNG  
- **API Automation:** RestAssured  
- **Utilities:** Reusable API class (`ApiResuable`) for GET and POST requests  
- **Tools:** JSON payloads for POST requests  

---

## ⚡ Features
- Automated testing of multiple API endpoints:  
  - `/get-recipe-detail` – fetches recipe details by ID  
  - `/get-recipes` – fetches recipes by category  
  - `/get-suggestions` – fetches recipe suggestions based on query  
  - `/chatbot-api` – tests chatbot functionality with prompt  
- Supports **dynamic payloads** and header customization.  
- Prints response **status code, endpoint, method, and body** for each request.  
- Efficient execution of multiple API requests using a reusable API class.

---


