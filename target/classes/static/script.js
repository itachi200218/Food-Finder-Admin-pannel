// ✅ Check if user is logged in before doing anything
async function checkLogin() {
  try {
    const response = await fetch("http://localhost:8080/api/auth/check", {
      method: "GET",
      credentials: "include" // ✅ keeps session cookie
    });

    const isLoggedIn = await response.json();
    if (!isLoggedIn) {
      // ❌ Not logged in → redirect immediately
      window.location.href = "Auth.html";
    } else {
      // ✅ Logged in → load recipes now
      fetchRecipes();
    }
  } catch (error) {
    console.error("Error checking login:", error);
    window.location.href = "Auth.html"; // any error → redirect
  }
}

// ✅ Only run this after page loads
document.addEventListener("DOMContentLoaded", checkLogin);

const API_URL = "http://localhost:8080/api/users";

// ✅ Fetch all recipes (only runs after successful login)
async function fetchRecipes() {
  try {
    const res = await fetch(API_URL, { credentials: "include" });
    if (!res.ok) throw new Error("Failed to fetch recipes");
    const data = await res.json();
    displayRecipes(data);
  } catch (err) {
    console.error("Error fetching recipes:", err);
  }
}

// ✅ Display recipes
function displayRecipes(recipes) {
  const list = document.getElementById("recipeList");
  list.innerHTML = "";

  if (!recipes || recipes.length === 0) {
    list.innerHTML = "<p>No recipes found.</p>";
    return;
  }

  recipes.forEach((r) => {
    const li = document.createElement("li");
    li.className = "recipe-item";
    li.innerHTML = `
      <div class="recipe-card">
        <h3>${r.name}</h3>
        <p><strong>Description:</strong> ${r.description || "N/A"}</p>
        <p><strong>Ingredients:</strong> ${r.ingredients || "N/A"}</p>
        <p><strong>Steps:</strong> ${r.steps || "N/A"}</p>
        <p><strong>Category ID:</strong> ${r.categoryId}</p>
        ${r.url ? `<img src="${r.url}" alt="${r.name}" class="recipe-image" />` : ""}
        <div class="btn-group">
          <button class="edit-btn" onclick="editRecipe(${r.id})">Edit</button>
          <button class="delete-btn" onclick="deleteRecipe(${r.id})">Delete</button>
        </div>
      </div>
    `;
    list.appendChild(li);
  });
}

// ✅ Add / Update recipe
document.getElementById("recipeForm").addEventListener("submit", async (e) => {
  e.preventDefault();

  const id = document.getElementById("recipeId").value;
  const recipe = {
    name: document.getElementById("name").value,
    description: document.getElementById("description").value,
    ingredients: document.getElementById("ingredients").value,
    steps: document.getElementById("steps").value,
    url: document.getElementById("url").value,
    categoryId: document.getElementById("categoryId").value,
  };

  const method = id ? "PUT" : "POST";
  const endpoint = id ? `${API_URL}/${id}` : API_URL;

  await fetch(endpoint, {
    method,
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(recipe),
    credentials: "include"
  });

  alert(id ? "Recipe updated!" : "Recipe added!");
  document.getElementById("recipeForm").reset();
  fetchRecipes();
});

// ✅ Edit recipe
async function editRecipe(id) {
  const res = await fetch(`${API_URL}/${id}`, { credentials: "include" });
  const r = await res.json();
  document.getElementById("recipeId").value = r.id;
  document.getElementById("name").value = r.name;
  document.getElementById("description").value = r.description;
  document.getElementById("ingredients").value = r.ingredients;
  document.getElementById("steps").value = r.steps;
  document.getElementById("url").value = r.url;
  document.getElementById("categoryId").value = r.categoryId;
}

// ✅ Delete recipe
async function deleteRecipe(id) {
  if (confirm("Are you sure you want to delete this recipe?")) {
    await fetch(`${API_URL}/${id}`, { method: "DELETE", credentials: "include" });
    alert("Recipe deleted!");
    fetchRecipes();
  }
}

// ✅ Filter by category
async function filterCategory(categoryId) {
  const res = await fetch(`${API_URL}/category/id/${categoryId}`, { credentials: "include" });
  const data = await res.json();
  displayRecipes(data);
}

// ✅ Search recipe
async function searchRecipe() {
  const term = document.getElementById("search").value.toLowerCase();
  const res = await fetch(API_URL, { credentials: "include" });
  const data = await res.json();
  const filtered = data.filter((r) => r.name.toLowerCase().includes(term));
  displayRecipes(filtered);
}
