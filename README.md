Project Setup in IntelliJ IDEA
1. Clone the Repository

Open IntelliJ IDEA and select:

File → New → Project from Version Control

Enter the repository URL and clone the project.

2. Configure Project SDK

Open:

File → Project Structure

In the Project section, set:
Project SDK: JDK 23
Project Language Level: SDK Default (23)

If JDK 23 is not available, click:

Add SDK → Download JDK → Version 23

3. Configure Gradle

Navigate to:

Settings → Build, Execution, Deployment → Build Tools → Gradle

Set the following options:

Gradle JVM: JDK 23
Build and run using: Gradle
Run tests using: Gradle

Make sure Gradle is configured to use Java 23.

4. Reload Gradle Project

After configuring the SDK and Gradle settings:

Open the Gradle tool window and click Reload All Gradle Projects

or run:

./gradlew clean build

For Windows:

gradlew.bat clean build

5. Verify the Configuration

Run:

java -version

and

./gradlew -version

The output should show:

JVM: 23

Once these steps are completed, the project is ready to build and run.
