// Makes use of _.partial from the underscore library - http://underscorejs.org/#partial.
(function() {
    "use strict";
    
    angular.module("ngContacts").controller("quickSearchController", quickSearchController);

    quickSearchController.$inject = ["$log", "$scope", "growl", "eventService", "contactService"];

    function quickSearchController($log, $scope, growl, eventService, contactService) {
        var logger = $log.getInstance("quickSearchController");
        logger.log("New quickSearchController created");
        
        var vm = this;
        vm.isSearching = false;
        vm.resetForm = resetForm;
        vm.submitSearch = submitSearch;
        vm.formModel = {
            anyName: {
                value: "tam mc",
                hasError: false,
                errorMsg: ""
            },
            userId: {
                value: "",
                hasError: false,
                errorMsg: ""
            },
            email: {
                value: "",
                hasError: false,
                errorMsg: ""
            }
        };
        
        $scope.$on("$destroy", function() {
            logger.log("Destroying quickSearchController");
        });
        
        function resetForm() {
            for (var key in vm.formModel) {  
                vm.formModel[key].value = "";
                vm.formModel[key].hasError = false;
                vm.formModel[key].errorMsg = "";
            }
        }
        
        function submitSearch() {
            if (!isFormValid()) {
                return;
            }
            
            var searchCriteria = contactService.createSearchCriteria(
                vm.formModel.anyName.value.split(" "),
                null,
                null,
                vm.formModel.userId.value,
                vm.formModel.email.value,
                null,
                null
            );
        
            showSpinners();
            contactService.searchContacts(searchCriteria)
                .then(function(data) {
                    logger.log("Search results count: " + data.data.contacts.length);
                    
                    eventService.broadcast("search.results.displayContacts", {
                        contactsList: data.data.contacts
                    });
                })
                .catch(function(error) {
                    logger.log("Unable to search for contacts due to an unknown error");
                    growl.error("An unknown error occurred. Please try again later.");
                })
                .finally(function() {
                    hideSpinners();
                });
            
            logger.log("Form submitted, search criteria: ", vm.formModel);
        };
        
        function showSpinners() {
            vm.isSearching = true;
            
            eventService.broadcast("search.results.showSpinner", {
                showSpinner: true
            });
        }
        
        function hideSpinners() {
            vm.isSearching = false;
            
            eventService.broadcast("search.results.showSpinner", {
                showSpinner: false
            });
        }
        
        function isNameValid(name) {
            return !ngContactsUtils.isBlank(name);
        }
        
        function isUserIdValid(userId) {
            return (!ngContactsUtils.isBlank(userId) && userId.length >= 3);
        }
        
        function isEmailValid(email) {
            return (!ngContactsUtils.isBlank(email) && email.indexOf("@") > -1);
        }
        
        function isFormValid() {
            var hasAtLeastOneError = false;
            
            // This is a required field
            var nameValidateFn = _.partial(isNameValid, vm.formModel.anyName.value);
            if (!isFieldValid(vm.formModel.anyName, "Name is required", nameValidateFn)) {
                hasAtLeastOneError = true;
            }
            
            // Optional fields, if the user entered some data then validate it
            if (!ngContactsUtils.isBlank(vm.formModel.userId.value)) {
                var userIdValidateFn = _.partial(isUserIdValid, vm.formModel.userId.value);
                if (!isFieldValid(vm.formModel.userId, "Minimum length is 3", userIdValidateFn)) {
                    hasAtLeastOneError = true;
                }    
            }
            
            if (!ngContactsUtils.isBlank(vm.formModel.email.value)) {
                var emailValidateFn = _.partial(isEmailValid, vm.formModel.email.value);
                if (!isFieldValid(vm.formModel.email, "Invalid email address", emailValidateFn)) {
                    hasAtLeastOneError = true;
                }    
            }
            
            logger.log("Does the form have at least one error? " + (hasAtLeastOneError ? "Yes" : "No"));
            
            return !hasAtLeastOneError;
        }
        
        function isFieldValid(fieldObj, errorMsg, validationFn) {
            var passed = false;
            
            if (validationFn()) {
                fieldObj.hasError = false;
                fieldObj.errorMsg = "";
                passed = true;
            }
            else {
                fieldObj.hasError = true;
                fieldObj.errorMsg = errorMsg;
                passed = false;
            }
            
            return passed;
        }
        
    };
    
})();
