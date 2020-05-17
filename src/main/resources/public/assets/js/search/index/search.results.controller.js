(function() {
    "use strict";
    
    angular.module("ngContacts").controller("searchResultsController", searchResultsController);

    searchResultsController.$inject = ["$log", "$scope", "growl", "eventService", "favoritesService", "$state", "CONSTANTS"];
    
    function searchResultsController($log, $scope, growl, eventService, favoritesService, $state, CONSTANTS) {
        var logger = $log.getInstance("searchResultsController");
        logger.log("New searchResultsController created");
        
        var vm = this;
        vm.showSpinner = false;
        vm.isInitialPageLoad = true;
        vm.results = [];
        vm.addRemoveFavorite = addRemoveFavorite;
        vm.displayContactDetails = displayContactDetails;
        
        // Pass in $scope so that the event service can automatically unbind event handlers for you
        eventService.on("search.results.displayContacts", $scope, function(eventName, data) {
           vm.results = data.contactsList;
           vm.isInitialPageLoad = false;
        });
        
        eventService.on("search.results.showSpinner", $scope, function(eventName, data) {
            vm.showSpinner = data.showSpinner;
            
            // If showing the spinner, clear out the current list
            if (vm.showSpinner) {
                vm.results = [];                
            }
        });
        
        $scope.$on("$destroy", function() {
            logger.log("Destroying searchResultsController");
        });
        
        function addRemoveFavorite(contact) {
            logger.log("addRemoveFavorite");
            
            if (contact.isFavorite) {
                favoritesService.removeFavorite(contact.id)
                    .then(function(data) {
                        logger.log("Favorite was removed: " + contact.id);
                        contact.isFavorite = false;
                        eventService.broadcast("search.index.updateFavCount");
                        growl.warning(contact.firstName + " " + contact.lastName + " (id=" + contact.id + ") removed from favorites");
                    })
                    .catch(function(error) {
                        logger.log("Unable to save favorite for contactId " + contact.id);
                        growl.error("An unknown error occurred. Please try again later.");
                    });
            }
            else {
                favoritesService.addFavorite(contact.id)
                    .then(function(data) {
                        logger.log("Favorite was saved: " + contact.id);
                        contact.isFavorite = true;
                        eventService.broadcast("search.index.updateFavCount");
                        growl.success(contact.firstName + " " + contact.lastName + " (id=" + contact.id + ") added to favorites");
                    })
                    .catch(function(error) {
                        logger.log("Unable to save favorite for contactId " + contact.id);
                        growl.error("An unknown error occurred. Please try again later.");
                    });
            }
        }
        
        function displayContactDetails(contactId) {
            $state.go("search.details", {
                contactId: contactId
            });
        }
    };
    
})();
