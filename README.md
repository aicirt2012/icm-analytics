# ICM NLP Analyzer Service

## Building

Use ``mvn clean install -DskipTests=true`` from inside the project directory to create a deployable ``*.war`` file in the ``target`` folder.

## Running

To run the project, simply copy the ``*.war`` file to a tomcat's ``webapp`` directory and start the tomcat instance. 

## Troubleshooting

When encountering any errors, always make sure you have run ``mvn clean install`` and re-tested the issue before reporting a bug.

## IDE Config

When developing with IntelliJ, the built-in maven can be used instead of a global one. To do so, just add a new maven run configuration and configure it like depicted below.

1. Create new maven run configuration

![1](https://user-images.githubusercontent.com/6501308/32493209-526892e8-c3bd-11e7-995c-7592db95fdf9.PNG)
2. Configure like in the screenshot below

![2](https://user-images.githubusercontent.com/6501308/32493367-da115004-c3bd-11e7-89c6-b880409a7840.PNG)
3. Select the run configuration and start the compilation using the run button

![3](https://user-images.githubusercontent.com/6501308/32493368-da3b4ff8-c3bd-11e7-9382-b4aba944041b.PNG)

