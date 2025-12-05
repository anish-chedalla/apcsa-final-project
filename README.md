# Pantry & Barcode Food Safety App

A simple Java app that helps people check food safety and avoid food waste. You can enter a barcode to look up product info through OpenFoodFacts, save it to your pantry with an expiration date, and get clear warnings about which items are safe, questionable, or expired.

## Purpose

Date labels are confusing, and people either throw out good food or eat something unsafe.  
This app helps by:

- Looking up products by barcode  
- Tracking expiration dates  
- Marking items that need checking  
- Flagging items that should be thrown out  
- Giving a quick, clear summary of your pantry status  

## How to Use

Run **`AppRunner.java`**.  
This opens the menu where you can:

- Add food items  
- Enter expiration dates  
- List everything in your pantry  
- See warnings about expired or questionable items  

## Features

- Barcode lookup using OpenFoodFacts  
- Pantry list management  
- LocalDate expiration tracking  
- Clear warning markers:  
  - `!` — past **EXPIRES** date (throw away)  
  - `*` — past **BEST_BY** or **USE_BY** (check before eating)  
- Support for Perishable and NonPerishable food types


## Developer Notes

### Java Version
Built and tested with:
Java 25.0.1 (LTS)


### API
Uses the **OpenFoodFacts** public API (no key required).


### Dependencies
- Java Standard Library  
- OpenFoodFacts API (HTTP/JSON)  
- JavaFX via Maven (for GUI version)

### Contributing
Use branches for new features. Avoid committing directly to main unless needed.

## License
This project is for educational use.

