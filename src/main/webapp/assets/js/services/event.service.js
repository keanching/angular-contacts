(function() {
    "use strict";
    
    angular.module("ngContacts").factory("eventService", eventService);

    eventService.$inject = ["$log", "$rootScope"];
    
    function eventService($log, $rootScope) {
        var logger = $log.getInstance("eventService");
        var service = {
            broadcast: broadcast,
            on: on
        };
        
        return service;
        
        function broadcast(eventName, data) {
            data = data || {};
            logger.log("Broadcasting event: " + eventName + ", data: ", data);
            $rootScope.$broadcast(eventName, data);
        }
        
        function on(eventName, scope, handlerFn) {
            logger.log("Registering handler for event: " + eventName);
            var unbind = $rootScope.$on(eventName, handlerFn);

            if (scope) {
                // Remove this block of code in a production environment, this is just for debugging
                scope.$on("$destroy", function() {
                    logger.log("Removing handler for event: " + eventName);    
                });

                // This is to prevent memory leaks by calling the unbind function when the scope gets destroyed
                scope.$on("$destroy", unbind);
            }
        }
    }

})();
