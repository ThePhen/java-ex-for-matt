# Example of containerization of a Java8 AWT app

* Java 8 with AWT UI, augmented to have a CLI (ideally headless) mode. Uses hard coded ideas about finding some configuration from a %USER_HOME%\SETTINGS.ini file.
* Docker container, passes in 'jab specific config' as environment variable and tackles the legacy Java app's need to display an AWT UI, even though that UI is never interacted with my users (when invoked via CLI args).
* Refactor the app to elegantly handle the app discovers configuration parameters.
* Optionally, refactor the app to not use AWT unless needed, and then remove the use of `xvfb` to 'fake out'
 AWT.
* Refactor the app not to bake-in the 'local file storage' and instead use a mounted volume (`docker run -v ...`).

## Goals

* Achieve the simplest working containerization solution that works.
* Demo problems and solutions for handling filepath separators (i.e. `\` vs `/`).
* Demo configuration handling techniques.
* Demo decoupling techniques for UI, configuration.

## Elaboration of the UI and Job Parameters

The UI ought to collect the Client, Project, (starting) Sequence #. Should display a non-modal dialog box when errors occur. Should display Job progress in a large textarea.

### Parameters

* Client
* Project
* Starting Sequence Number (optional) - a Project may have many PDFs to generate; this is the ordinal number of the first sequenced PDF to generate. Defaults to 1.
* Ending Sequence Number (optional) - the ordinal number of the last sequenced PDF to generate. If not present, the Job will run until there are no remaining PDFs to generate.

## Initial Architecture

placeholder

## Refactored Architecture

placeholder
