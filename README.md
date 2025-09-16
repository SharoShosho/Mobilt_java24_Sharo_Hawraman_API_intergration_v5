Currency Converter App

The Currency Converter App is a simple and user-friendly Android application built with Kotlin and Android Studio, designed to convert currencies in real-time. The app utilizes the Unirate API to fetch up-to-date exchange rates, providing accurate conversions between major currencies such as USD, EUR, GBP, JPY, CAD, AUD, CHF, CNY, SEK, and NZD.

The app features two main screens: Convert and Result. In the Convert screen, users can select the base and target currencies, enter the amount to convert, and initiate the conversion. The app uses Volley, a lightweight HTTP library, to handle API requests efficiently. Once the API returns the converted value, it is passed via a Bundle to the Result screen, which displays the formatted amount along with the selected currencies.

The interface is designed to be intuitive and responsive, including a progress bar to indicate loading while fetching conversion data. Error handling is implemented to manage invalid inputs and network issues, ensuring a smooth user experience.

This project demonstrates fundamental concepts of Android development, including fragments, navigation, API integration, and JSON parsing. It provides a practical and interactive way to learn about network requests, data handling, and user interface design in mobile applications.

The Currency Converter App can be built and run on any Android device or emulator with an internet connection.
