# ICM NLP Analyzer Service

## Building

Use ``mvn clean install -DskipTests=true`` from inside the project directory to create a deployable ``*.war`` file in the ``target`` folder.

## Running

To run the project, simply copy the ``*.war`` file to a tomcat's ``webapp`` directory and start the tomcat instance. 

## Troubleshooting

When encountering any errors, always make sure you have run ``mvn clean install`` and re-tested the issue before reporting a bug.

## IDE Configuration

When developing with IntelliJ, the built-in maven can be used instead of a global one. To do so, just add a new maven run configuration and configure it like depicted below.

1. Create new maven run configuration

![1](https://user-images.githubusercontent.com/6501308/34104052-4771f1f2-e3ef-11e7-9713-46c6d1a40070.PNG)

![2](https://user-images.githubusercontent.com/6501308/34104055-4a3bc6ce-e3ef-11e7-8546-ac42edd3efff.PNG)

2. Configure like in the screenshot below

![3](https://user-images.githubusercontent.com/6501308/34104057-4e4f1856-e3ef-11e7-892d-60bdb75c9334.PNG)
3. Select the run configuration and start the compilation using the run button

![4](https://user-images.githubusercontent.com/6501308/34104065-5457130c-e3ef-11e7-811b-546b54a9c100.PNG)

