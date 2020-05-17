(function() {
    "use strict";
    
    angular.module("ngContacts").controller("favoritesController", favoritesController);

    favoritesController.$inject = ["$log", "$scope", "eventService", "favoritesService", "$state", "CONSTANTS"];
    
    function favoritesController($log, $scope, eventService, favoritesService, $state, CONSTANTS) {
        var logger = $log.getInstance("favoritesController");
        logger.log("New favoritesController created");
        
        var vm = this;
        vm.showSpinner = true;
        vm.favList = [];
        vm.displayContactDetails = displayContactDetails;
        
        $scope.$on("$destroy", function() {
            logger.log("Destroying favoritesController");
        });
        
        displayFavorites();
        
        function displayFavorites() {
            favoritesService.getFavorites()
                .then(function(data) {
                    vm.favList = data.data.favorites;
                })
                .catch(function(error) {
                    logger.log("Unable to get favorites due to an unknown error");
                })
                .finally(function() {
                    vm.showSpinner = false;
                });
        }
        
        function displayContactDetails(contactId) {
            $state.go("search.details", {
                contactId: contactId
            });
        }
    };
    
})();
