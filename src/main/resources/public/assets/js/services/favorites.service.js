// Configure the routes
(function() {
    "use strict";
    
    angular.module("ngContacts").factory("favoritesService", favoritesService);

    favoritesService.$inject = ["$log", "$http", "$q", "CONSTANTS"];
    
    function favoritesService($log, $http, $q, CONSTANTS) {
        var service = {
            getFavorites: getFavorites,
            getFavoritesCount: getFavoritesCount,
            addFavorite: addFavorite,
            removeFavorite: removeFavorite
        };
        
        return service;
        
        function getFavorites() {
            var deferred = $q.defer();
            
            $http({
                method: "GET",
                url: CONSTANTS.REST_API_URL + "/favorites"
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
        
        function getFavoritesCount() {
            var deferred = $q.defer();
            
            $http({
                method: "GET",
                url: CONSTANTS.REST_API_URL + "/favoritesCount"
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
        
        function addFavorite(contactId) {
            var deferred = $q.defer();
            
            $http({
                method: "POST",
                url: CONSTANTS.REST_API_URL + "/addFavorite",
                data: {
                    contactId: contactId
                }
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
        
        function removeFavorite(contactId) {
            var deferred = $q.defer();
            
            $http({
                method: "POST",
                url: CONSTANTS.REST_API_URL + "/removeFavorite",
                data: {
                    contactId: contactId
                }
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
    }

})();
