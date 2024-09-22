# Calculator backend
## Install development environment

Run installation script in suitable variant
* Developer installation
    ```#shell
    ./install.sh develop
    ```
  This variant creates desktop file that launch configured IDEA, so you can
  select "IDEA SE Project Fall 2024" in your application launcher to start IDEA.

* Minimal build and run installation
    ```#shell
    ./install.sh minimal
    ```

## Run app from CLI or Gradle 101
First of all you should be under nix environment:
```#shell
nix develop # from the project root directory
```

Now you can use `gradle` command that provided by nix flake,
and your system will remain clean.

Most useful commands:
* `gradle build`
* `gradle run`
* `gradle test`
* `gradle help`

## [Architecture design](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Calculator.drawio#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D1n22IL4d48abKkXYeTFZID624mQQqpETp%26export%3Ddownload)
