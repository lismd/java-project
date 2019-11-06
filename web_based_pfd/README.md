# CE Devops React App template

**Build:**
[![BuildStatus](https://bamboo.honeywell.com/plugins/servlet/wittified/build-status/FGKVJBNQ-KWUGJNFI)](https://bamboo.honeywell.com/browse/FGKVJBNQ-KWUGJNFI)

This is the React App template for the CE DevOps Initiative. For more information see the [Design](https://confluence.honeywell.com/pages/viewpage.action?pageId=264639558)

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

The following tools with minimum specified version are required to install local build.
  * node version 8.11.4
  * yarn version 1.10.1

The following tools with minimum specified version are required to install full build.
  * node version 8.11.4
  * yarn version 1.10.1
  * Docker version 18

### Install prerequisites
#### Windows
Please refer [here](https://blog.teamtreehouse.com/install-node-js-npm-windows) for node installation.
Please refer [here](https://yarnpkg.com/lang/en/docs/install/#windows-stable) for yarn installation.

#### Mac
Please refer [here](https://treehouse.github.io/installation-guides/mac/node-mac.html) for node installation.
```bash
brew install node
```
Please refer [here](https://yarnpkg.com/lang/en/docs/install/#mac-stable) for yarn installation.
```bash
brew install yarn
```

### Enable code linting using TSLint
Please refer [here](https://palantir.github.io/tslint/usage/configuration/) for more TSLint configuration information

Tslint can be linked with git commit using pre-commit hooks.

Execute the following command to enable pre-commit code linting

```bash
git config core.hooksPath .githooks
```

#### Note for Linux users

If you're on Linux, you will need to install [Powershell Core](<https://docs.microsoft.com/en-us/powershell/scripting/setup/installing-powershell-core-on-linux?view=powershell-6>) in order to run the build script.

### Building

#### Windows

Execute `./bootstrap.ps1` in a Powershell window to download and execute the build scripts.

#### Linux

Execute `pwsh bootstrap.ps1` to download and execute the build scripts.

### Running

To run React App on your machine execute

```bash
yarn --cwd .\src\app\ install
yarn --cwd .\src\app\ start
```

### Build and run Docker locally

#### Pre-requisite

Make sure you have Docker for Windows installed and it uses Linux containers

#### Generating only the docker image

1. Run `powershell -executionpolicy unrestricted .\bootstrap.ps1` to download and execute the build scripts for building and packing. 
2. Run `powershell -executionpolicy unrestricted .\bootstrap.ps1 -Target Publish`, which creates the local Docker image.
3. From a command/powershell prompt you should be able to see your newly created image. It should look something like this:

    ```shell

    $ docker image ls
    REPOSITORY           TAG        IMAGE ID            CREATED             SIZE

    <bitbucket-repo-name> 0.1.0     2d2cfc645c50       2 hours ago         1.45GB

    ```

#### Running the web API  container

Create and run the docker container from the image as follows:

```shell
  docker run -it --rm -p 3000:3000/tcp --name <bitbucket-repo-name>.0.1.0 <bitbucket-repo-name>
```
## Acceptance Test
### Local execution
Acceptance test are written using Cucumber JS. Tests are executed against deployed instance, hence a successful build is required.

```shell
$ cd template_root_dir\src\_tests_\acceptance
$ acceptance>selenium-standalone install  //only execute for the first time
$ acceptance>npm run-script selenium
// Open a new terminal to run "npm start"
$ acceptance>npm start
```

Please refer [here](https://docs.cucumber.io/cucumber/) for complete documentation

### Octopus execution

ToDo

## Built With

* [Cake](https://cakebuild.net/) - The Build framework
* [Yarn](https://yarnpkg.com/en/) - Packet manager

## Contributing

TBD

## Versioning

We use [SemVer](http://semver.org/) for versioning using [GitVersion](https://github.com/GitTools/GitVersion). For the versions available, see the [artifactory repository](https://artifactory-na.honeywell.com/artifactory/webapp/#/artifacts/browse/simple/General/ce-devops-common-nuget-stable-local).