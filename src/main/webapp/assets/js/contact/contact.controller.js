(function() {
    "use strict";
    
    angular.module("ngContacts").controller("contactController", contactController);

    contactController.$inject = ["$log", "$scope", "$anchorScroll", "CONSTANTS"];
    
    function contactController($log, $scope, $anchorScroll, CONSTANTS) {
        var logger = $log.getInstance("contactController");
        logger.log("New contactController created");
        
        var vm = this;
        vm.showSuccessMsg = false;
        vm.submitMessage = submitMessage;
        
        $scope.$on("$destroy", function() {
            logger.log("Destroying contactController");
        });
        
        function submitMessage() {
            logger.log("Message submitted");
            vm.showSuccessMsg = true;
            $anchorScroll();
        }
    };
    
})();
