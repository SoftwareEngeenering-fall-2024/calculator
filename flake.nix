{
  description = "Kotlin simple application flake";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/336eda0d07dc5e2be1f923990ad9fdb6bc8e28e3";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem ( system:
    let
      pkgs = import nixpkgs { inherit system; };
      core-pkgs = with pkgs; [
          kotlin # compiler
          gradle # building
          sqlite
      ];
    in {
      devShell = pkgs.mkShell {
        name = "kotlin";
        buildInputs = core-pkgs ++ [pkgs.jetbrains.idea-community];
      };
    });
}
