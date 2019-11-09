# SteamGroup
A plugin which let's users obtain a custom group depending on if they are member of a certain Steam group.

## 🚀 Gettings started

Just download the latest release that's compatible with your TSQPF version and copy it into its plugin directory. After you've done that, either reload or restart your framework instance in order to get it loaded and initiated.

## ⚙️ Configuration

Here's a list of all config keys, value datatypes and a description:

KEY | DATATYPE | DESCRIPTION

- **messageLinkClientVerificationRunning** : [String] Tells the user that the account linking process is being run.
- **messagePinCreated** : [String] Tells the user that a temporary link has been created an returns its pin.
- **messageLinkUrlInvalid** : [String] Tells the user that the entered Steam URL is invalid.
- **messageLinkCreated** : [String] Tells the users that the linking and verification process has been completed successfully.
- **messageLinkRemovedFailedNoLink** : [String] Tells the users that his Teamspeak ID can not be unlinked as there is no link that can be removed.
- **messageLinkClientLinkedNeedsVerification** : [String] Tells the user that the entered Steam profile is already awaiting verification.
- **messageLinkClientNotVerified** : [String] Tells the user that the verification process failed.
- **messageGuide** : [String] Tells the user how to use the plugin.
- **messageLinkMissing** : [String] Tells the user that the Steam profile has not been assigned to a temporary link before.
- **messageLinkRemoved** : [String] Tells the user that the current link has been revoked.
- **messageLinkClientAlreadyLinked** : [String] Tells the user that his Teamspeak client is already linked.
- **messageUnknownError** : [String] Tells the user that an unknown error occourred.
- **messageSyntax** : [String] Return the plugins command syntax.
- **steamGroupUrl** : [String | URL] URL of the Steam group.
- **linkGroupId** : [Integer] Database ID of the Teamspeak Steam server group.
- **tempLinkRemoveIntervalInSeconds** : [Integer] Interval in seconds of how often the temporary link remover service will search for any old pins and invalidate them.


## 📁 Directory Tree

SteamGroup/
├── templinks.txt
└── plugin.conf

## 📜 Vortexdata Certification

This plugin is developed by VortexdataNET for the Teamspeak Query Plugin Framework. Every release is being tested for any bugs, its performance or security issues. You are free to use, modify or redistribute the plugin.
