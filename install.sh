#!/usr/bin/env bash

set -x
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
    curl -L https://nixos.org/nix/install | sh
    sudo usermod -aG nix-users $(whoami)
    newgrp nix-users
fi

if ! nix flake info &> /dev/null; then
    mkdir -p ~/.config/nix
    echo "experimental-features = nix-command flakes" >> ~/.config/nix/nix.conf
fi

nix develop ".#${flake_variant}" -c 'true'

if [ "$target" = "develop" ]; then
    cat > ~/.local/share/applications/idea-se-project-fall-2024.desktop <<FILE
[Desktop Entry]
Name=Idea SE Project Fall 2024
Exec=nix develop '$(pwd)#$flake_variant' -c idea-community
Type=Application
Categories=Development;
StartupNotify=true
FILE
fi
