#!/usr/bin/env bash

# Get wallpaper in KDE
qdbus org.kde.plasmashell /PlasmaShell org.kde.PlasmaShell.evaluateScript '
  let d = desktops()[0];
  d.wallpaperPlugin = "org.kde.image";
  d.currentConfigGroup = Array("Wallpaper", "org.kde.image", "General");
  print(d.readConfig("Image"));
'