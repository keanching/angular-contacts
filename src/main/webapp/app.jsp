<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en-us" ng-app="ngContacts" ng-strict-di>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
        <meta name="description" content="Angular Contacts">
        <meta name="author" content="Kean Ching">
        <title>Angular Contacts</title>
        
        <!-- Google fonts -->    
        <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Roboto:400,300,300italic,400italic,500,500italic,700,700italic">

        <!-- Don't minify using usemin because these css files reference 'fonts' directories -->
        <link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="vendor/font-awesome/css/font-awesome.min.css">
                
        <!-- build:css style.min.css -->
        <link rel="stylesheet" type="text/css" href="vendor/angular-growl-v2/angular-growl.min.css">
        <link rel="stylesheet" type="text/css" href="vendor/ui-router-extras/statevis.css">
        <link rel="stylesheet" type="text/css" href="assets/css/style.css">
        <!-- endbuild -->

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
        <![endif]-->
    </head>
    <body ng-cloak>
        <div growl></div>
        <!-- Fixed navbar -->
        <nav class="navbar navbar-default navbar-fixed-top navbar-ngcontacts">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar-collapse" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" ui-sref="search.index" style="padding-top:8px">
                        <span class="fa-stack">
                            <i class="fa fa-square-o fa-stack-2x"></i>
                            <i class="fa fa-user fa-stack-1x"></i>
                        </span>
                        Angular Contacts
                    </a>
                </div>
                <div id="top-navbar-collapse" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li ui-sref-active="active"><a ui-sref="search">Search</a></li>
                        <li ui-sref-active="active"><a ui-sref="about">About</a></li>
                        <li ui-sref-active="active"><a ui-sref="contactus">Contact Us</a></li>
                    </ul>
                </div>
            </div>
        </nav>
        
        <div style="min-height:600px; margin-bottom:40px" ui-view></div>
        
        <hr>
        
        <footer class="footer" style="margin-bottom:40px">
            <div class="container">
                <div class="row">
                    <br>
                    <div class="col-md-12 text-center">
                        <span class="label label-default" style="color:#555; background-color:#eee; padding:5px 10px; font-size:95%">
                            Copyright &#169; 2016 - Kean Ching - Intecore Technologies, Inc.
                        </span>
                    </div>
                </div>
                <br>
                <hr>
                Debug router state using D3
                <br>
                <br>
                <div class="row">
                    <div class="col-md-4">
                        <div style="border:1px solid lightgray; padding:1em; border-radius:4px">
                            <legend>Legend</legend>
                            <span style="padding:5px; background-color:#AF0; border-radius:4px; font-weight:bold">Entered</span>
                            <span style="padding:5px; background-color:#0F0; border-radius:4px; font-weight:bold">Active</span>
                            <span style="padding:5px; background-color:#55F; border-radius:4px; font-weight:bold">Inactive</span>
                            <span style="padding:5px; background-color:#777; border-radius:4px; font-weight:bold">Exited</span>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div state-vis width="400" height="200"></div>
                    </div>
                </div>
            </div>
        </footer>
        
        <!-- build:js app.min.js -->
        <script type="text/javascript" src="vendor/jquery/jquery.min.js"></script>
        <script type="text/javascript" src="vendor/underscore/underscore-min.js"></script>
        <script type="text/javascript" src="vendor/underscore.string/underscore.string.min.js"></script>
        <script type="text/javascript" src="vendor/momentjs/min/moment.min.js"></script>
        <script type="text/javascript" src="vendor/sprintf/sprintf.min.js"></script>
        <script type="text/javascript" src="vendor/d3/d3.min.js"></script>
        <script type="text/javascript" src="vendor/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="vendor/angular/angular.min.js"></script>
        <script type="text/javascript" src="vendor/angular-messages/angular-messages.min.js"></script>
        <script type="text/javascript" src="vendor/angular-ui-router/angular-ui-router.min.js"></script>
        <script type="text/javascript" src="vendor/ui-router-extras/ct-ui-router-extras.min.js"></script>
        <script type="text/javascript" src="vendor/angular-logger/angular-logger.min.js"></script>
        <script type="text/javascript" src="vendor/angular-growl-v2/angular-growl.min.js"></script>
        <script type="text/javascript" src="assets/js/app.utils.js"></script>
        <script type="text/javascript" src="assets/js/app.module.js"></script>
        <script type="text/javascript" src="assets/js/app.constants.js"></script>
        <script type="text/javascript" src="assets/js/app.config.js"></script>
        <script type="text/javascript" src="assets/js/app.routes.js"></script>
        <script type="text/javascript" src="assets/js/app.run.js"></script>
        <script type="text/javascript" src="assets/js/directives/kctooltip.directive.js"></script>
        <script type="text/javascript" src="assets/js/directives/phoneNumberValidator.directive.js"></script>
        <script type="text/javascript" src="assets/js/directives/userIdValidator.directive.js"></script>
        <script type="text/javascript" src="assets/js/services/event.service.js"></script>
        <script type="text/javascript" src="assets/js/services/contact.service.js"></script>
        <script type="text/javascript" src="assets/js/services/favorites.service.js"></script>
        <script type="text/javascript" src="assets/js/contact/contact.controller.js"></script>
        <script type="text/javascript" src="assets/js/search/search.controller.js"></script>
        <script type="text/javascript" src="assets/js/search/index/quick.search.controller.js"></script>
        <script type="text/javascript" src="assets/js/search/index/search.results.controller.js"></script>
        <script type="text/javascript" src="assets/js/search/favorites/favorites.controller.js"></script>
        <script type="text/javascript" src="assets/js/search/details/details.controller.js"></script>
        <script type="text/javascript" src="assets/js/search/create/create.controller.js"></script>
        <!-- endbuild -->
    </body>
</html>
