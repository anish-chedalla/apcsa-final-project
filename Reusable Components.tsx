// src/components/ProductInfo.tsx (for displaying product details)
import React from "react";
import { Product } from "../api/api";

interface Props {
  product: Product;
}
const ProductInfo: React.FC<Props> = ({ product }) => (
  <div className="bg-gray-100 p-3 rounded">
    <h3 className="text-lg font-semibold">{product.name}</h3>
    <p><strong>Brand:</strong> {product.brand}</p>
    {product.expiration && <p><strong>Expiration:</strong> {product.expiration}</p>}
  </div>
);
export default ProductInfo;
// src/components/PantryItem.tsx (for a single pantry list item)
import React from "react";
import { Product } from "../api/api";

interface Props {
  item: Product;
  onRemove: (item: Product) => void;
}
const PantryItem: React.FC<Props> = ({ item, onRemove }) => (
  <li className="flex justify-between items-center bg-gray-50 border-b p-2">
    <div>
      <p className="font-medium">{item.name}</p>
      <p className="text-sm text-gray-700">
        Brand: {item.brand}
        {item.expiration && <> | Exp: {item.expiration}</>}
      </p>
    </div>
    <button onClick={() => onRemove(item)} className="text-red-600 hover:underline">
      Remove
    </button>
  </li>
);
export default PantryItem;
Reusable Components
