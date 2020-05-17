(function() {
    "use strict";
    
    angular.module("ngContacts").controller("createController", createController);

    createController.$inject = ["$log", "$scope", "growl", "contactService", "$state", "CONSTANTS"];
    
    function createController($log, $scope, growl, contactService, $state, CONSTANTS) {
        var logger = $log.getInstance("createController");
        logger.log("New createController created");
        
        var vm = this;
        vm.isSaving = false;
        vm.contact = {
            id: -1,
            userId: "", // Use an empty string because the asyncvalidator will attempt to validate when the page loads
                        // and we want to pass an empty string to the server instead of a null value
            firstName: null,
            lastName: null,
            suffix: null,
            gender: null,
            email: null,
            phone: null,
            companyName: null
        };
        vm.submitForm = submitForm;
        
        $scope.$on("$destroy", function() {
            logger.log("Destroying createController");
        });
        
        function submitForm(form) {
            logger.log("valid: " + form.$valid);
            logger.log("pending: " + (form.$pending != undefined));
            
            if (!form.$valid) {
                logger.log("Form is not valid, nothing submitted");
                growl.error("Unable to create contact.<br>Please fix all errors before proceeding.");
                return false;
            }
            
            vm.isSaving = true;
            contactService.addContact(vm.contact)
                .then(function(data) {
                    var newContactId = data.data.newContactId;
                    var successMsg = vm.contact.firstName + " " + vm.contact.lastName + " (id=" + newContactId + ") successfully saved";
                    logger.log(successMsg);
                    growl.success(successMsg);
                    
                    $state.go("search.details", {
                        contactId: newContactId
                    });
                })
                .catch(function(error) {
                    logger.log("Unable to create contact due to an unknown error");
                })
                .finally(function() {
                    vm.isSaving = false;
                });
            
            return true;
        }
    };
    
})();
