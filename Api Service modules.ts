// src/api/api.ts
const API_BASE = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080/api"; 
// ^ Assume the backend is hosted here and CORS is enabled for frontend's domain.

export interface Product {
  id?: number;            // product or pantry item ID (if applicable)
  barcode: string;
  name: string;
  brand: string;
  expiration?: string;    // optional expiration date (ISO string or simple date string)
}

// Fetch product info by barcode using the FoodAPI backend endpoint
export async function getProductByBarcode(barcode: string): Promise<Product> {
  const res = await fetch(`${API_BASE}/products/${barcode}`);
  if (!res.ok) {
    // If the product is not found, backend might return 404
    throw new Error(`Product lookup failed with status ${res.status}`);
  }
  const data = await res.json();
  return data as Product;
}

// Get all pantry items (for the current user or global pantry)
export async function getPantryItems(): Promise<Product[]> {
  const res = await fetch(`${API_BASE}/pantry`);
  if (!res.ok) {
    throw new Error("Failed to fetch pantry items");
  }
  return res.json() as Promise<Product[]>;
}

// Add a new item to pantry. 
// If adding via barcode, we might call getProductByBarcode first, then send that info.
export async function addPantryItem(item: Omit<Product, "id">): Promise<Product> {
  const res = await fetch(`${API_BASE}/pantry`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(item)
  });
  if (!res.ok) {
    throw new Error("Failed to add item to pantry");
  }
  return res.json() as Promise<Product>;
}

// Remove an item from pantry by ID (or by barcode if ID not used)
export async function removePantryItem(itemId: number): Promise<void> {
  const res = await fetch(`${API_BASE}/pantry/${itemId}`, { method: "DELETE" });
  if (!res.ok) {
    throw new Error("Failed to remove item");
  }
  // No content expected on success (204), just return void
}
