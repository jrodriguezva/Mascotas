# Mascotas

This is an example Android Application that follow up Clean Architecture principles using MVVM pattern with Google architecture components and kotlin.
The application has been made according to https://members.architectcoders.com/ requirements, which are:
* Follow SOLID principles
* Use a presentation pattern (MVP or MVVM).
* Use Google Architecture Components.
* Make a Clean Architecture, similar to Robert C. Martin (Uncle Bob) solution (https://blog.cleancoder.com/uncle-bob/2016/01/04/ALittleArchitecture.html).
* Use a dependency injector (Dagger or Koin).
* Add some unit tests, integration tests, and UI tests.

## Installation
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/jrodriguezva/Mascotas.git
```
Then, create a new Firebase project https://console.firebase.google.com/ and generate a google-services.json that allows the app to use a Firebase Database.
The downloaded file must be stored inside the app module.

To login into the app, you can create an user account using the Firebase app or do it just inside the app. It does not need two steeps verification, so you don't need to use your real email account.

Enjoy!

## Maintainers
This project is mantained by:
* [Álvaro Quintana](https://github.com/AlvaroQ)
* [José Manuel Riballo](https://github.com/RiballoJose)
* [Juan Rodríguez](https://github.com/jrodriguezva)

## Contributing

1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
5. Push your branch (git push origin my-new-feature)
6. Create a new Pull Request 
