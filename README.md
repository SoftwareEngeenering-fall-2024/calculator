# Calculator
## Installing development environment

1. Install nix package manager into your system:
```#shell
curl -L https://nixos.org/nix/install | sh
```
2. Configure nix
```#shell
mkdir -p ~/.config/nix
echo "experimental-features = nix-command flakes" > ~/.config/nix/nix.conf
```
3. Enter into the environment
    * If you have system-wide installed IDEA:
    ```#shell
    # from the project root directory
    nix develop '.#no-idea'
    ```
    * If you haven't system-wide installed IDEA:
    ```#shell
    # from the project root directory
    nix develop
    ```
4. Start idea-community

   ```#shell
   idea-community
   ```

## Gradle usage
General rule: you shouldn't use `./gradlew` or bring
it into the repository.

Use `gradle` command that provided by nix flake,
and your system will remain clean.

Most useful commands:
* `gradle build`
* `gradle run`
* `gradle test`

## [Architecture design](https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Calculator.drawio#Uhttps%3A%2F%2Fdrive.google.com%2Fuc%3Fid%3D1n22IL4d48abKkXYeTFZID624mQQqpETp%26export%3Ddownload)

<!-- TODO[akhorokhorin,kmitkin]: complete readme -->
