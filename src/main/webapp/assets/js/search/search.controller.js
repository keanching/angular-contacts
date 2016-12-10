(function() {
    "use strict";
    
    angular.module("ngContacts").controller("searchController", searchController);

    searchController.$inject = ["$log", "$scope", "eventService", "favoritesService"];

    function searchController($log, $scope, eventService, favoritesService) {
        var logger = $log.getInstance("searchController");
        logger.log("New searchController created");
        
        var vm = this;
        vm.favCount = 0;
        
        eventService.on("search.index.updateFavCount", $scope, function(eventName, data) {
            displayFavoritesCount();
        });
        
        $scope.$on("$destroy", function() {
            logger.log("Destroying searchController");
        });
        
        displayFavoritesCount();
        
        function displayFavoritesCount() {
            favoritesService.getFavoritesCount()
                .then(function(data) {
                    vm.favCount = data.data.favoritesCount;
                    logger.log("Favorites count: " + vm.favCount);
                })
                .catch(function(error) {
                    logger.log("Unable to get favorites count due to an unknown error");
                });            
        }
    };
    
})();
