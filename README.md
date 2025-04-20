# Example of containerization of a Java8 AWT app.

* Java 8 with AWT UI, augmented to have a CLI (ideally headless) mode.
* Docker container, passes in 'jab specific config' as environment variable and tackles the legacy Java app's need to display an AWT UI, even though that UI is never interacted with my users (when invoked via CLI args).
* Refactor the app to elegantly handle the app discovers configuration parameters.
* Optionally, refactor the app to not use AWT unless needed, and then remove the use of `xvfb` to 'fake out
 AWT.
* Refactor the app not to bake-in the 'local file storage' and instead use a mounted volume (`docker run -v ...`).

## Initial Architecture

tbd

## Refactored Architecture

tbd
