# APCSA Final Project - Barcode Product Lookup

A Java application that uses the OpenFoodFacts API to retrieve product information from barcodes. App is used to inform food safety.

## Overview

This project provides a simple command-line interface for looking up product details (name, brand, etc.) using barcode data. The application makes a single API call to OpenFoodFacts and returns structured product information.

## Getting Started

### Running the Application

**`AppRunner.java`** is the main entry point—run this class to execute the entire application. All other components feed into this central runner.

### Project Structure

- `appRunner.java`: Main CLI orchestrator; menu and pantry user interactions
- `FoodAPI.java`: Calls OpenFoodFacts API; parses product name and brand
- `FoodAPIExample.java`: Example CLI demonstrating raw API call and JSON parsing
- `Food.java`: Data model for food items with barcode and expiration
- `Pantry.java`: Manages pantry collections; add/remove items; list owners

## Dependencies

- Java 8+
- OpenFoodFacts API (free, no key required)

## Contributing

Commit to branches, main commit risky.