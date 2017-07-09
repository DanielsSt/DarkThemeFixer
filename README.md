# DarkThemeFixer
Xposed module that fixes some annoying bugs for dark themes, like white text on white background or black text on black background, since some of dark themes have not been updated for a while and new versions of apps are not looking pretty well with them.
Fixes added so far:

* Messaging com.google.android.apps.messaging (white on white when typing text)
* Google Play Store com.android.vending (white on white text on multiple places)
* Google Chrome com.android.chrome (white on white location bar)
* Gmail com.google.android.gm (black on black when creating message)
* WhatsApp com.whatsapp (white on white text when using @ mention)


Tested on CM13 using DeepDarkness Theme

# Installation
Install from http://repo.xposed.info/module/tk.navideju.darkthemefixer
Then reboot :)

#Planned features
* Update UI element colors without installing new version and rebooting (by getting element list from hosted source)
* Ability to toggle fixes
