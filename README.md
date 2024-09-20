# Calculator
## Installing development environment

1. Install nix package manager into your system:
```
curl -L https://nixos.org/nix/install | sh
```
2. Configure nix
```
mkdir -p ~/.config/nix
echo "experimental-features = nix-command flakes" > ~/.config/nix/nix.conf
```
3. Enter into the environment
    * If you have system-wide installed IDEA:
    ```
    # from the project root directory
    nix develop '.#no-idea'
    ```
    * If you haven't system-wide installed IDEA:
    ```
    # from the project root directory
    nix develop
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

<!-- TODO[akhorokhorin,kmitkin]: complete readme -->
