// src/App.tsx
import React from "react";
import { BrowserRouter as Router, Routes, Route, Link, Navigate } from "react-router-dom";
import LookupPage from "./pages/LookupPage";
import PantryPage from "./pages/PantryPage";

const App: React.FC = () => {
  return (
    <Router>
      {/* Header / Nav */}
      <header className="p-4 bg-blue-600 text-white flex justify-between items-center">
        <h1 className="text-xl font-semibold">
          Pantry Manager
        </h1>
        {/* Simple navigation links */}
        <nav className="space-x-4 text-base">
          <Link to="/lookup" className="hover:underline">Lookup Product</Link>
          <Link to="/pantry" className="hover:underline">My Pantry</Link>
        </nav>
      </header>

      {/* Routes for pages */}
      <main className="p-4 max-w-3xl mx-auto">
        <Routes>
          <Route path="/" element={<Navigate to="/lookup" replace />} />
          <Route path="/lookup" element={<LookupPage />} />
          <Route path="/pantry" element={<PantryPage />} />
        </Routes>
      </main>
    </Router>
  );
};

export default App;
