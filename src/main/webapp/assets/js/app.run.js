(function() {
    "use strict";
    
    angular.module("ngContacts").run(appRun);

    appRun.$inject = ["$log", "$rootScope", "$state", "$anchorScroll", "CONSTANTS"];
    
    function appRun($log, $rootScope, $state, $anchorScroll, CONSTANTS) {
        var logger = $log.getInstance("ngContacts");
        
        logger.info("jquery: " + $.fn.jquery);
        logger.info("underscore: " + _.VERSION);
        logger.info("moment: " + moment.version);
        logger.info("angular: " + angular.version.full);
        
        $rootScope.$on("$stateChangeStart", function(event, toState, toParams, fromState) {
            logger.log("stateChangeStart: from '" + fromState.name + "' to '" + toState.name + "'");
        });

        $rootScope.$on("$stateChangeError", function(event, toState, toParams, fromState, fromParams, error) {
            logger.log("stateChangeError: from '" + fromState.name + "' to '" + toState.name + "' with error: ", error);
        });

        $rootScope.$on("$stateChangeSuccess", function(event, toState, toParams, fromState) {
            logger.log("stateChangeSuccess: from '" + fromState.name + "' to '" + toState.name + "' with params " + JSON.stringify(toParams));
            $anchorScroll(); // Scrolls to the top because the scroll position is retained between view transitions
        });
        
        $rootScope.$state = $state;
        
        // Add constants to rootScope so that it can be accessed from html files
        $rootScope.CONSTANTS = {};
        for (var prop in CONSTANTS) {
            $rootScope.CONSTANTS[prop] = CONSTANTS[prop];
        }
    }
    
})();
