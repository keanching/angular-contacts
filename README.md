angular-contacts by Kean Ching
==============================

A contacts application using Spring Boot + AngularJS.
To run the app:
- `mvn spring-boot:run`
- Open url in browser: http://localhost:8080/angular-contacts
- Set `spring.profiles.active` property to specify the environment, for example: `mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=prd"`

For a prod build/deploy
=======================
- `grunt` then `mvn package` to create a deployable war file

# Follow instructions below for local development

Install Git
===========
- Install git from git-scm.com
- Verify install with `git --version`

Install Node.js
===============
- Download from nodejs.org and install
- Verify with `node -v`

Update npm
==========
Node comes with npm installed so you should have a version of npm.
However, npm gets updated more frequently than Node does, so you'll want to make sure it's the latest version

- `npm install -g npm`
- `npm -v`

Install Grunt
=============
- `npm install -g grunt-cli`
- `grunt --version`

Install Bower (optional)
========================
- `npm install -g bower`
- `bower -v`
- `bower install`
- devDependencies are for the development-related scripts, e.g. unit testing, packaging scripts, documentation generation, etc.
- dependencies are required for production use, and assumed required for dev as well.

Install NPM dependencies
=====================
- `npm install`

Build JS files
==============
- `grunt`

Watch files while developing
============================
- `grunt watch`
