## Project Overview
The **ANDR_ExternalStorage** project is an Android application developed to demonstrate how to handle external storage operations within an Android environment. It focuses on reading from and writing to external storage, providing practical examples of managing files outside the app's internal storage.

## Features
- **Write to External Storage**: The application allows users to create and save files to the device's external storage, illustrating the process of obtaining necessary permissions and handling file output streams.

- **Read from External Storage**: Users can retrieve and display the contents of files stored in external storage, demonstrating how to access and read external files securely.

## Lessons Learned
Developing this project provided valuable insights into:

- **External Storage Permissions**: Understanding the permissions required to interact with external storage, especially with changes introduced in Android 11 and above, such as the MANAGE_EXTERNAL_STORAGE permission. 

- **Scoped Storage**: Learning about the Scoped Storage model implemented in recent Android versions, which alters how apps access shared storage, emphasizing the importance of adhering to new best practices for data access.

- **File Management**: Implementing methods to create, write, read, and delete files in external storage, ensuring data integrity and security throughout the application's lifecycle.

### How to Use
- **Clone the Repository**: Download or clone the repository to your local machine using the following command:

        git clone https://github.com/thuliovs/ANDR_ExternalStorage.git

- **Open in Android Studio**: Launch Android Studio and select 'Open an existing project.' Navigate to the cloned repository's directory to open the project.

- **Build and Run**: Build the project and run it on an emulator or physical device to explore the external storage functionalities.
