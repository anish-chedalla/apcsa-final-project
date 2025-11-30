// src/pages/PantryPage.tsx
import React, { useEffect, useState } from "react";
import { getPantryItems, getProductByBarcode, addPantryItem, removePantryItem, Product } from "../api/api";

const PantryPage: React.FC = () => {
  const [pantry, setPantry] = useState<Product[]>([]);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  // Form states for adding items
  const [newBarcode, setNewBarcode] = useState("");        // for adding via barcode
  const [manualName, setManualName] = useState("");
  const [manualBrand, setManualBrand] = useState("");
  const [manualExp, setManualExp] = useState("");

  useEffect(() => {
    // Fetch initial pantry items when component mounts
    const fetchItems = async () => {
      try {
        const items = await getPantryItems();
        setPantry(items);
      } catch (err: any) {
        console.error(err);
        setError("Failed to load pantry items.");
      } finally {
        setLoading(false);
      }
    };
    fetchItems();
  }, []);

  const handleRemove = async (item: Product) => {
    if (!item.id) return; // assuming each pantry item has an id field
    try {
      await removePantryItem(item.id);
      // Update state to remove the item from list
      setPantry(prev => prev.filter(p => p.id !== item.id));
    } catch (err: any) {
      console.error(err);
      setError("Failed to remove item. Please try again.");
    }
  };

  const handleAddByBarcode = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    if (!newBarcode.trim()) {
      setError("Enter a barcode to add.");
      return;
    }
    try {
      const product = await getProductByBarcode(newBarcode.trim());
      // Optionally, prompt user for expiration date here (not implemented for brevity)
      const addedItem = await addPantryItem({
        barcode: product.barcode,
        name: product.name,
        brand: product.brand,
        expiration: product.expiration  // if any (likely undefined by default)
      });
      // Update pantry list with the newly added item
      setPantry(prev => [...prev, addedItem]);
      // Clear the input field
      setNewBarcode("");
    } catch (err: any) {
      console.error(err);
      setError("Could not add item by barcode. " + (err.message || ""));
    }
  };

  const handleAddManual = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);
    // Basic validation for manual fields
    if (!manualName.trim() || !manualBrand.trim()) {
      setError("Name and brand are required for manual add.");
      return;
    }
    try {
      const addedItem = await addPantryItem({
        barcode: "manual",  // use a placeholder or generate a dummy barcode for manual items
        name: manualName.trim(),
        brand: manualBrand.trim(),
        expiration: manualExp.trim() || undefined
      });
      setPantry(prev => [...prev, addedItem]);
      // Clear manual input fields
      setManualName("");
      setManualBrand("");
      setManualExp("");
    } catch (err: any) {
      console.error(err);
      setError("Failed to add item. Please try again.");
    }
  };

  return (
    <section className="PantryPage">
      <h2 className="text-2xl font-bold mb-4">My Pantry</h2>

      {/* Loading and error states */}
      {loading && <p>Loading pantry items...</p>}
      {error && <p className="text-red-600">{error}</p>}

      {/* Pantry List */}
      {!loading && pantry.length === 0 && !error && (
        <p className="mb-4">Your pantry is empty. Add some items!</p>
      )}
      {!loading && pantry.length > 0 && (
        <ul className="mb-6">
          {pantry.map(item => (
            <li key={item.id || item.barcode} className="flex justify-between items-center bg-gray-50 border-b p-2">
              <div>
                <p className="font-medium">{item.name}</p>
                <p className="text-sm text-gray-700">Brand: {item.brand} {item.expiration && (
                  <> | Exp: {item.expiration}</>
                )}</p>
              </div>
              <button 
                onClick={() => handleRemove(item)} 
                className="text-red-600 hover:underline"
              >
                Remove
              </button>
            </li>
          ))}
        </ul>
      )}

      {/* Add by Barcode Form */}
      <div className="add-section mb-6">
        <h3 className="text-xl font-semibold mb-2">Add Item by Barcode</h3>
        <form onSubmit={handleAddByBarcode} className="flex flex-col sm:flex-row items-center mb-4">
          <label htmlFor="newBarcode" className="mr-2">Barcode:</label>
          <input 
            id="newBarcode"
            type="text" 
            value={newBarcode}
            onChange={e => setNewBarcode(e.target.value)}
            className="border border-gray-300 rounded p-2 mr-2 flex-1"
            placeholder="Enter barcode"
          />
          <button type="submit" className="mt-2 sm:mt-0 px-4 py-2 bg-green-700 text-white rounded">
            Add by Barcode
          </button>
        </form>
      </div>

      {/* Add Manual Form */}
      <div className="add-section mb-6">
        <h3 className="text-xl font-semibold mb-2">Add Item Manually</h3>
        <form onSubmit={handleAddManual} className="flex flex-col space-y-2 max-w-sm">
          <div className="flex flex-col">
            <label htmlFor="manualName">Name:</label>
            <input 
              id="manualName"
              type="text" 
              value={manualName}
              onChange={e => setManualName(e.target.value)}
              className="border border-gray-300 rounded p-1"
              placeholder="e.g. Rice"
              required 
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="manualBrand">Brand:</label>
            <input 
              id="manualBrand"
              type="text" 
              value={manualBrand}
              onChange={e => setManualBrand(e.target.value)}
              className="border border-gray-300 rounded p-1"
              placeholder="e.g. Generic or Brand name"
              required 
            />
          </div>
          <div className="flex flex-col">
            <label htmlFor="manualExp">Expiration Date (optional):</label>
            <input 
              id="manualExp"
              type="date" 
              value={manualExp}
              onChange={e => setManualExp(e.target.value)}
              className="border border-gray-300 rounded p-1"
            />
          </div>
          <button type="submit" className="mt-3 px-4 py-2 bg-blue-700 text-white rounded">
            Add Item
          </button>
        </form>
      </div>
    </section>
  );
};

export default PantryPage;
