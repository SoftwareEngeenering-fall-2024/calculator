#!/usr/bin/env bash

set -ex
if [ "$1" != "minimal" ] && [ "$1" != "develop" ]; then
    echo "Usage: ./install.sh <minimal|develop>"
    exit 1
fi
target=$1

if [ $target = "minimal" ]; then
    flake_variant=minimal
fi
if [ $target = "develop" ]; then
    # Try to detect system wide installed IDEA
    if ! type "idea-community" &> /dev/null; then
        flake_variant=with-idea
    else
        flake_variant=minimal
    fi
fi


if ! type "nix" &> /dev/null; then
    sh <(curl -L https://nixos.org/nix/install) --daemon --yes
fi

if ! nix flake info &> /dev/null; then
    mkdir -p ~/.config/nix
    echo "experimental-features = nix-command flakes" >> ~/.config/nix/nix.conf
fi

bash -cl "nix develop '.#${flake_variant}' -c 'true'"

if [ "$target" = "develop" ]; then
    mkdir -p ~/.local/share/applications
    cat > ~/.local/share/applications/idea-se-project-fall-2024.desktop <<FILE
[Desktop Entry]
Name=Idea SE Project Fall 2024
Exec=bash -cl 'nix develop "$(pwd)#$flake_variant" -c idea-community'
Type=Application
Categories=Development;
StartupNotify=true
FILE
fi
