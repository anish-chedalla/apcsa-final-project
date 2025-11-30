// src/pages/LookupPage.tsx
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { getProductByBarcode, addPantryItem, Product } from "../api/api";

const LookupPage: React.FC = () => {
  const [barcode, setBarcode] = useState("");
  const [product, setProduct] = useState<Product | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLookup = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    setProduct(null);
    // Basic validation: barcode should be non-empty and numeric
    if (!barcode.trim()) {
      setError("Please enter a barcode.");
      return;
    }
    setLoading(true);
    try {
      const result = await getProductByBarcode(barcode.trim());
      setProduct(result);
    } catch (err: any) {
      // If product not found or fetch error
      setError(err.message || "Product not found.");
    } finally {
      setLoading(false);
    }
  };

  const handleAddToPantry = async () => {
    if (!product) return;
    try {
      // When adding to pantry, allow user to specify expiration if needed.
      // For simplicity, we use the product info as is.
      await addPantryItem({
        barcode: product.barcode,
        name: product.name,
        brand: product.brand,
        expiration: product.expiration // (likely undefined here unless set elsewhere)
      });
      alert("Item added to pantry!");
      navigate("/pantry"); // navigate to pantry page to view the updated list
    } catch (err: any) {
      console.error(err);
      setError("Failed to add item to pantry.");
    }
  };

  return (
    <section className="LookupPage">
      <h2 className="text-2xl font-bold mb-4">Lookup Product by Barcode</h2>
      {/* Barcode input form */}
      <form onSubmit={handleLookup} className="flex flex-col sm:flex-row items-center mb-4">
        <label htmlFor="barcode" className="mr-2 font-medium">Barcode:</label>
        <input
          id="barcode"
          type="text"
          value={barcode}
          onChange={e => setBarcode(e.target.value)}
          className="border border-gray-300 rounded p-2 mr-2 flex-1"
          placeholder="Enter or scan barcode"
        />
        <button 
          type="submit" 
          disabled={loading}
          className="mt-2 sm:mt-0 px-4 py-2 bg-blue-600 text-white rounded disabled:opacity-50"
        >
          {loading ? "Looking up..." : "Lookup"}
        </button>
      </form>

      {/* Error message */}
      {error && <p className="text-red-600 mb-3">{error}</p>}

      {/* Display product info if available */}
      {product && !error && (
        <div className="product-info bg-gray-100 p-4 rounded shadow-sm">
          <h3 className="text-xl font-semibold mb-1">{product.name}</h3>
          <p className="mb-1"><strong>Brand:</strong> {product.brand}</p>
          {/* Only display expiration if the product object has it */}
          {product.expiration && (
            <p className="mb-1"><strong>Expiration:</strong> {product.expiration}</p>
          )}
          {/* "Add to Pantry" button */}
          <button 
            onClick={handleAddToPantry} 
            className="mt-3 px-3 py-1 bg-green-600 text-white rounded"
          >
            Add to Pantry
          </button>
        </div>
      )}
    </section>
  );
};

export default LookupPage;
