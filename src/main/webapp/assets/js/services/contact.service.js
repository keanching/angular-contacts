// Configure the routes
(function() {
    "use strict";
    
    angular.module("ngContacts").factory("contactService", contactService);

    contactService.$inject = ["$log", "$http", "$q", "CONSTANTS"];
    
    function contactService($log, $http, $q, CONSTANTS) {
        var service = {
            createSearchCriteria: createSearchCriteria,
            getContact: getContact,
            getContacts: getContacts,
            searchContacts: searchContacts,
            doesUserIdExist: doesUserIdExist,
            updateContact: updateContact,
            addContact: addContact
        };
        
        return service;
        
        function createSearchCriteria(anyName, firstName, lastName, userId, email, dobStart, dobEnd) {
            var retObj = {};
            retObj.anyName = anyName;
            retObj.firstName = firstName;
            retObj.lastName = lastName;
            retObj.userId = userId;
            retObj.email = email;
            retObj.dobStart = dobStart;
            retObj.dobEnd= dobEnd;
            
            return retObj;
        }
        
        function getContact(contactId) {
            var deferred = $q.defer();
            
            $http({
                method: "GET",
                url: CONSTANTS.REST_API_URL + "/contact/" + contactId
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
        
        // TODO: Implement paging, add parameters startIndex, pageLength
        function getContacts() {
            var deferred = $q.defer();
            
            $http({
                method: "GET",
                url: CONSTANTS.REST_API_URL + "/contacts"
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
        
        function searchContacts(searchCriteria) {
            var deferred = $q.defer();
            
            $http({
                method: "POST",
                url: CONSTANTS.REST_API_URL + "/search",
                data: searchCriteria
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
        
        function doesUserIdExist(submittedUserId, excludedContactId) {
            var deferred = $q.defer();
            
            $http({
                method: "GET",
                url: CONSTANTS.REST_API_URL + "/userIdExists?submittedUserId=" + submittedUserId + "&excludedContactId=" + excludedContactId
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
        
        function updateContact(contact) {
            var deferred = $q.defer();
            
            $http({
                method: "POST",
                url: CONSTANTS.REST_API_URL + "/updateContact",
                data: contact
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }

        function addContact(contact) {
            var deferred = $q.defer();
            
            $http({
                method: "POST",
                url: CONSTANTS.REST_API_URL + "/addContact",
                data: contact
            }).success(function(data) {
                deferred.resolve(data);
            }).error(function(data) {
                deferred.reject(data);
            });

            return deferred.promise;
        }
    }
})();
