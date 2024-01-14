# QR Code Scanner Android App📷

## Overview

This Android app enables users to effortlessly scan QR codes using their device's camera. The application offers a user-friendly interface and utilizes the `journeyapps` library for barcode scanning. Upon scanning a QR code, the app provides various actions based on the content:

- **Open in Browser:** If the scanned content is a valid URL, users can choose to open it in their device's web browser.
- **Copy to Clipboard:** For non-URL content, users can copy the scanned text to the device's clipboard.
- **Share and Download QR Code:** After generating a QR code, users can share it with others or download it to their device.
- **Customizable Prompt:** Users can customize the scanning prompt to provide additional instructions.
- **Beep Sound and Flash:** The app includes features such as a beep sound on successful scans and the ability to use the device's flash for scanning in low-light conditions.

## Key Features

- QR code scanning using the device's camera
- URL validation and integration with the device's web browser
- Copy scanned text to the device's clipboard
- Share and download generated QR codes
- Customizable scanning options (prompt, beep, flash)

## Dependencies

- [journeyapps](https://github.com/journeyapps/zxing-android-embedded) library for barcode scanning

## Usage

1. Press the designated button to initiate QR code scanning.
2. Follow the on-screen instructions to align the QR code within the camera view.
3. Take appropriate actions based on the scanned content.

Feel free to contribute, report issues, or suggest enhancements! Happy scanning!

## License

This project is licensed under the [MIT License](LICENSE).
