# angular-contacts

A contacts application using Spring Boot + AngularJS by Kean Ching.

Features

* Advanced routing using ui-router (sticky tabs)
* Integration with Spring restful endpoints
* Bootstrap theme
* Project structure organized by features
* Following conventions of the angular style guide

## Online demo

Located at [http://intecore-demo.com/angular-contacts](http://intecore-demo.com/angular-contacts)

## Running locally

* `mvn spring-boot:run`
* Open url in browser: http://localhost:8080/angular-contacts
* Optionally, set `spring.profiles.active` property to specify the environment, for example: `mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=prd"`

## Build and deploy

* `grunt` then `mvn package` to create a deployable war file

# Follow instructions below for local development

## Install Git

* Install git from git-scm.com
* Verify install with `git --version`

## Install Node.js
* Download from nodejs.org and install
* Verify with `node -v`

## Update npm
Node comes with npm installed so you should have a version of npm.
However, npm gets updated more frequently than Node does, so you'll want to make sure it's the latest version

* `npm install -g npm`
* Verify with `npm -v`

## Install Grunt
* `npm install -g grunt-cli`
* Verify with `grunt --version`

## Install Bower (optional)
* `npm install -g bower`
* Verify with `bower -v`
* `bower install`

devDependencies are for the development-related scripts, e.g. unit testing, packaging scripts, documentation generation, etc.  
dependencies are required for production use, and assumed required for dev as well.

## Install npm dependencies
* `npm install`

## Build js files
- `grunt`

## Watch files while developing
- `grunt watch`

## Contact
For more info contact Kean Ching at kching25@gmail.com
